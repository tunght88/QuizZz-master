package com.evn.web.tasks;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.evn.web.service.usermanagement.token.TokenService;

@Component
public class TokenTasks {

	@Autowired
	List<TokenService<?>> tokenServices;

	@Scheduled(fixedDelay = 20000)
	public void cleanUpExpiredTokens() {
		for (TokenService<?> tokenService : tokenServices) {
			tokenService.invalidateExpiredTokensPreviousTo(new Date());
		}
	}
}
