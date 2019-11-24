package com.issam.ppmtool.ppmToolProject.Payload;

import javax.validation.constraints.NotBlank;

public class LoginRequest {
	
	@NotBlank(message = "the username is required")
	private String username;
	@NotBlank(message = "the password is required !")
	private String password;
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "LoginRequest [username=" + username + ", password=" + password + "]";
	}
	
	
}
