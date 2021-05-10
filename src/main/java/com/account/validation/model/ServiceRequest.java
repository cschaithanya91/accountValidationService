package com.account.validation.model;

import java.util.List;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class ServiceRequest {

	@NotBlank(message = "Valid Sort Code is required")
	private String sortCode;

	@NotBlank(message = "Valid Account Number is required")
	private String accountNumber;

	private List<String> providers;

}
