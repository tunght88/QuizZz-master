package com.evn.web.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.evn.web.exceptions.ResourceUnavailableException;
import com.evn.web.exceptions.UnauthorizedActionException;
import com.evn.web.model.Assessment;
import com.evn.web.model.AssessmentResult;
import com.evn.web.model.User;
import com.evn.web.model.support.AssessmentView;
import com.evn.web.repository.AssessmentRepository;
import com.evn.web.repository.AssessmentResultRepository;
import com.evn.web.repository.CouncilRepository;
import com.evn.web.repository.IdeaRepository;
import com.evn.web.repository.MemberRepository;
import com.evn.web.repository.PeriodRepository;

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

	public Page<AssessmentView> getAssessmentsByUser(User user, Pageable pageable){
		String whereClause = "from assessment t1, assessment_user t2, idea t3, level t4, council t5 where t1.id = t2.assessment_id and t1.idea_id = t3.id and t1.level_id = t4.id and t1.council_id = t5.id and t2.user_id = ? and t1.active = 1";
		String countSql = "select count(*) " + whereClause;
		int total = jdbcTemplate.queryForObject(countSql, new Object[] {user.getId()}, Integer.class);
		String pageSql = "select t1.*, t3.text, t4.text, t5.text " + whereClause +  " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();
		List<AssessmentView> lst = jdbcTemplate.query(pageSql, new Object[] {user.getId()}, (rs, rowNum) -> AssessmentView.builder()
												.id(rs.getLong("id"))
												.name(rs.getString("t1.name"))
												.ideaName(rs.getString("t3.text"))
												.level(rs.getString("t4.text"))
												.councilName(rs.getString("t5.text"))
												.description(rs.getString("description")).build());
		return new PageImpl<>(lst, pageable, total);
	}
	public Page<AssessmentView> getAssessmentsCreateByUser(User user, Pageable pageable){
		String whereClause = "from assessment t1, idea t3, level t4, council t5 where  t1.idea_id = t3.id and t1.level_id = t4.id and t1.council_id = t5.id and t1.created_by_id = ? and t1.active = 1";
		String countSql = "select count(*) " + whereClause;
		int total = jdbcTemplate.queryForObject(countSql, new Object[] {user.getId()}, Integer.class);
		String pageSql = "select t1.*, t3.text, t4.text, t5.text, (select COUNT(*) from assessment_result ar where AR.assessment_id = t1.id ) count_result,(select COUNT(*) from assessment_user au  where au.assessment_id  = t1.id ) count_user " + whereClause +  " LIMIT " + pageable.getPageSize() + " OFFSET " + pageable.getOffset();
		List<AssessmentView> lst = jdbcTemplate.query(pageSql, new Object[] {user.getId()}, (rs, rowNum) -> AssessmentView.builder()
												.id(rs.getLong("id"))
												.name(rs.getString("t1.name"))
												.ideaName(rs.getString("t3.text"))
												.level(rs.getString("t4.text"))
												.countResult(rs.getInt("count_result"))
												.countUser(rs.getInt("count_user"))
												.councilName(rs.getString("t5.text"))
												.description(rs.getString("description")).build());
		return new PageImpl<>(lst, pageable, total);
	}
//	@Override
//	public Page<Assessment> getAssessmentsByUser(User user, Pageable pageable){
//		return assessmentRepository.findByUser(user.getId(),pageable);
//	}
}
