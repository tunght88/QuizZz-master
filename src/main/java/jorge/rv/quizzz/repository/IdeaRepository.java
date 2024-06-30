package jorge.rv.quizzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.model.Idea;

@Repository("ideaRepository")
public interface IdeaRepository extends JpaRepository<Idea, Long> {
}
