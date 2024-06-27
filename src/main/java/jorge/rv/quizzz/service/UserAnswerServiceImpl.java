package jorge.rv.quizzz.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jorge.rv.quizzz.exceptions.ActionRefusedException;
import jorge.rv.quizzz.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.exceptions.UnauthorizedActionException;
import jorge.rv.quizzz.model.Answer;
import jorge.rv.quizzz.model.Question;
import jorge.rv.quizzz.model.Quiz;
import jorge.rv.quizzz.model.UserAnswer;
import jorge.rv.quizzz.repository.QuestionRepository;
import jorge.rv.quizzz.repository.UserAnswerRepository;

@Service("QuestionService")
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
		int count = questionRepository.countByQuiz(userAnswer.getQuiz());

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
