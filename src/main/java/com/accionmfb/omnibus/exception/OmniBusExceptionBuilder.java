package com.accionmfb.omnibus.exception;

import lombok.Data;

public class OmniBusExceptionBuilder
{
    private final OmniBusException exception = new OmniBusException();

    public  OmniBusExceptionBuilder withCode(String code){
        exception.setResponseCode(code);
        return this;
    }

    public OmniBusExceptionBuilder withMessage(String message){
        exception.setResponseMessage(message);
        return this;
    }

    public OmniBusException build(){
        return exception;
    }
}
