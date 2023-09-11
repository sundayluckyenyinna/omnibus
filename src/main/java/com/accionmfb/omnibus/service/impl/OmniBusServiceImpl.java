package com.accionmfb.omnibus.service.impl;

import com.accionmfb.omnibus.config.OmniBusProperties;
import com.accionmfb.omnibus.constants.ResponseCodes;
import com.accionmfb.omnibus.constants.SessionStatus;
import com.accionmfb.omnibus.exception.OmniExceptionResponse;
import com.accionmfb.omnibus.jwt.JwtTokenUtil;
import com.accionmfb.omnibus.model.AppUser;
import com.accionmfb.omnibus.model.Customer;
import com.accionmfb.omnibus.model.CustomerLogSession;
import com.accionmfb.omnibus.repository.OmniBusRepository;
import com.accionmfb.omnibus.service.OmniBusService;
import com.accionmfb.omnibus.jwt.PatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnMissingBean(value = OmniBusService.class)
@EnableConfigurationProperties(value = OmniBusProperties.class)
public class OmniBusServiceImpl implements OmniBusService {

    private final OmniBusRepository omniBusRepository;
    private final PatUtil patUtil;
    private final JwtTokenUtil jwtTokenUtil;
    private final OmniBusProperties omniBusProperties;


    @Override
    public CustomerLogSession getCustomerLatestSession(String mobileNumber){
        return omniBusRepository.findAllByMobileNumber(mobileNumber)
                .stream()
                .reduce((a, b) -> b)
                .orElse(null);
    }

    @Override
    public CustomerLogSession getCustomerLatestSession(String mobileNumber, String channel){
         return omniBusRepository.findAllByMobileNumberAndChannel(mobileNumber, channel)
                 .stream()
                 .reduce((a, b) -> b)
                 .orElse(null);
    }

    @Override
    public CustomerLogSession saveCustomerSession(String mobileNumber, String channel, String deviceId){
        CustomerLogSession logSession = new CustomerLogSession();
        logSession.setChannel(channel);
        logSession.setPat(patUtil.createPat(mobileNumber));
        logSession.setCreatedAt(LocalDateTime.now());
        logSession.setDeviceId(deviceId);
        logSession.setStatus(SessionStatus.LOGGED_IN.name());
        logSession.setFailureReason("");
        logSession.setPatExpiry(logSession.getCreatedAt().plusSeconds(Long.parseLong(omniBusProperties.getPatExpirationInSeconds())));
        logSession.setMobileNumber(mobileNumber);
        logSession.setUpdatedAt(logSession.getCreatedAt());
        return omniBusRepository.saveCustomerSession(logSession);
    }

    @Override
    public OmniExceptionResponse validateCustomerPat(String pat, String userToken){
        OmniExceptionResponse response = new OmniExceptionResponse();
        String mobileNumber = patUtil.getCustomerMobileFromPAT(pat);
        Customer customer = omniBusRepository.findByMobileNumber(mobileNumber);

        // Validate the app user.
        String appUsername = jwtTokenUtil.getUsernameFromToken(userToken);
        log.info("APP USERNAME: {}", appUsername);

        AppUser appUser = omniBusRepository.findByUsername(appUsername);
        if(Optional.ofNullable(appUser).isEmpty()){
            response.setResponseCode(ResponseCodes.RECORD_NOT_EXIST_CODE.getResponseCode());
            response.setResponseMessage("Invalid AppUser username provided!");
            return response;
        }

        String currentAppUSerChannel = appUser.getChannel();

        if(Optional.ofNullable(customer).isEmpty()){
            response.setResponseCode(ResponseCodes.RECORD_NOT_EXIST_CODE.getResponseCode());
            response.setResponseMessage("Customer record not found");
            return response;
        }

        CustomerLogSession logSession = this.getCustomerLatestSession(mobileNumber);

        // Check if the customer has no session record.
        if(Optional.ofNullable(logSession).isEmpty()){
            response.setResponseCode(ResponseCodes.NO_SESSION_FOUND.getResponseCode());
            response.setResponseMessage("No session found for customer");
            return response;
        }

        // Check if the current loginSession channel is same as the incoming channel
        if(!currentAppUSerChannel.equalsIgnoreCase(logSession.getChannel())){
            response.setResponseCode(ResponseCodes.SESSION_CHANNEL_MISMATCH.getResponseCode());
            response.setResponseMessage("Current session is not associated to provided AppUSer!");
            return response;
        }

        // Check if the customer personal access token has expired.
        if(LocalDateTime.now().isAfter(logSession.getPatExpiry())){
            response.setResponseCode(ResponseCodes.EXPIRED_SESSION.getResponseCode());
            response.setResponseMessage("Customer session has expired!");
            return response;
        }

        // Check if the customer is currently logged out or Locked in.
        String status = logSession.getStatus();
        if(status != null && !status.toUpperCase().equalsIgnoreCase(SessionStatus.LOGGED_IN.name())){
            response.setResponseCode(ResponseCodes.SESSION_INACTIVE.getResponseCode());
            if(status.equalsIgnoreCase(SessionStatus.LOGGED_OUT.name())){
                response.setResponseMessage("Customer has logged out, please login again!");
            }
            else if(status.equalsIgnoreCase(SessionStatus.LOCKED.name())){
                response.setResponseMessage("Customer session is locked. Please contact administrator or support!");
            }
            else{
                response.setResponseMessage("Customer session is currently not active!");
            }

            return response;
        }

        response.setResponseCode(ResponseCodes.SUCCESS_CODE.getResponseCode());
        response.setResponseMessage("Success");
        return response;
    }

    @Override
    public OmniExceptionResponse validateAppUser(String userToken){
        OmniExceptionResponse response = new OmniExceptionResponse();

        // Validate the app user.
        String appUsername = jwtTokenUtil.getUsernameFromToken(userToken);
        log.info("APP USERNAME: {}", appUsername);

        AppUser appUser = omniBusRepository.findByUsername(appUsername);
        if(Optional.ofNullable(appUser).isEmpty()){
            response.setResponseCode(ResponseCodes.RECORD_NOT_EXIST_CODE.getResponseCode());
            response.setResponseMessage("Invalid AppUser username provided!");
            return response;
        }

        response.setResponseCode(ResponseCodes.SUCCESS_CODE.getResponseCode());
        response.setResponseMessage("Success");
        return response;
    }
}
