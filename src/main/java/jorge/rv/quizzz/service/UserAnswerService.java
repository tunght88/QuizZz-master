package jorge.rv.quizzz.service;

import java.util.List;

import jorge.rv.quizzz.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.exceptions.UnauthorizedActionException;
import jorge.rv.quizzz.model.Answer;
import jorge.rv.quizzz.model.Question;
import jorge.rv.quizzz.model.Quiz;
import jorge.rv.quizzz.model.UserAnswer;

public interface UserAnswerService {
	UserAnswer save(UserAnswer userAnswer) throws UnauthorizedActionException;

	UserAnswer find(Long id) throws ResourceUnavailableException;

	List<UserAnswer> findUserAnswersByQuiz(Quiz quiz);

	UserAnswer update(UserAnswer userAnswer) throws ResourceUnavailableException, UnauthorizedActionException;

	void delete(UserAnswer userAnswer) throws ResourceUnavailableException, UnauthorizedActionException;


}
