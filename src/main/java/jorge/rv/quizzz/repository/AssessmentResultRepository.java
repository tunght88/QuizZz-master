package jorge.rv.quizzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.model.AssessmentResult;

@Repository("assessmentResultRepository")
public interface AssessmentResultRepository extends JpaRepository<AssessmentResult, Long> {
}
