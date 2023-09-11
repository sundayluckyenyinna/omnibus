package com.accionmfb.omnibus.filter;

import com.accionmfb.omnibus.config.OmniBusObjectMapper;
import com.accionmfb.omnibus.config.OmniBusProperties;
import com.accionmfb.omnibus.constants.ResponseCodes;
import com.accionmfb.omnibus.exception.OmniExceptionResponse;
import com.accionmfb.omnibus.service.OmniBusService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
@EnableConfigurationProperties(value = OmniBusProperties.class)
public class PatSessionFilter extends OncePerRequestFilter {

    private final OmniBusProperties omniBusProperties;
    private final OmniBusService omniBusService;
    private final OmniBusObjectMapper objectMapper = new OmniBusObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String[] whiteListedUris = omniBusProperties.getWhiteListedUris().split(",");
        List<String> whiteListedUrisList = Arrays.stream(whiteListedUris).map(String::trim).collect(Collectors.toList());

        String requestUri = httpServletRequest.getRequestURI();
        boolean isWhiteListed = isRequestUriWhiteListed(whiteListedUrisList, requestUri);
        log.info("Request URI: {}", requestUri);
        log.info("Request URI whiteListed: {}", isWhiteListed);

        PrintWriter responseWriter = httpServletResponse.getWriter();
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);

        OmniExceptionResponse customerSessionValidation;
        String userAuth = httpServletRequest.getHeader("Authorization");
        String userToken;
        if(!isWhiteListed){
            log.info("The Request URI of value: {} must be authenticated!. Initializing filter chain", requestUri);

            String pat = httpServletRequest.getHeader("pat");
            if(Optional.ofNullable(pat).isEmpty()){
                customerSessionValidation = new OmniExceptionResponse();
                customerSessionValidation.setResponseCode(ResponseCodes.FAILED_MODEL.getResponseCode());
                customerSessionValidation.setResponseMessage("Invalid Personal Access Token provided!");

                log.info("No PAT provided in the request header!");
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                responseWriter.write(objectMapper.stringify(customerSessionValidation));
                return;
            }

            if(Optional.ofNullable(userAuth).isEmpty()){
                customerSessionValidation = new OmniExceptionResponse();
                customerSessionValidation.setResponseCode(ResponseCodes.FAILED_MODEL.getResponseCode());
                customerSessionValidation.setResponseMessage("Invalid App User token provided!");

                log.info("No AppUser token provided in the request header!");
                httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                responseWriter.write(objectMapper.stringify(customerSessionValidation));
                return;
            }
            userToken = userAuth.replace("Bearer ", "").trim();

            customerSessionValidation = omniBusService.validateCustomerPat(pat, userToken);
            if(!customerSessionValidation.getResponseCode().equalsIgnoreCase(ResponseCodes.SUCCESS_CODE.getResponseCode())){
                log.info("Customer could not login even with PAT provided. Error is: {}", objectMapper.stringify(customerSessionValidation));
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                responseWriter.write(objectMapper.stringify(customerSessionValidation));
            }else{
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            }

        }
        else{
            log.info("The request URI of value: {} is whitelisted!. Validating app user...", requestUri);

            if(Optional.ofNullable(userAuth).isEmpty()){
                customerSessionValidation = new OmniExceptionResponse();
                customerSessionValidation.setResponseCode(ResponseCodes.FAILED_MODEL.getResponseCode());
                customerSessionValidation.setResponseMessage("Invalid App User token provided!");

                log.info("No AppUser token provided in the request header!");
                httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                responseWriter.write(objectMapper.stringify(customerSessionValidation));
                return;
            }
            userToken = userAuth.replace("Bearer ", "").trim();
            customerSessionValidation = omniBusService.validateAppUser(userToken);
            if(!customerSessionValidation.getResponseCode().equalsIgnoreCase(ResponseCodes.SUCCESS_CODE.getResponseCode())){
                log.info("User could not use resources even after Authorization header is provided. Error: {}", objectMapper.stringify(customerSessionValidation));
                httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
                responseWriter.write(objectMapper.stringify(customerSessionValidation));
                return;
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }


    private boolean isRequestUriWhiteListed(List<String> whiteListedUris, String requestUri){
        boolean result = false;
        for(String uri : whiteListedUris){
            if(requestUri.endsWith(uri) || uri.endsWith(requestUri)) {
                result = true;
                break;
            }
        }
        return result;
    }
}
