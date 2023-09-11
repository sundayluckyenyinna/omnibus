/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accionmfb.omnibus.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author bokon
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Long id;
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    @Column(name = "customer_number")
    private String customerNumber;
    @Column(name = "title")
    private String title;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "other_name")
    private String otherName;
    @Column(name = "mnemonic")
    private String mnemonic;
    @Column(name = "dob")
    private LocalDate dob = LocalDate.parse("1900-01-01");
    @Column(name = "marital_status")
    private String maritalStatus;
    @Column(name = "gender")
    private String gender;
    @Column(name = "email")
    private String email = "";
    @ManyToOne
    private Branch branch;
    @ManyToOne
    private BVN bvn = null;
    @Column(name = "boarded")
    private boolean boarded = false;
    @Column(name = "ussd_boarded")
    private boolean ussdBoarded = false;
    @Column(name = "ibanking_boarded")
    private boolean iBankingBoarded = false;
    @ManyToOne
    private Identification nin;
    @ManyToOne
    private Identification driversLicense;
    @ManyToOne
    private Identification passport;
    @ManyToOne
    private Identification pvc;
    @Column(name = "other_id_number")
    private String otherIdNumber = "";
    @Column(name = "other_id_type")
    private String otherIdType = "";
    @Column(name = "mobile_number")
    private String mobileNumber;
    @Column(name = "telco")
    private String telco = "";
    @Column(name = "customer_type")
    private String customerType;
    @Column(name = "pin")
    private String pin = "";
    @Column(name = "password")
    private String password = "";
    @Column(name = "otp")
    private String otp = "";
    @Column(name = "otp_expire")
    private LocalDateTime otpExpiry;
    @Column(name = "security_question")
    private String securityQuestion = "";
    @Column(name = "security_answer")
    private String securityAnswer = "";
    @Column(name = "kyc_tier")
    private String kycTier;
    @Column(name = "unboard_at")
    private LocalDateTime unboardAt = LocalDateTime.parse("1900-01-01T00:00:00");
    @Column(name = "boarded_at")
    private LocalDateTime boardedAt = LocalDateTime.parse("1900-01-01T00:00:00");
    @Column(name = "ussd_unboard_at")
    private LocalDateTime ussdUnboardAt = LocalDateTime.parse("1900-01-01T00:00:00");
    @Column(name = "ussd_boarded_at")
    private LocalDateTime ussdBoardedAt = LocalDateTime.parse("1900-01-01T00:00:00");
    @Column(name = "ibanking_unboard_at")
    private LocalDateTime iBankingUnboardAt = LocalDateTime.parse("1900-01-01T00:00:00");
    @Column(name = "ibanking_boarded_at")
    private LocalDateTime iBankingBoardedAt = LocalDateTime.parse("1900-01-01T00:00:00");
    @Column(name = "deposit_limit")
    private BigDecimal depositLimit = BigDecimal.ZERO;
    @Column(name = "withdrawal_limit")
    private BigDecimal withdrawalLimit = BigDecimal.ZERO;
    @Column(name = "balance_limit")
    private BigDecimal balanceLimit = BigDecimal.ZERO;
    @Column(name = "daily_limit")
    private BigDecimal dailyLimit = BigDecimal.ZERO;
    @Column(name = "optout_date")
    private LocalDateTime optoutDate = LocalDateTime.parse("1900-01-01T00:00:00");
    @Column(name = "failure_reason")
    private String failureReason = "";
    @Column(name = "reason_for_status")
    private String reasonForStatus;
    @Column(name = "residence_state")
    private String residenceState;
    @Column(name = "residence_city")
    private String residenceCity;
    @Column(name = "residence_address")
    private String residenceAddress;
    @Column(name = "status")
    private String status = "Pending";
    @Column(name = "request_id")
    private String requestId;
    @ManyToOne
    private AppUser appUser;
    @Column(name = "education_level")
    private String educationLevel;
    @Column(name = "account_officer_code")
    private String accountOfficerCode;
    @Column(name = "other_officer_code")
    private String otherOfficerCode;
    @Column(name = "sector")
    private String sector;
    @Column(name = "time_period")
    private char timePeriod;
    @Column(name = "finger_print", length = 10000)
    private String fingerPrint;
    @Column(name = "ibanking_finger_print", length = 10000)
    private String iBankingFingerPrint;
    @Column(name = "referal_code")
    private String referalCode;
    @Column(name = "referred_by")
    private String referredBy;
    @Column(name = "ussd_pin")
    private String ussdPin = "";
    @Column(name = "ussd_password")
    private String ussdPassword = "";
    @Column(name = "ussd_security_question")
    private String ussdSecurityQuestion = "";
    @Column(name = "ussd_security_answer")
    private String ussdSecurityAnswer = "";
    @Column(name = "ibanking_pin")
    private String iBankingPin = "";
    @Column(name = "ibanking_password")
    private String iBankingPassword = "";
    @Column(name = "ibanking_security_question")
    private String iBankingSecurityQuestion = "";
    @Column(name = "ibanking_security_answer")
    private String iBankingSecurityAnswer = "";
    @Column(name = "imei")
    private String imei = "";
    @Column(name = "password_tries")
    private int passwordTries = 0;
    @Column(name = "pin_tries")
    private int pinTries = 0;
    @Column(name = "ussd_pin_tries")
     private int ussdPinTries = 0;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", createdAt=" + createdAt +
                ", customerNumber='" + customerNumber + '\'' +
                ", title='" + title + '\'' +
                ", lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", middleName='" + middleName + '\'' +
                ", otherName='" + otherName + '\'' +
                ", mnemonic='" + mnemonic + '\'' +
                ", dob=" + dob +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", gender='" + gender + '\'' +
                ", email='" + email + '\'' +
                ", branch=" + branch +
                ", bvn=" + bvn +
                ", boarded=" + boarded +
                ", ussdBoarded=" + ussdBoarded +
                ", iBankingBoarded=" + iBankingBoarded +
                ", nin=" + nin +
                ", driversLicense=" + driversLicense +
                ", passport=" + passport +
                ", pvc=" + pvc +
                ", otherIdNumber='" + otherIdNumber + '\'' +
                ", otherIdType='" + otherIdType + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", telco='" + telco + '\'' +
                ", customerType='" + customerType + '\'' +
                ", pin='" + pin + '\'' +
                ", password='" + password + '\'' +
                ", otp='" + otp + '\'' +
                ", otpExpiry=" + otpExpiry +
                ", securityQuestion='" + securityQuestion + '\'' +
                ", securityAnswer='" + securityAnswer + '\'' +
                ", kycTier='" + kycTier + '\'' +
                ", unboardAt=" + unboardAt +
                ", boardedAt=" + boardedAt +
                ", ussdUnboardAt=" + ussdUnboardAt +
                ", ussdBoardedAt=" + ussdBoardedAt +
                ", iBankingUnboardAt=" + iBankingUnboardAt +
                ", iBankingBoardedAt=" + iBankingBoardedAt +
                ", depositLimit=" + depositLimit +
                ", withdrawalLimit=" + withdrawalLimit +
                ", balanceLimit=" + balanceLimit +
                ", dailyLimit=" + dailyLimit +
                ", optoutDate=" + optoutDate +
                ", failureReason='" + failureReason + '\'' +
                ", reasonForStatus='" + reasonForStatus + '\'' +
                ", residenceState='" + residenceState + '\'' +
                ", residenceCity='" + residenceCity + '\'' +
                ", residenceAddress='" + residenceAddress + '\'' +
                ", status='" + status + '\'' +
                ", requestId='" + requestId + '\'' +
                ", appUser=" + appUser +
                ", educationLevel='" + educationLevel + '\'' +
                ", accountOfficerCode='" + accountOfficerCode + '\'' +
                ", otherOfficerCode='" + otherOfficerCode + '\'' +
                ", sector='" + sector + '\'' +
                ", timePeriod=" + timePeriod +
                ", fingerPrint='" + fingerPrint + '\'' +
                ", iBankingFingerPrint='" + iBankingFingerPrint + '\'' +
                ", referalCode='" + referalCode + '\'' +
                ", referredBy='" + referredBy + '\'' +
                ", ussdPin='" + ussdPin + '\'' +
                ", ussdPassword='" + ussdPassword + '\'' +
                ", ussdSecurityQuestion='" + ussdSecurityQuestion + '\'' +
                ", ussdSecurityAnswer='" + ussdSecurityAnswer + '\'' +
                ", iBankingPin='" + iBankingPin + '\'' +
                ", iBankingPassword='" + iBankingPassword + '\'' +
                ", iBankingSecurityQuestion='" + iBankingSecurityQuestion + '\'' +
                ", iBankingSecurityAnswer='" + iBankingSecurityAnswer + '\'' +
                ", imei='" + imei + '\'' +
                ", passwordTries=" + passwordTries +
                ", pinTries=" + pinTries +
                ", ussdPinTries=" + ussdPinTries +
                ", mobStatus='" + mobStatus + '\'' +
                ", accountOpeningStatus='" + accountOpeningStatus + '\'' +
                '}';
    }

    @Column(name = "mob_status")
    private String mobStatus = "DEACTIVATED";
    @Column(name = "account_opening_status")
    private String accountOpeningStatus = "";
}
