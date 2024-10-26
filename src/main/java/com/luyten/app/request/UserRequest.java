package com.luyten.app.request;


import java.io.Serializable;

import lombok.Data;

@Data
public class UserRequest implements Serializable{

	private static final long serialVersionUID = 3675414865775889054L;
	
	private String name;
	private String lastName;
	private String age;
	private String country;
	private String city;
	private String password;
	private String mail;
	
	private boolean active;
}
