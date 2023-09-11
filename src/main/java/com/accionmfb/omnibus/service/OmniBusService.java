package com.accionmfb.omnibus.service;

import com.accionmfb.omnibus.exception.OmniExceptionResponse;
import com.accionmfb.omnibus.model.CustomerLogSession;

public interface OmniBusService {
    CustomerLogSession getCustomerLatestSession(String mobileNumber);

    CustomerLogSession getCustomerLatestSession(String mobileNumber, String channel);

    CustomerLogSession saveCustomerSession(String mobileNumber, String channel, String deviceId);

    OmniExceptionResponse validateCustomerPat(String pat, String userToken);

    OmniExceptionResponse validateAppUser(String userToken);
}
