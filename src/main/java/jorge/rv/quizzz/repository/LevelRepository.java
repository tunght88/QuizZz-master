package jorge.rv.quizzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.model.Level;

@Repository("levelRepository")
public interface LevelRepository extends JpaRepository<Level, Long> {
}
