package com.rx.exception;

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
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		Map<String, String> validationErrors = new LinkedHashMap<>();
		ex.getBindingResult().getAllErrors().forEach(
			(error) -> validationErrors.put(
					((FieldError)error).getField(),
					((FieldError) error).getDefaultMessage()
			)
		);
		return ResponseEntity.status(400).body(validationErrors);
	}
	@ExceptionHandler(CustomException.class)
	protected ResponseEntity<?> handleCustomException(Exception ex,WebRequest request){
		Map<String,String> errorMap = new LinkedHashMap<>();
		errorMap.put("error", ex.getMessage().replaceAll("[\"}{:\\]\\[]", "").split("error")[1]);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
	}
	
	@ExceptionHandler(BookingDetailsNotFoundException.class)
	protected ResponseEntity<?> handleBookingDetailsNotFoundException(Exception ex) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
	@ExceptionHandler(UnauthorizedException.class)
	protected ResponseEntity<?> handleUnauthorizedException(Exception ex){
		Map<String, String> map = new LinkedHashMap<>();
		map.put("error", ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
	}
}
