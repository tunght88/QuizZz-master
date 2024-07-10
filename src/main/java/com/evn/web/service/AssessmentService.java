package com.evn.web.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evn.web.exceptions.ResourceUnavailableException;
import com.evn.web.exceptions.UnauthorizedActionException;
import com.evn.web.model.Assessment;
import com.evn.web.model.AssessmentResult;
import com.evn.web.model.User;
import com.evn.web.model.support.AssessmentView;

public interface AssessmentService {
	Assessment save(Assessment assessment, User user);
	AssessmentResult save(AssessmentResult assessmentResult, User user);
	AssessmentResult save(AssessmentResult assessmentResult);

	Page<Assessment> findAll(Pageable pageable);

	Page<Assessment> findAllActive(Pageable pageable);


	Assessment find(Long id) throws ResourceUnavailableException;

	Assessment update(Assessment quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	void delete(Assessment quiz) throws ResourceUnavailableException, UnauthorizedActionException;

	Page<Assessment> search(String query, Pageable pageable);
	Page<AssessmentView> getAssessmentsByUser(User user, Pageable pageable);
	Page<AssessmentView> getAssessmentsCreateByUser(User user, Pageable pageable);
	List<AssessmentResult> findAllByAssessmentId(Long assessmentId);
}
