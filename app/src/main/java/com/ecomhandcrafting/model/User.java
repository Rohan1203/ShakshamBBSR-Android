package com.ecomhandcrafting.model;

import retrofit2.Call;

public class User {
	
	String username;
	String password;
	String token;
	String type;
	
	public User() {
		super();
	}
	
	public User(String username, String password, String token, String type) {
		super();
		this.username = username;
		this.password = password;
		this.token = token;
		this.type = type;
	}
	
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
	
	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
