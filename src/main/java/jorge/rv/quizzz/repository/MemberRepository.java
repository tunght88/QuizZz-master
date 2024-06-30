package jorge.rv.quizzz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jorge.rv.quizzz.model.Member;

@Repository("memberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {
}
