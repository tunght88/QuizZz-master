package com.evn.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evn.web.model.Question;
import com.evn.web.model.Quiz;

@Repository("QuestionRepository")
public interface QuestionRepository extends JpaRepository<Question, Long> {
	int countByQuiz(Quiz quiz);

	int countByQuizAndIsValidTrue(Quiz quiz);

	List<Question> findByQuizOrderByOrderAsc(Quiz quiz);

	List<Question> findByQuizAndIsValidTrueOrderByOrderAsc(Quiz quiz);
}
