package jorge.rv.quizzz.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import jorge.rv.quizzz.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.exceptions.UnauthorizedActionException;
import jorge.rv.quizzz.model.Assessment;
import jorge.rv.quizzz.model.AssessmentResult;
import jorge.rv.quizzz.model.User;

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

}
