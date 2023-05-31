package com.majchrzw.springboot.ticketSystem.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RegisterForm {
	@NotEmpty( message = "password is needed")
	@Size( min = 6, max = 50)
	private String password;
	@NotEmpty( message = "email is needed")
	@Email
	private String email;
	@NotEmpty( message = "email is needed")
	@Size( min = 4, max = 32)
	private String username;
	
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
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public RegisterForm(String password, String email, String username) {
		this.password = password;
		this.email = email;
		this.username = username;
	}
	
	public RegisterForm(){}
}
