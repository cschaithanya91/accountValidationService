package com.account.validation.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.account.validation.exception.ServiceProviderException;
import com.account.validation.model.ServiceRequest;
import com.account.validation.model.ServiceResponse;
import com.account.validation.service.AccountValidationService;
import com.account.validation.util.BankAccountConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author user
 *
 */
@Slf4j
@RestController
@RequestMapping(path = "/api/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Api(value = BankAccountConstants.BANK_ACCOUNT_API, tags = BankAccountConstants.BANK_ACCOUNT_API_TAG)
public class AccountValidationController {

	/**
	 * 
	 */
	@Autowired
	AccountValidationService accountService;

	/**
	 * 
	 * @param serviceRequest
	 * @return
	 * @throws ServiceProviderException 
	 */
	@ApiOperation(value = BankAccountConstants.BANK_ACCOUNT_API_VALUE, response = Map.class)
	@ApiResponses({ @ApiResponse(code = 400, message = "Bad Request"), @ApiResponse(code = 404, message = "Not found"),
			@ApiResponse(code = 200, message = "OK"),
			@ApiResponse(code = 500, message = "Application internal error") })
	@PostMapping("validateAccount")
	public ResponseEntity<ServiceResponse> validateAccount(@Valid @RequestBody ServiceRequest serviceRequest) throws ServiceProviderException {
		log.info("verifying account details");
		ServiceResponse response = accountService.isValidBankAccount(serviceRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
