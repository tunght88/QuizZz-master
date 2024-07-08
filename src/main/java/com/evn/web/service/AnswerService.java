package com.evn.web.service;

import java.util.List;

import com.evn.web.exceptions.ResourceUnavailableException;
import com.evn.web.exceptions.UnauthorizedActionException;
import com.evn.web.model.Answer;
import com.evn.web.model.Question;

public interface AnswerService {
	Answer save(Answer answer) throws UnauthorizedActionException;

	Answer find(Long id) throws ResourceUnavailableException;

	Answer update(Answer newAnswer) throws UnauthorizedActionException, ResourceUnavailableException;

	void delete(Answer answer) throws UnauthorizedActionException, ResourceUnavailableException;

	List<Answer> findAnswersByQuestion(Question question);

	int countAnswersInQuestion(Question question);
}
