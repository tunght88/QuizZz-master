package com.evn.web.controller.utils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public class SendEmailSSL {

	final static String username = "hdkhcn.slhpc@gmail.com";
	final static String password = "qpvq ubxc zhaz manc";
	@Autowired
	private static Environment env;
	public static void send(String toEmail,String subject, String body, File attacthemnt) {
		Properties prop = new Properties();
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.socketFactory.port", "465");
		prop.put("mail.mime.charset", "UTF-8");
		prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

		Session session = Session.getInstance(prop, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(toEmail));
			message.setSubject(subject, "utf-8");
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			BodyPart messageBodyPart = new MimeBodyPart(); 
			messageBodyPart.setContent(body, "text/plain; charset=UTF-8");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			if(attacthemnt != null) {
				MimeBodyPart attachmentPart = new MimeBodyPart();
				attachmentPart.attachFile(attacthemnt);
				multipart.addBodyPart(attachmentPart);
			}
			message.setContent(multipart, "text/plain");
			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}