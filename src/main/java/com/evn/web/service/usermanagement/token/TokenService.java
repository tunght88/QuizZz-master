package com.evn.web.service.usermanagement.token;

import java.util.Date;

import com.evn.web.exceptions.InvalidTokenException;
import com.evn.web.model.TokenModel;
import com.evn.web.model.User;

public interface TokenService<T extends TokenModel> {
	T generateTokenForUser(User user);

	void validateTokenForUser(User user, String token) throws InvalidTokenException;

	void invalidateToken(String token);

	void invalidateExpiredTokensPreviousTo(Date date);
}
