package com.api.exceptions;

public class DepartmentNotFoundException extends RuntimeException {
	public DepartmentNotFoundException(String msg) {
		super(msg);
	}
}
