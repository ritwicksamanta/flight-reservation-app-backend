package com.rx.flights.exception;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		//Map<String, Map<String,String>> response = new LinkedHashMap<>();
		Map<String, String> errors = new LinkedHashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error -> {
			errors.put(
					((FieldError)error).getField(),
					((FieldError)error).getDefaultMessage()
					);
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	@ExceptionHandler(ConstraintException.class)
	protected ResponseEntity<?> handleConstraintException(Exception ex,WebRequest request){
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
	}
	
	@ExceptionHandler({FlightNotFoundException.class,SeatException.class})
	protected ResponseEntity<?> handleResourceNotAvailableException(Exception ex,WebRequest request){
		Map<String, String> errorMap = new HashMap<>();
		errorMap.put("error", ex.getMessage());
		return ResponseEntity.status(404).body(errorMap);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	protected ResponseEntity<?> handleUnauthorizedException(Exception ex){
		Map<String,String> map = new HashMap<>();
		map.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
	}
}
