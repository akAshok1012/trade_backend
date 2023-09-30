package com.tm.app.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component

public class EmailUtil {
	@Autowired
	private JavaMailSender mailSender;

	@Value("${spring.mail.username}")
	private String emailUserName;

	public void sendEmail(String toAddress, String htmlCondent, String subject) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setSubject(subject);
		helper.setFrom(emailUserName);
		helper.setTo(toAddress);
		boolean html = true;
		helper.setText(htmlCondent, html);
		mailSender.send(message);

	}
}
