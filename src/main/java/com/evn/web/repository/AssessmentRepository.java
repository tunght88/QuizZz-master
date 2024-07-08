package com.evn.web.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.evn.web.model.Assessment;
import com.evn.web.model.Quiz;

@Repository("assessmentRepository")
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
	Page<Assessment> findByActiveTrue(Pageable pageable);
//	@Query(value = "select t1.* from assessment t1, assessment_User t2 where t1.id = t2.assessment.id and t2.user.id = ?1 and t1.active = 1 ORDER BY ?#{#pageable}",
//			countQuery="select count(id) from assessment t1, assessment_User t2 where t1.id = t2.assessment.id and t2.user.id = ?1 and t1.active = 1 ORDER BY ?#{#pageable}",
//	           nativeQuery = true)
//	Page<Assessment> findByUser(long userId,Pageable pageable);
}
