package com.accionmfb.omnibus.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "omnibus.config")
public class OmniBusProperties {

    private String requestLogging = "default";
    private String responseLogging = "default";
    private String patSigningKey = "j3H5Ld5nYmGWyULy6xwpOgfSH++NgKXnJMq20vpfd+8=t";
    private String patExpirationInSeconds = "60";
    private String whiteListedUris = "/personal/login,/customerService/new";
}
