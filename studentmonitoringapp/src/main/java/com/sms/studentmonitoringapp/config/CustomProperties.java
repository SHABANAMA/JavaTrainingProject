package com.sms.studentmonitoringapp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@PropertySource("classpath:custom.properties")
@ConfigurationProperties(prefix="custom")
public class CustomProperties {
	private String errormsg;
}
