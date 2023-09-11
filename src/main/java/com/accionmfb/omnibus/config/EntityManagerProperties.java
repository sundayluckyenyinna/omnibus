package com.accionmfb.omnibus.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.jpa.hibernate")
public class EntityManagerProperties {

    @Value("${ddl-auto:update}")
    private String ddlAuto;

    private String dialect;
}
