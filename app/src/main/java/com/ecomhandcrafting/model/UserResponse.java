package com.ecomhandcrafting.model;

public class UserResponse {
	String email;
	String status;
	String satusCode;
	
	public UserResponse(String email, String status, String satusCode) {
		super();
		this.email = email;
		this.status = status;
		this.satusCode = satusCode;
	}
	public UserResponse() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSatusCode() {
		return satusCode;
	}
	public void setSatusCode(String satusCode) {
		this.satusCode = satusCode;
	}
	
	

}
