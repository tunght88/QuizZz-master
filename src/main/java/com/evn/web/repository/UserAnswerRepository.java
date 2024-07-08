package com.evn.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evn.web.model.Quiz;
import com.evn.web.model.UserAnswer;

@Repository("UserAnswerRepository")
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
	int countByQuiz(Quiz quiz);
	List<UserAnswer> findByQuiz(Quiz quiz);

}
