package com.luyten.app.service;

import java.util.Random;

import com.luyten.app.request.UserRequest;

public interface MailService {

	public void sendMailConfimAccount(UserRequest request);
	
}
