package com.account.validation.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.account.validation.model.ErrorResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author user
 *
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * 
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error(ex.getMessage());
		List<String> errors = ex.getBindingResult().getAllErrors().stream().map(x -> x.getDefaultMessage())
				.collect(Collectors.toList());
		ErrorResponse error = new ErrorResponse("Validation Failed", errors);
		return new ResponseEntity(error, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex) {
		log.error(ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Server Error", details);
		return new ResponseEntity(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	/**
	 * 
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RestClientResponseException.class)
	public final ResponseEntity<Object> handleRestExceptions(RestClientResponseException  ex) {
		log.error(ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getMessage());
		ErrorResponse error = new ErrorResponse("Provider Error", details);
		return new ResponseEntity(error,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ServiceProviderException.class)
	public ResponseEntity<Object> exception(ServiceProviderException ex) {
		log.error(ex.getMessage());
		List<String> details = new ArrayList<>();
		details.add(ex.getLocalizedMessage());
		ErrorResponse error = new ErrorResponse("Server Error", details);
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}