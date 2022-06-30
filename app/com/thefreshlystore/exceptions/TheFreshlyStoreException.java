package com.thefreshlystore.exceptions;

public class TheFreshlyStoreException extends RuntimeException {
	private String message;
	
	public TheFreshlyStoreException(String message) {
		this.message = message;
	}
	
	public String getErrorMessage() {
		return this.message;
	}
}
