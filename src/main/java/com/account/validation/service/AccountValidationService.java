package com.account.validation.service;

import com.account.validation.exception.ServiceProviderException;
import com.account.validation.model.ServiceRequest;
import com.account.validation.model.ServiceResponse;

/**
 * 
 * @author user
 *
 */
public interface AccountValidationService {

	/**
	 * 
	 * @param serviceRequest
	 * @return
	 * @throws ServiceProviderException
	 */
	ServiceResponse isValidBankAccount(ServiceRequest serviceRequest) throws ServiceProviderException;

}
