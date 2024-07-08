package com.evn.web.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evn.web.exceptions.ResourceUnavailableException;
import com.evn.web.exceptions.UnauthorizedActionException;
import com.evn.web.model.Quiz;
import com.evn.web.model.User;
import com.evn.web.model.support.Response;
import com.evn.web.model.support.Result;

public interface QuizService {
	Quiz save(Quiz quiz, User user);

	Page<Quiz> findAll(Pageable pageable);

	Page<Quiz> findAllPublished(Pageable pageable);

	Page<Quiz> findQuizzesByUser(User user, Pageable pageable);

	Quiz find(Long id) throws ResourceUnavailableException;

	Quiz update(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	void delete(Quiz quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	Page<Quiz> search(String query, Pageable pageable);

	Result checkAnswers(Quiz quiz, List<Response> answersBundle);

	void publishQuiz(Quiz quiz);
}
