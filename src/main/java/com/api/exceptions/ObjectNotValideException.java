package com.api.exceptions;

import java.util.Set;

public class ObjectNotValideException extends RuntimeException{
	private final Set<String> errorMessages;

	public ObjectNotValideException(Set<String> errorMessages) {
		super();
		this.errorMessages = errorMessages;
	}

	public Set<String> getErrorMessages() {
		return errorMessages;
	}
	
}
