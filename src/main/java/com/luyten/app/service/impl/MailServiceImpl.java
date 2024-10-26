package com.luyten.app.service.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.luyten.app.request.UserRequest;
import com.luyten.app.service.MailService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService{

	private JavaMailSender MAIL_SENDER;
	
	@Override
	public void sendMailConfimAccount(UserRequest request) {
		log.info("Enviando mail a: {}", request.getMail());
		MimeMessage message = MAIL_SENDER.createMimeMessage();
		
		try {
			message.setFrom(new InternetAddress("${spring.mail.username}"));
			message.addRecipients(MimeMessage.RecipientType.TO,request.getMail());
			message.setContent(getHTMLFile(request), "text/html; charset=utf-8");
			message.setSubject("Confirmation of my account to LuytenServices site");
			MAIL_SENDER.send(message);
			log.info("Se envio email de confirmacion a {}", request.getMail());
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}
	}
	

	private String getHTMLFile(UserRequest request) {
		String htmlEMAIL = "Welcome";
		
		try {
			var file = Files.lines(Paths.get("src/main/resources/mail/ConfirmationEmail.html"));
			var html = file.collect(Collectors.joining());
			htmlEMAIL = html.replace("{{fullName}}", request.getName());
			htmlEMAIL = html.replace("{{fullName}}", request.getName() + " " + request.getLastName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	
	return htmlEMAIL;
	}


}
