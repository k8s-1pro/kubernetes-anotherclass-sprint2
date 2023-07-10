package com.pro.app.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;



@Configuration
@ConfigurationProperties("datasource")
@Data
public class DatasourceProperties {

    private String driverClassName;
    private String url;
    private String username;
    private String password;

}