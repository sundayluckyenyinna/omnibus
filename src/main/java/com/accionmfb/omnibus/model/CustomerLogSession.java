package com.accionmfb.omnibus.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "customer_log_session")
public class CustomerLogSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "channel")
    private String channel;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "status")
    private String status;

    @Column(name = "failure_reason")
    private String failureReason;

    @Column(name = "logout")
    private String logoutAt;

    @Column(name = "pat")
    private String pat;

    @Column(name = "pat_expiry")
    private LocalDateTime patExpiry;
}
