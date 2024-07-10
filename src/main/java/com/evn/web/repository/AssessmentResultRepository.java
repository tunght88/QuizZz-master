package com.evn.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evn.web.model.AssessmentResult;

@Repository("assessmentResultRepository")
public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
	List<AssessmentResult> findAllByAssessmentId(Long assessmentId);
}
