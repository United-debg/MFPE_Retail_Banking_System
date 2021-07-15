package com.cognizant.bankmvc.exception;

import java.time.LocalDateTime;
import java.util.HashMap;

import javax.validation.ValidationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;



@RestControllerAdvice
public class GlobalExceptionController{

	/**
	 * @param e
	 * @return HashMap<String, Object>
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public HashMap<String, Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		HashMap<String, Object> map = new HashMap<>();
		map.put("key1", "Validation Error");
		map.put("Exception", e);
		map.put("status", status);
		return map;
	}
	
	/**
	 * @param ex
	 * @return ResponseEntity<CustomErrorResponse>
	 */
	@ExceptionHandler({ CustomerNotFoundException.class })
	public ResponseEntity<CustomErrorResponse> handleConsumerNotFoundException(CustomerNotFoundException ex) {

		CustomErrorResponse response = new CustomErrorResponse();
		response.setTimestamp(LocalDateTime.now());
		response.setMessage(ex.getMessage());
		response.setStatus(HttpStatus.NOT_FOUND);
		response.setReason("Invalid Consumer Id Provided");

		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

	}
	
	/**
	 * @param ex
	 * @return HashMap<String, Object>
	 */
	@ExceptionHandler(BindException.class)
	protected HashMap<String, Object> handleBindException(BindException ex) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		HashMap<String, Object> map = new HashMap<>();
		map.put("key1", "Validation Error");
		map.put("Exception", ex);
		map.put("status", status);
		return map;
	}

	
	/**
	 * @param e
	 * @return ModelAndView
	 */
	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	public ModelAndView handleError405(HttpRequestMethodNotSupportedException e) {
		ModelAndView mav = new ModelAndView("/405");
		mav.addObject("exception", e);
		mav.addObject("errorcode", "405");
		return mav;
	}

	/**
	 * @param e
	 * @return ModelAndView
	 */
	@ExceptionHandler(InternalServerError.class)
	public ModelAndView handleError500(InternalServerError e) {
		ModelAndView mav = new ModelAndView("/500");
		mav.addObject("exception", e);
		mav.addObject("errorcode", "500");
		return mav;
	}

	/**
	 * @param e
	 * @return ModelAndView
	 */
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public ModelAndView handleError404(NoHandlerFoundException e) {
		ModelAndView mav = new ModelAndView("/404");
		mav.addObject("exception", e);
		mav.addObject("errorcode", "404");
		System.out.println("ERROR happended");
		return mav;
	}

	/**
	 * @param e
	 * @return HashMap<String, Object>
	 */
	@ExceptionHandler(ValidationException.class)
	public HashMap<String, Object> handlevalidationError(ValidationException e) {
		HashMap<String, Object> map = new HashMap<>();
		map.put("key1", "Validation Error");
		map.put("Exception", e);
		return map;
	}
}
