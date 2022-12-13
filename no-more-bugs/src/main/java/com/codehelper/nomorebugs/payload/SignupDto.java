package com.codehelper.nomorebugs.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class SignupDto {
	
	@NotEmpty(message = "first name must not be empty")
	@Size(min = 2, message = "first name must be atleast 2 characters long")	
	private String firstName;
	
	private String lastName;
	
	@NotEmpty(message = "Email must not be empty")
	@Email(message = "Enter valid email address")	
	private String email;
	
	@NotEmpty(message = "password must not be empty")
	@Size(min = 5, message = "password must be atleast 5 characters long")	
	private String password;
	
	public SignupDto() {
	}

	public SignupDto(
			@NotEmpty(message = "first name must not be empty") @Size(min = 2, message = "first name atleast 2 characters long") String firstName,
			String lastName,
			@NotEmpty(message = "Email must not be empty") @Email(message = "Enter valid email address") String email,
			String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
