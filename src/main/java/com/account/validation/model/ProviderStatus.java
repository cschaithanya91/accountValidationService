package com.account.validation.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ProviderStatus {

	private String provider;

	@JsonProperty("isValid")
	private boolean isValid;

}
