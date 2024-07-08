package com.evn.web.service.usermanagement.token;

import org.springframework.scheduling.annotation.Async;

import com.evn.web.model.TokenModel;
import com.evn.web.model.TokenType;
import com.evn.web.model.User;

public interface TokenDeliverySystem {
	@Async
	void sendTokenToUser(TokenModel token, User user, TokenType tokenType);
}
