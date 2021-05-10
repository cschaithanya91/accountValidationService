package com.account.validation;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.account.validation.controller.AccountValidationController;
import com.account.validation.model.ProviderStatus;
import com.account.validation.model.ServiceRequest;
import com.account.validation.model.ServiceResponse;
import com.account.validation.service.AccountValidationService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(value = AccountValidationController.class)
public class AccountControllerTest {

	public static final String SORTCODE_ERR_MESSAGE = "Valid Sort Code is required";
	public static final String ACCOUNT_NUMBER_ERR_MESSAGE = "Valid Account Number is required";
	public static final String ACCOUNT_SERVICE_API = "/api/v1/validateAccount";

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AccountValidationService jpmcAccountService;

	/**
	 * Mandatory input validation test case (Both sortCode and accountNumber is
	 * invalid)
	 * 
	 * @throws Exception
	 */
	@Test
	void invalidServiceRequestTest() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(ACCOUNT_SERVICE_API)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(getInValidServiceRequest()))
				.contentType(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest()).andDo(print())
				.andExpect(status().isBadRequest()).andExpect(jsonPath("$.errors").isArray())
				.andExpect(jsonPath("$.errors", hasSize(2)))
				.andExpect(jsonPath("$.errors", hasItem(SORTCODE_ERR_MESSAGE)))
				.andExpect(jsonPath("$.errors", hasItem(ACCOUNT_NUMBER_ERR_MESSAGE)));

	}

	@Test
	void serviceTestWithNoproviders() throws Exception {

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(ACCOUNT_SERVICE_API)
				.accept(MediaType.APPLICATION_JSON).content(asJsonString(getValidServiceRequestNoProviders()))
				.contentType(MediaType.APPLICATION_JSON);
		
		ServiceResponse response = new ServiceResponse();
		List<ProviderStatus> providerResponses = new ArrayList<>();
		ProviderStatus providerStatus = new ProviderStatus();
		providerStatus.setProvider("provider1");
		providerStatus.setValid(true);
		providerResponses.add(providerStatus);
		ProviderStatus provider2Status = new ProviderStatus();
		provider2Status.setProvider("provider2");
		provider2Status.setValid(false);
		providerResponses.add(provider2Status);
		response.setResult(providerResponses);
		
		when(jpmcAccountService.isValidBankAccount(getValidServiceRequestNoProviders())).thenReturn(response);

		mockMvc.perform(requestBuilder).andExpect(status().isOk()).andDo(print())
				.andExpect(jsonPath("$.result", hasSize(2)))
				.andExpect(jsonPath("$.result[0].provider", is("provider1")))
				.andExpect(jsonPath("$.result[0].isValid", is(true)))
				.andExpect(jsonPath("$.result[1].provider", is("provider2")))
				.andExpect(jsonPath("$.result[1].isValid", is(false)));
	}
	

	/**
	 * 
	 * @return
	 */
	private ServiceRequest getInValidServiceRequest() {
		ServiceRequest request = new ServiceRequest();
		request.setAccountNumber(null);
		request.setSortCode("");

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

	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
