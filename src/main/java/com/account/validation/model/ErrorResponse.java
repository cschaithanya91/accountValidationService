package com.account.validation.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
	
	//General error message about nature of error
    private String message;
 
    //Specific errors in API request processing
    private List<String> errors;

}
