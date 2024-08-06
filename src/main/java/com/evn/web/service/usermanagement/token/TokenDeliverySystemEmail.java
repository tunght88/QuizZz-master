package com.evn.web.service.usermanagement.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.evn.web.controller.utils.SendEmailSSL;
import com.evn.web.model.TokenModel;
import com.evn.web.model.TokenType;
import com.evn.web.model.User;

@Service
public class TokenDeliverySystemEmail implements TokenDeliverySystem {

	private static final String BASE_CONFIG_URI = "web.tokens.%s";

	private MessageSource messageSource;
	private JavaMailSender mailSender;

	@Autowired
	public TokenDeliverySystemEmail(MessageSource messageSource, JavaMailSender mailSender) {
		this.messageSource = messageSource;
		this.mailSender = mailSender;
	}

	@Override
	public void sendTokenToUser(TokenModel token, User user, TokenType tokenType) {
		String base_config = String.format(BASE_CONFIG_URI, tokenType.toString().toLowerCase());
		String url = String.format(messageSource.getMessage(base_config + ".url", null, null), user.getId(),
				token.getToken());

		try {
			sendByMail(user, url, base_config);
		} catch (Exception e) {
			// This runs on a thread so it is too late to notify the user. A
			// re-try mechanism could be put in place.
			e.printStackTrace();
		}
	}

	private void sendByMail(User user, String url, String base_config) {
		String subject = messageSource.getMessage(base_config + ".subject", null, null);
		String body = String.format(messageSource.getMessage(base_config + ".body", null, null), user.getUsername(),
				url);
		SendEmailSSL.send(user.getEmail(), subject, body, null);
	}
}
