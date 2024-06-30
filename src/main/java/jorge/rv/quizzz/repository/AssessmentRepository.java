package jorge.rv.quizzz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.model.Assessment;
import jorge.rv.quizzz.model.Quiz;

@Repository("assessmentRepository")
public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
	Page<Assessment> findByActiveTrue(Pageable pageable);
}
