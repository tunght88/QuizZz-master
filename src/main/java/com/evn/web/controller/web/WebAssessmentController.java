package com.evn.web.controller.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.evn.web.model.Assessment;
import com.evn.web.model.AssessmentResult;
import com.evn.web.model.AuthenticatedUser;
import com.evn.web.service.AssessmentService;

@Controller
public class WebAssessmentController {
	@Autowired
	private MessageSource messageSource;
	@Autowired
	AssessmentService assessmentService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView home(HttpSession session, @AuthenticationPrincipal AuthenticatedUser user) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("home");
		mav.addObject("user", user.getUser());
		mav.addObject("welcomeMessage", messageSource.getMessage("label.home.welcome", null, null) + " " + user.getUser().getFullName());
		session.setAttribute("user", user.getUser());
		return mav;
	}

	@RequestMapping(value = "/assessments/list", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView assessments(HttpSession session, @AuthenticationPrincipal AuthenticatedUser user) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("assessments");
		session.setAttribute("user", user);
		return mav;
	}
	@RequestMapping(value = "/assessment/{assessment_id}", method = RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getQuiz(@PathVariable long assessment_id,@AuthenticationPrincipal AuthenticatedUser user) {

		
		Assessment assessment = assessmentService.find(assessment_id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("assessment", assessment);
		mav.addObject("members", assessment.getIdea().getMembers());
		AssessmentResult result = new AssessmentResult();
		if(user != null) {
			mav.addObject("user", user.getUser());
			result.setV_2_1(user.getUsername());
		}
		mav.addObject("result", result);
		mav.setViewName("assessView");

		return mav;
	}


}
