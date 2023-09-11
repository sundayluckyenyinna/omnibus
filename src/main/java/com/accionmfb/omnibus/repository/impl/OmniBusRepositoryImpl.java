package com.accionmfb.omnibus.repository.impl;

import com.accionmfb.omnibus.model.AppUser;
import com.accionmfb.omnibus.model.Customer;
import com.accionmfb.omnibus.model.CustomerLogSession;
import com.accionmfb.omnibus.repository.OmniBusRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@ConditionalOnMissingBean(OmniBusRepository.class)
@RequiredArgsConstructor
public class OmniBusRepositoryImpl implements  OmniBusRepository{

    @PersistenceContext()
    private EntityManager em;

    @Override
    public AppUser findByUsername(String username) {
        return em.createQuery("select a from AppUser a where a.username = :username", AppUser.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<CustomerLogSession> findAllByMobileNumber(String mobileNumber) {
        return em.createQuery("select cls from CustomerLogSession cls where cls.mobileNumber = :mobileNumber", CustomerLogSession.class)
                .setParameter("mobileNumber", mobileNumber)
                .getResultList();
    }

    @Override
    public List<CustomerLogSession> findAllByMobileNumberAndChannel(String mobileNumber, String channel) {
        return em.createQuery("select cls from CustomerLogSession cls where cls.mobileNumber = :mobileNumber and cls.channel = :channel", CustomerLogSession.class)
                .setParameter("mobileNumber", mobileNumber)
                .setParameter("channel", channel)
                .getResultList();
    }

    @Override
    public Customer findByMobileNumber(String mobileNumber) {
        return em.createQuery("select c from Customer c where c.mobileNumber = :mobileNumber", Customer.class)
                .setParameter("mobileNumber", mobileNumber)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
    }

    @Override
    public CustomerLogSession saveCustomerSession(CustomerLogSession customerLogSession) {
        em.persist(customerLogSession);
        em.flush();
        return customerLogSession;
    }
}
