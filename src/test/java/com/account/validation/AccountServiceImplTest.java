package com.account.validation;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.account.validation.controller.AccountValidationController;
import com.account.validation.model.DataProviderConfigProperty;
import com.account.validation.model.ProviderResponse;
import com.account.validation.model.ProviderStatus;
import com.account.validation.model.ServiceRequest;
import com.account.validation.model.ServiceResponse;
import com.account.validation.service.AccountValidationServiceImpl;

@WebMvcTest(value = AccountValidationController.class)
@EnableConfigurationProperties(value = DataProviderConfigProperty.class)
@ComponentScan("com.account.validation.service")
public class AccountServiceImplTest {

	@InjectMocks
	AccountValidationServiceImpl service = new AccountValidationServiceImpl();

	@Mock
	private DataProviderConfigProperty dataProviders;

	@Mock
	RestTemplate restTemplate;

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	void testValidProviderResponse() throws Exception {

		ServiceResponse response = new ServiceResponse();
		List<ProviderStatus> providerResponses = new ArrayList<>();
		ProviderStatus providerStatus = new ProviderStatus();
		providerStatus.setProvider("provider1");
		providerStatus.setValid(false);
		providerResponses.add(providerStatus);
		response.setResult(providerResponses);

		ProviderResponse providerResponse = new ProviderResponse();
		providerResponse.setValid(false);

		Map<String, String> providers = new HashMap<>();
		providers.put("provider1", "http://localhost:9090/provider/isValidBank");
		providers.put("provider2", "http://localhost:9090/provider2/isValidBank");
		when(dataProviders.getProviders()).thenReturn(providers);

		when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), Mockito.any()))
				.thenReturn(providerResponse);

		ServiceResponse serviceResponse = service.isValidBankAccount(getValidServiceRequest());

		Assert.assertEquals(response, serviceResponse);

	}

	/**
	 * 
	 * @throws Exception
	 */
	@Test
	void testServiceWithNoproviders() throws Exception {

		ServiceResponse response = new ServiceResponse();
		List<ProviderStatus> providerResponses = new ArrayList<>();
		ProviderStatus providerStatus = new ProviderStatus();
		providerStatus.setProvider("provider1");
		providerStatus.setValid(false);
		providerResponses.add(providerStatus);
		ProviderStatus provider2Status = new ProviderStatus();
		provider2Status.setProvider("provider2");
		provider2Status.setValid(false);
		providerResponses.add(provider2Status);
		response.setResult(providerResponses);

		ProviderResponse providerResponse = new ProviderResponse();
		providerResponse.setValid(false);

		Map<String, String> providers = new HashMap<>();
		providers.put("provider1", "http://localhost:9090/provider/isValidBank");
		providers.put("provider2", "http://localhost:9090/provider2/isValidBank");
		when(dataProviders.getProviders()).thenReturn(providers);

		when(restTemplate.postForObject(Mockito.anyString(), Mockito.any(), Mockito.any()))
				.thenReturn(providerResponse);

		ServiceResponse serviceResponse = service.isValidBankAccount(getValidServiceRequestNoProviders());

		Assert.assertEquals(response, serviceResponse);

	}

	/**
	 * 
	 * @return
	 */
	private ServiceRequest getValidServiceRequest() {
		ServiceRequest request = new ServiceRequest();
		request.setAccountNumber("12345678");
		request.setSortCode("123456");

		List<String> providerList = new ArrayList<>();

		providerList.add("provider1");
		request.setProviders(providerList);

		return request;
	}

	/**
	 * 
	 * @return
	 */
	private ServiceRequest getValidServiceRequestNoProviders() {
		ServiceRequest request = new ServiceRequest();
		request.setAccountNumber("12345678");
		request.setSortCode("123456");

		return request;
	}

}
