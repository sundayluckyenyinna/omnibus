package com.accionmfb.omnibus.repository;

import com.accionmfb.omnibus.model.AppUser;
import com.accionmfb.omnibus.model.Customer;
import com.accionmfb.omnibus.model.CustomerLogSession;

import java.util.List;

public interface OmniBusRepository {

    AppUser findByUsername(String username);
    List<CustomerLogSession> findAllByMobileNumber(String mobileNumber);
    List<CustomerLogSession> findAllByMobileNumberAndChannel(String mobileNumber, String channel);
    Customer findByMobileNumber(String mobileNumber);

    CustomerLogSession saveCustomerSession(CustomerLogSession customerLogSession);
}
