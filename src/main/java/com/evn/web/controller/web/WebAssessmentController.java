package com.evn.web.controller.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STOnOff;
import org.openxmlformats.schemas.officeDocument.x2006.sharedTypes.STOnOff1;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTOnOff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.evn.web.controller.utils.RestVerifier;
import com.evn.web.exceptions.ModelVerificationException;
import com.evn.web.model.Assessment;
import com.evn.web.model.AssessmentResult;
import com.evn.web.model.AuthenticatedUser;
import com.evn.web.model.Question;
import com.evn.web.model.Quiz;
import com.evn.web.service.AssessmentService;
import com.evn.web.service.QuestionService;
import com.evn.web.service.QuizService;
import com.evn.web.service.accesscontrol.AccessControlService;

@Controller
public class WebAssessmentController {

	@Autowired
	QuizService quizService;

	@Autowired
	QuestionService questionService;
	@Autowired
	AssessmentService assessmentService;

	@Autowired
	AccessControlService<Quiz> accessControlServiceQuiz;

	@Autowired
	AccessControlService<Question> accessControlServiceQuestion;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home(HttpSession session, @AuthenticationPrincipal AuthenticatedUser user) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		session.setAttribute("user", user);
		return mav;
	}

	@RequestMapping(value = "/createQuiz", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public String newQuiz(Map<String, Object> model) {
		return "createQuiz";
	}

	@RequestMapping(value = "/createQuiz", method = RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public String newQuiz(@AuthenticationPrincipal AuthenticatedUser user, @Valid Quiz quiz, BindingResult result,
			Map<String, Object> model) {
		Quiz newQuiz;

		try {
			RestVerifier.verifyModelResult(result);
			newQuiz = quizService.save(quiz, user.getUser());
		} catch (ModelVerificationException e) {
			return "createQuiz";
		}

		return "redirect:/editQuiz/" + newQuiz.getId();
	}

	@RequestMapping(value = "/editQuiz/{quiz_id}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editQuiz(@PathVariable long quiz_id) {
		Quiz quiz = quizService.find(quiz_id);
		accessControlServiceQuiz.canCurrentUserUpdateObject(quiz);

		ModelAndView mav = new ModelAndView();
		mav.addObject("quiz", quiz);
		mav.setViewName("editQuiz");

		return mav;
	}

	@RequestMapping(value = "/editAnswer/{question_id}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView editAnswer(@PathVariable long question_id) {
		Question question = questionService.find(question_id);
		accessControlServiceQuestion.canCurrentUserUpdateObject(question);

		ModelAndView mav = new ModelAndView();
		mav.addObject("question", question);
		mav.setViewName("editAnswers");

		return mav;
	}

	@RequestMapping(value = "/assessment/{assessment_id}", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	public ModelAndView getQuiz(@PathVariable long assessment_id,@AuthenticationPrincipal AuthenticatedUser user) {

		
		Assessment assessment = assessmentService.find(assessment_id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("assessment", assessment);
		AssessmentResult result = new AssessmentResult();
		if(user != null) {
			mav.addObject("user", user.getUser());
			result.setV_2_1(user.getUsername());
		}
		mav.addObject("result", result);
		mav.setViewName("quizView");

		return mav;
	}


}
