package com.evn.web.service.usermanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evn.web.model.ForgotPasswordToken;
import com.evn.web.model.TokenType;
import com.evn.web.model.User;
import com.evn.web.service.UserService;
import com.evn.web.service.usermanagement.token.TokenDeliverySystem;
import com.evn.web.service.usermanagement.token.TokenServiceForgotPassword;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	private UserService userService;
	private TokenServiceForgotPassword forgotPasswordService;
	private TokenDeliverySystem tokenDeliveryService;

	@Autowired
	public UserManagementServiceImpl(UserService userService, TokenServiceForgotPassword forgotPasswordService,
			TokenDeliverySystem tokenDeliveryService) {
		this.forgotPasswordService = forgotPasswordService;
		this.tokenDeliveryService = tokenDeliveryService;
		this.userService = userService;
	}

	@Override
	public void resendPassword(User user) {
		ForgotPasswordToken token = forgotPasswordService.generateTokenForUser(user);
		tokenDeliveryService.sendTokenToUser(token, user, TokenType.FORGOT_PASSWORD);
	}

	@Override
	public void verifyResetPasswordToken(User user, String token) {
		forgotPasswordService.validateTokenForUser(user, token);
	}

	@Override
	public void updatePassword(User user, String password) {
		userService.updatePassword(user, password);
	}

}
