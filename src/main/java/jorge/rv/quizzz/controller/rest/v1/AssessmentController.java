package jorge.rv.quizzz.controller.rest.v1;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jorge.rv.quizzz.model.Assessment;
import jorge.rv.quizzz.model.AssessmentResult;
import jorge.rv.quizzz.model.AuthenticatedUser;
import jorge.rv.quizzz.model.support.Response;
import jorge.rv.quizzz.service.AssessmentService;

@RestController
@RequestMapping(AssessmentController.ROOT_MAPPING)
public class AssessmentController {

	public static final String ROOT_MAPPING = "/api/assessments";
	
	private static final Logger logger = LoggerFactory.getLogger(AssessmentController.class);

	@Autowired
	private AssessmentService assessmentService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Page<Assessment> findAll(Pageable pageable,
			@RequestParam(required = false, defaultValue = "false") Boolean active) {
		
		if (active) {
			return assessmentService.findAllActive(pageable);
		} else {
			return assessmentService.findAll(pageable);
		}
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Page<Assessment> searchAll(Pageable pageable, @RequestParam(required = true) String filter,
			@RequestParam(required = false, defaultValue = "false") Boolean onlyValid) {

		return assessmentService.search(filter, pageable);
	}


	@RequestMapping(value = "/{assessment_id}", method = RequestMethod.GET)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public Assessment find(@PathVariable Long assessment_id) {

		return assessmentService.find(assessment_id);
	}


	@RequestMapping(value = "/{assessment_id}/submit", method = RequestMethod.POST)
	@PreAuthorize("permitAll")
	@ResponseStatus(HttpStatus.OK)
	public AssessmentResult playQuiz(@PathVariable long assessment_id, @RequestBody Response resp,@AuthenticationPrincipal AuthenticatedUser user) {
		Assessment assessment = assessmentService.find(assessment_id);
		AssessmentResult result = new AssessmentResult(resp);
		result.setAssessment(assessment);
		return assessmentService.save(result,user.getUser());
	}

}
