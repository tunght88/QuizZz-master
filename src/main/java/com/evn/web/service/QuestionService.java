package com.evn.web.service;

import java.util.List;

import com.evn.web.exceptions.ResourceUnavailableException;
import com.evn.web.exceptions.UnauthorizedActionException;
import com.evn.web.model.Answer;
import com.evn.web.model.Question;
import com.evn.web.model.Quiz;

public interface QuestionService {
	Question save(Question question) throws UnauthorizedActionException;

	Question find(Long id) throws ResourceUnavailableException;

	List<Question> findQuestionsByQuiz(Quiz quiz);

	List<Question> findValidQuestionsByQuiz(Quiz quiz);

	Question update(Question question) throws ResourceUnavailableException, UnauthorizedActionException;

	void delete(Question question) throws ResourceUnavailableException, UnauthorizedActionException;


	Answer addAnswerToQuestion(Answer answer, Question question);

	int countQuestionsInQuiz(Quiz quiz);

	int countValidQuestionsInQuiz(Quiz quiz);
}
