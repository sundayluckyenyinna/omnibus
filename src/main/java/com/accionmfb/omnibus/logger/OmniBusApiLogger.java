package com.accionmfb.omnibus.logger;

import com.accionmfb.omnibus.config.OmniBusObjectMapper;
import com.accionmfb.omnibus.config.OmniBusProperties;
import kong.unirest.HttpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(value = OmniBusProperties.class)
public class OmniBusApiLogger {

    private final OmniBusProperties omniBusProperties;
    private final OmniBusObjectMapper objectMapper = new OmniBusObjectMapper();

    public void logIrisApiRequest(String url, String method, Object body, Map<String, String> headers, Map<String, Object> params){
        String requestLoggingStyle = omniBusProperties.getRequestLogging();
        log.info("============================================ OMNI API REQUEST ============================================");
        log.info("URL: {}", url);
        log.info("Http Method: {}", method.toUpperCase());
        log.info("Request headers: {}", objectMapper.stringify(headers));
        log.info("Request Parameters: {}", objectMapper.stringify(params));
        Object requestBody;
        if(body instanceof String){
            requestBody = objectMapper.objectify((String) body);
        }else{
            requestBody = body;
        }
        String requestBodyPrefixLog = "Request Body: {}";
        if(requestLoggingStyle.equalsIgnoreCase("pretty")) {
            log.info(requestBodyPrefixLog, objectMapper.prettyStringify(requestBody));
        }else{
            log.info(requestBodyPrefixLog, objectMapper.stringify(requestBody));
        }
        log.info("===========================================================================================================");
    }

    public void logIrisApiResponse(HttpResponse<String> httpResponse){
        String responseLoggingStyle = omniBusProperties.getResponseLogging();

        log.info("======================================== OMNI API SUCCESS RESPONSE ========================================");
        log.info("Http Status: {}", httpResponse.getStatus());
        log.info("Http Status Text: {}", httpResponse.getStatusText());
        log.info("Http Headers: {}", objectMapper.stringify(httpResponse.getHeaders()));
        log.info("Http Cookies: {}", objectMapper.stringify(Collections.EMPTY_LIST));
        String responseBodyPrefixLog = "Http Response body: {}";
        if(responseLoggingStyle.equalsIgnoreCase("pretty")) {
            log.info(responseBodyPrefixLog, objectMapper.prettyStringify(objectMapper.objectify(httpResponse.getBody())));
        }else{
            log.info(responseBodyPrefixLog, objectMapper.stringify(objectMapper.objectify(httpResponse.getBody())));
        }
        log.info("===========================================================================================================");
    }

    public void logIrisApiExceptionResponse(HttpStatusCodeException httpResponse){
        String responseLoggingStyle = omniBusProperties.getResponseLogging();

        log.info("======================================= OMNI API EXCEPTION RESPONSE =======================================");
        log.info("Http Status: {}", httpResponse.getStatusCode().value());
        log.info("Http Status Text: {}", httpResponse.getStatusCode());
        log.info("Http Headers: {}", objectMapper.stringify(httpResponse.getResponseHeaders()));
        log.info("Http Cookies: {}", objectMapper.stringify(Collections.EMPTY_LIST));
        String responseBodyPrefixLog = "Http Response body: {}";
        if(responseLoggingStyle.equalsIgnoreCase("pretty")) {
            log.info(responseBodyPrefixLog, objectMapper.prettyStringify(objectMapper.objectify(httpResponse.getResponseBodyAsString())));
        }else{
            log.info(responseBodyPrefixLog, objectMapper.stringify(objectMapper.objectify(httpResponse.getResponseBodyAsString())));
        }
        log.info("===========================================================================================================");
    }
}
