/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.accionmfb.omnibus.constants;

/**
 *
 * @author bokon
 */
public enum ResponseCodes {
    SUCCESS_CODE("00"),
    IP_BANNED_CODE("01"),
    IVR_OBSOLETE_RECORD("01"),
    RECORD_EXIST_CODE("02"),
    RECORD_NOT_EXIST_CODE("03"),
    RECORD_INUSE_CODE("04"),
    REQUEST_PROCESSING("05"),
    FAILED_TRANSACTION("06"),
    FAILED_MODEL("07"),
    IVR_RECORD_NOT_FOUND("07"),
    FORMAT_EXCEPTION("08"),
    ID_EXPIRED("09"),
    IVR_FIELD_VALUE_ERROR("09"),
    CUSTOMER_DISABLED("10"),
    PASSWORD_PIN_MISMATCH("11"),
    CUSTOMER_BOARDED("12"),
    CUSTOMER_TIER_REACHED("13"),
    CUSTOMER_NUMBER_MISSING("14"),
    CUSTOMER_NUMBER_MOBILE_MISMATCH("15"),
    MOBILE_NUMBER_CUSTOMER_NAME_MISMATCH("16"),
    ACCOUNT_CUSTOMER_MISMATCH("17"),
    IRREVERSIBLE_TRANSACTION("18"),
    NO_PRIMARY_ACCOUNT("19"),
    INSUFFICIENT_BALANCE("20"),
    NO_CALLBACK_SERVICE("21"),
    SERVICE_UNAVAILABLE("22"),
    CORRUPT_DATA("23"),
    SAME_ACCOUNT("24"),
    ACTIVE_LOAN_EXIST("25"),
    OUT_OF_RANGE("26"),
    IVR_RECORD_EXIST("26"),
    NAME_MISMATCH("27"),
    INVALID_TYPE("28"),
    DUPLICATE_CUSTOMER_RECORD("29"),
    IVR_FIELD_NOT_SUPPLIED("30"),
    OTP_REQUIRED("30"),
    OTP_INVALID("31"),
    NO_ROLE("32"),
    TRANSACTION_BRANCH_MISMATCH("33"),
    LIMIT_EXCEEDED("34"),
    LOAN_DECLINED("35"),
    LOAN_CUSTOMER_MISMATCH("36"),
    DOB_MISMATCH("37"),
    IMEI_MISMATCH("38"),
    IMEI_IS_BLANK("40"),
    IVR_PASSWORD_EXPIRE("60"),
    IVR_INVALID_LOGIN("63"),
    IVR_TRANSACTION_FAILED_ERROR("96"),
    INTERNAL_SERVER_ERROR("99"),
    BAD_REQUEST("400"),
    RESOURCE_NOT_FOUND("97"),
    NO_SESSION_FOUND("95"),
    EXPIRED_SESSION("94"),
    SESSION_INACTIVE("93"),
    SESSION_CHANNEL_MISMATCH("92"),
    REVERSE_CODE("98");

    private final String responseCode;

    public String getResponseCode() {
        return this.responseCode;
    }

    ResponseCodes(String responseCode) {
        this.responseCode = responseCode;
    }
}
