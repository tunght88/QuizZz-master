package com.evn.web.service.usermanagement.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.evn.web.model.ForgotPasswordToken;
import com.evn.web.repository.ForgotPasswordTokenRepository;
import com.evn.web.service.usermanagement.utils.TokenGenerator;

@Service
public class TokenServiceForgotPassword extends TokenServiceAbs<ForgotPasswordToken> {

	@Value("${web.tokens.forgot_password.timeout}")
	private Integer expirationTimeInMinutes = 86400;

	@Autowired
	public TokenServiceForgotPassword(ForgotPasswordTokenRepository forgotPasswordTokenRepository,
			TokenGenerator tokenGenerator) {
		super(tokenGenerator, forgotPasswordTokenRepository);
	}

	@Override
	protected ForgotPasswordToken create() {
		return new ForgotPasswordToken();
	}

	@Override
	protected int getExpirationTimeInMinutes() {
		return expirationTimeInMinutes;
	}

	public void setExpirationTimeInMinutes(Integer expirationTimeInMinutes) {
		this.expirationTimeInMinutes = expirationTimeInMinutes;
	}

}
