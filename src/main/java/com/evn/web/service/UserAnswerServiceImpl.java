package com.evn.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evn.web.exceptions.ActionRefusedException;
import com.evn.web.exceptions.ResourceUnavailableException;
import com.evn.web.exceptions.UnauthorizedActionException;
import com.evn.web.model.Answer;
import com.evn.web.model.Question;
import com.evn.web.model.Quiz;
import com.evn.web.model.UserAnswer;
import com.evn.web.repository.QuestionRepository;
import com.evn.web.repository.UserAnswerRepository;

@Service("UserAnswerService")
@Transactional
public class UserAnswerServiceImpl implements UserAnswerService {

	private static final Logger logger = LoggerFactory.getLogger(UserAnswerServiceImpl.class);
	private QuestionRepository questionRepository;
	private UserAnswerRepository userAnswerRepository;

	private AnswerService answerService;

	@Autowired
	public UserAnswerServiceImpl(QuestionRepository questionRepository, AnswerService answerService, UserAnswerRepository userAnswerRepository) {
		this.questionRepository = questionRepository;
		this.answerService = answerService;
		this.userAnswerRepository = userAnswerRepository;
	}

	@Override
	public UserAnswer save(UserAnswer userAnswer) throws UnauthorizedActionException {

		return userAnswerRepository.save(userAnswer);
	}

	@Override
	public UserAnswer find(Long id) throws ResourceUnavailableException {
		UserAnswer userAnswer = userAnswerRepository.findOne(id);

		if (userAnswer == null) {
			logger.error("User Answer " + id + " not found");
			throw new ResourceUnavailableException("User Answer " + id + " not found");
		}

		return userAnswer;
	}

	@Override
	public UserAnswer update(UserAnswer userAnswer) throws ResourceUnavailableException, UnauthorizedActionException {

		return userAnswerRepository.save(userAnswer);
	}

	@Override
	public void delete(UserAnswer userAnswer) throws ResourceUnavailableException, UnauthorizedActionException {


		userAnswerRepository.delete(userAnswer);
	}

	@Override
	public List<UserAnswer> findUserAnswersByQuiz(Quiz quiz) {
		// TODO Auto-generated method stub
		return userAnswerRepository.findByQuiz(quiz);
	}




}
