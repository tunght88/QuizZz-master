package com.evn.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evn.web.model.Council;

@Repository("councilRepository")
public interface CouncilRepository extends JpaRepository<Council, Long> {
}
