package com.evn.web.service.usermanagement;

import com.evn.web.model.User;

public interface UserManagementService {

	void resendPassword(User user);

	void verifyResetPasswordToken(User user, String token);

	void updatePassword(User user, String password);

}
