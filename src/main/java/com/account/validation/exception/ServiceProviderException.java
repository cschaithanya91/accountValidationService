package com.account.validation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author user
 *
 */
@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class ServiceProviderException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceProviderException(String errorMessage, Throwable err) {
		super(errorMessage, err);
	}

	public ServiceProviderException(String errorMessage) {
		super(errorMessage);
	}

}
