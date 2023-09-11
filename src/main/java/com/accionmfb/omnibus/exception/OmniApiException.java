package com.accionmfb.omnibus.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OmniApiException
{
    private String responseCode;
    private String responseMessage;
    private String timestamp;
    private String path;
    private String stackTrace;
}
