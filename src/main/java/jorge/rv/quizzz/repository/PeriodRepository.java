package jorge.rv.quizzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.model.Period;

@Repository("periodRepository")
public interface PeriodRepository extends JpaRepository<Period, Long> {
}
