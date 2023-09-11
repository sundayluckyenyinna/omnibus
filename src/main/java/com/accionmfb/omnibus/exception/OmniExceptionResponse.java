package com.accionmfb.omnibus.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class OmniExceptionResponse {
    private String responseCode;
    private String responseMessage;
}
