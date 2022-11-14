package com.rx.exception;


public class UnauthorizedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public UnauthorizedException() {
		super("User Not Authenticated. Unauthorized access");
	}
	
	public UnauthorizedException(String message) {
		super(message);
	}

}
