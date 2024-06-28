package jorge.rv.quizzz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.model.Quiz;
import jorge.rv.quizzz.model.UserAnswer;

@Repository("UserAnswerRepository")
public interface UserAnswerRepository extends JpaRepository<UserAnswer, Long> {
	int countByQuiz(Quiz quiz);
	List<UserAnswer> findByQuiz(Quiz quiz);

}
