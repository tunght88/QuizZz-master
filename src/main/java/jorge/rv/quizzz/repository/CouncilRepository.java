package jorge.rv.quizzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.model.Council;

@Repository("councilRepository")
public interface CouncilRepository extends JpaRepository<Council, Long> {
}
