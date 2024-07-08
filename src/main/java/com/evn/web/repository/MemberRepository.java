package com.evn.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evn.web.model.Member;

@Repository("memberRepository")
public interface MemberRepository extends JpaRepository<Member, Long> {
}
