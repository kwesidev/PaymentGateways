package com.github.kwesidev.paygate;
@SuppressWarnings("serial")
public class ForbiddenException extends Exception {
	
	public ForbiddenException(String message) {
		super(message);
	}
}
