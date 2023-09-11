package com.accionmfb.omnibus.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceproperties
{
    private String username;
    private String password;
    @Value("${driverClassName:com.microsoft.sqlserver.jdbc.SQLServerDriver}")
    private String driverClassName;
    private String url;
}
