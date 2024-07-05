package jorge.rv.quizzz.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jorge.rv.quizzz.exceptions.ResourceUnavailableException;
import jorge.rv.quizzz.exceptions.UnauthorizedActionException;
import jorge.rv.quizzz.model.Assessment;
import jorge.rv.quizzz.model.AssessmentResult;
import jorge.rv.quizzz.model.User;
import jorge.rv.quizzz.repository.AssessmentRepository;
import jorge.rv.quizzz.repository.AssessmentResultRepository;
import jorge.rv.quizzz.repository.CouncilRepository;
import jorge.rv.quizzz.repository.IdeaRepository;
import jorge.rv.quizzz.repository.MemberRepository;
import jorge.rv.quizzz.repository.PeriodRepository;

@Service("AssessmentService")
@Transactional
public class AssessmentServiceImpl implements AssessmentService {

	private static final Logger logger = LoggerFactory.getLogger(AssessmentServiceImpl.class);
	@Autowired
	private AssessmentRepository assessmentRepository;
	@Autowired
	private AssessmentResultRepository assessmentResultRepository;
	@Autowired
	private PeriodRepository periodRepository;
	@Autowired
	private IdeaRepository ideaRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private CouncilRepository councilRepository;

   @Autowired
   JdbcTemplate jdbcTemplate;


	@Override
	public Assessment save(Assessment assessment, User user) {
		assessment.setCreatedBy(user);
		return assessmentRepository.save(assessment);
	}

	@Override
	public Page<Assessment> findAll(Pageable pageable) {
		return assessmentRepository.findAll(pageable);
	}

	@Override
	public Page<Assessment> findAllActive(Pageable pageable) {
		return assessmentRepository.findByActiveTrue(pageable);
	}

	@Override
	public Assessment find(Long id) throws ResourceUnavailableException {
		Assessment Assessment = assessmentRepository.findOne(id);

		if (Assessment == null) {
			logger.error("Assessment " + id + " not found");
			throw new ResourceUnavailableException("Assessment " + id + " not found");
		}

		return Assessment;
	}

	@Override
	public Assessment update(Assessment newAssessment) throws UnauthorizedActionException, ResourceUnavailableException {
		Assessment currentAssessment = find(newAssessment.getId());

		return assessmentRepository.save(currentAssessment);
	}

	@Override
	public void delete(Assessment Assessment) throws ResourceUnavailableException, UnauthorizedActionException {
		assessmentRepository.delete(Assessment);
	}


	@Override
	public AssessmentResult save(AssessmentResult assessmentResult, User user) {
		assessmentResult.setUser(user);
		assessmentResult.setV_2_1(user.getUsername());
		return assessmentResultRepository.save(assessmentResult);
	}

	@Override
	public AssessmentResult save(AssessmentResult assessmentResult) {
		return assessmentResultRepository.save(assessmentResult);
	}
	@Override
	public Page<Assessment> search(String query, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}


}
