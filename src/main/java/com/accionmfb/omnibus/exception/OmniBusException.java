package com.accionmfb.omnibus.exception;

public class OmniBusException extends RuntimeException
{
    private final OmniExceptionResponse response = new OmniExceptionResponse();

    public OmniBusException(){
        super();
    }

    public OmniBusException(String responseCode, String responseMessage){
        super(responseMessage);
        response.setResponseCode(responseCode);
        response.setResponseMessage(responseMessage);
    }

    protected void setResponseCode(String responseCode){
        this.response.setResponseCode(responseCode);
    }

    protected void setResponseMessage(String responseMessage){
        this.response.setResponseMessage(responseMessage);
    }

    public String getResponseCode(){
        return this.response.getResponseCode();
    }

    public String getResponseMessage(){
        return this.response.getResponseMessage();
    }

    public static OmniBusExceptionBuilder builder(){
        return new OmniBusExceptionBuilder();
    }

    @Override
    public String toString(){
        return this.response.toString();
    }
}
