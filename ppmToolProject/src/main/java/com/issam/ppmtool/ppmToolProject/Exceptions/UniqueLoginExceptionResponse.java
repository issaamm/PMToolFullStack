package com.issam.ppmtool.ppmToolProject.Exceptions;

public class UniqueLoginExceptionResponse {
	
	private String username;

	public UniqueLoginExceptionResponse(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
