package com.evn.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.evn.web.model.Level;

@Repository("levelRepository")
public interface LevelRepository extends JpaRepository<Level, Long> {
}
