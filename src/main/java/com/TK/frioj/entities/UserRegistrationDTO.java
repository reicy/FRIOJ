package com.TK.frioj.entities;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


public class UserRegistrationDTO {

	
	
	@Size(min=4, max=20, message="Username must be between 5 and 20 characters long.")
	@Pattern(regexp="^[a-zA-Z0-9]+$", message="Username must be alphanumeric")
	private String login;
	
	@Size(min=6, max=20, message="Password must be between 6 and 20 characters long.")
	@Pattern(regexp="^[a-zA-Z0-9]+$", message="Password must be alphanumeric")
	private String password;
	
	@NotNull
	@Size(min=0, max=30, message="Email must be at most 30 characters long.")
	@Pattern(regexp="[A-Za-z0-9.%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}",message="Invalid email address.")
	private String email;

	
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
}
