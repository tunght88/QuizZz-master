package com.evn.web.service.usermanagement;

import com.evn.web.model.User;

public interface RegistrationService {
	User startRegistration(User user);

	User continueRegistration(User user, String token);

	boolean isRegistrationCompleted(User user);
}
