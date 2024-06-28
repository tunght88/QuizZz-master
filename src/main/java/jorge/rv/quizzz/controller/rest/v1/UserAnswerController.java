package jorge.rv.quizzz.controller.rest.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jorge.rv.quizzz.controller.utils.RestVerifier;
import jorge.rv.quizzz.model.Answer;
import jorge.rv.quizzz.model.Question;
import jorge.rv.quizzz.model.UserAnswer;
import jorge.rv.quizzz.service.AnswerService;
import jorge.rv.quizzz.service.QuestionService;
import jorge.rv.quizzz.service.UserAnswerService;

@RestController
@RequestMapping(UserAnswerController.ROOT_MAPPING)
public class UserAnswerController {

	public static final String ROOT_MAPPING = "/api/user-answers";

	@Autowired
	AnswerService answerService;

	@Autowired
	UserAnswerService userAnswerService;
	@Autowired
	QuestionService questionService;

	@RequestMapping(value = "", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.CREATED)
	public UserAnswer save(@Valid UserAnswer userAnswer, BindingResult result, @RequestParam long question_id, @RequestParam long answer_id) {

		RestVerifier.verifyModelResult(result);

		Question question = questionService.find(question_id);
		Answer answer = answerService.find(answer_id);
		userAnswer.setAnswer(answer);
		userAnswer.setQuestion(question);
		return userAnswerService.save(userAnswer);
	}



	@RequestMapping(value = "/{user_answer_id}", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public UserAnswer find(@PathVariable Long user_answer_id) {

		return userAnswerService.find(user_answer_id);
	}

	@RequestMapping(value = "/{user_answer_id}", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	@ResponseStatus(HttpStatus.OK)
	public UserAnswer update(@PathVariable Long user_answer_id, @Valid UserAnswer userAnswer, BindingResult result) {

		RestVerifier.verifyModelResult(result);

		userAnswer.setId(user_answer_id);
		return userAnswerService.update(userAnswer);
	}

}
