package com.account.validation.model;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Configuration
@ConfigurationProperties(prefix = "bank.service")
@Data
public class DataProviderConfigProperty {
	
	private Map<String, String> providers;
}
