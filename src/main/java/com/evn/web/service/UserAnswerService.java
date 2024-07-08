package com.evn.web.service;

import java.util.List;

import com.evn.web.exceptions.ResourceUnavailableException;
import com.evn.web.exceptions.UnauthorizedActionException;
import com.evn.web.model.Answer;
import com.evn.web.model.Question;
import com.evn.web.model.Quiz;
import com.evn.web.model.UserAnswer;

public interface UserAnswerService {
	UserAnswer save(UserAnswer userAnswer) throws UnauthorizedActionException;

	UserAnswer find(Long id) throws ResourceUnavailableException;

	List<UserAnswer> findUserAnswersByQuiz(Quiz quiz);

	UserAnswer update(UserAnswer userAnswer) throws ResourceUnavailableException, UnauthorizedActionException;

	void delete(UserAnswer userAnswer) throws ResourceUnavailableException, UnauthorizedActionException;


}
