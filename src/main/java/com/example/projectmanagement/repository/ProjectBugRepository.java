package com.example.projectmanagement.repository;

// ProjectBugRepository.java


import com.example.projectmanagement.domain.ProjectBug;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectBugRepository extends JpaRepository<ProjectBug, Long> {
}