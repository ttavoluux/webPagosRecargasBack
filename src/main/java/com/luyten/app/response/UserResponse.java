package com.luyten.app.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserResponse implements Serializable{

	private static final long serialVersionUID = 8706902766763977105L;
	private Long id;
	
	
	private String name;
	private String lastName;
	private String age;
	private String country;
	private String city;
	private String password;
	private String mail;
	
	private boolean active;
}
