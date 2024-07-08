package com.evn.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evn.web.model.Idea;

@Repository("ideaRepository")
public interface IdeaRepository extends JpaRepository<Idea, Long> {
}
