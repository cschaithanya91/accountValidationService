package com.account.validation.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.account.validation.exception.ServiceProviderException;
import com.account.validation.model.DataProviderConfigProperty;
import com.account.validation.model.ProviderRequest;
import com.account.validation.model.ProviderResponse;
import com.account.validation.model.ProviderStatus;
import com.account.validation.model.ServiceRequest;
import com.account.validation.model.ServiceResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author user
 *
 */
@Service
@Slf4j
public class AccountValidationServiceImpl implements AccountValidationService {

	/**
	 * 
	 */
	@Autowired
	private DataProviderConfigProperty dataProviders;

	/**
	 * 
	 */
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Aggregation of all provider responses
	 *
	 * @return
	 * @throws ServiceProviderException
	 */
	@Override
	public ServiceResponse isValidBankAccount(ServiceRequest serviceRequest) throws ServiceProviderException {

		log.info("AccountServiceImpl - isValidBankAccount");
		ServiceResponse response = new ServiceResponse();
		List<ProviderStatus> providerResponses = new ArrayList<>();

		if (dataProviders != null && dataProviders.getProviders() != null) {
			if (serviceRequest.getProviders() == null || serviceRequest.getProviders().isEmpty()) {
				for (Map.Entry<String, String> provider : dataProviders.getProviders().entrySet()) {

					providerResponses = aggregateProviderResponse(provider.getKey(), provider.getValue(),
							serviceRequest, providerResponses);
				}
			} else {
				for (String name : serviceRequest.getProviders()) {
					if (dataProviders.getProviders().get(name) != null) {

						providerResponses = aggregateProviderResponse(name, dataProviders.getProviders().get(name),
								serviceRequest, providerResponses);
					} // else {
						// throw new ServiceProviderException("Given provider is not configured in the
						// service");
						// }
				}
			}
		} else {
			throw new ServiceProviderException("Error in loading provider configuration");
		}
		response.setResult(providerResponses);
		return response;
	}

	/**
	 * 
	 * @param name
	 * @param url
	 * @param serviceRequest
	 * @param providerResponses
	 * @return
	 */
	private List<ProviderStatus> aggregateProviderResponse(String name, String url, ServiceRequest serviceRequest,
			List<ProviderStatus> providerResponses) {
		Optional<ProviderResponse> providerResponse = getProviderResponse(url, serviceRequest);
		if (providerResponse.isPresent()) {
			ProviderStatus providerStatus = new ProviderStatus();
			providerStatus.setProvider(name);
			providerStatus.setValid(providerResponse.get().isValid());
			providerResponses.add(providerStatus);
		}
		return providerResponses;

	}

	/**
	 * Rest call for provider service
	 * 
	 * @param url
	 * @param serviceRequest
	 * @return
	 */
	private Optional<ProviderResponse> getProviderResponse(String url, ServiceRequest serviceRequest) {
		log.info("AccountServiceImpl - getProviderResponse");
		ProviderRequest providerRequest = new ProviderRequest(serviceRequest.getSortCode(),
				serviceRequest.getAccountNumber());
		ProviderResponse providerResponse = restTemplate.postForObject(url, providerRequest, ProviderResponse.class);
		Optional<ProviderResponse> providerOptional = Optional.ofNullable(providerResponse);
		return providerOptional;
	}

}
