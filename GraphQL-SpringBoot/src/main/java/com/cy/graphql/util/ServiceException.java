package com.cy.graphql.util;

public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 4138202587753354403L;
	
	public ServiceException() {
		super();
	}

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
