package com.evn.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evn.web.model.Answer;
import com.evn.web.model.Question;

@Repository("AnswerRepository")
public interface AnswerRepository extends JpaRepository<Answer, Long> {

	int countByQuestion(Question question);

	List<Answer> findByQuestionOrderByOrderAsc(Question question);

}
