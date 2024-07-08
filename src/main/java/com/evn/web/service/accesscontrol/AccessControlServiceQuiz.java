package com.evn.web.service.accesscontrol;

import org.springframework.stereotype.Service;

import com.evn.web.exceptions.UnauthorizedActionException;
import com.evn.web.model.AuthenticatedUser;
import com.evn.web.model.Quiz;

@Service("AccessControlQuiz")
public class AccessControlServiceQuiz extends AccessControlServiceUserOwned<Quiz> {

	/*
	 * As long as the user is authenticated, it can create a Quiz.
	 */
	@Override
	public void canUserCreateObject(AuthenticatedUser user, Quiz object) throws UnauthorizedActionException {
		if (user == null) {
			throw new UnauthorizedActionException();
		}
	}

}
