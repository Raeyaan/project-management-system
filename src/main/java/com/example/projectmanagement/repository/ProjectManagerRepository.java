package com.example.projectmanagement.repository;

// ProjectManagerRepository.java

import com.example.projectmanagement.domain.ProjectManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectManagerRepository extends JpaRepository<ProjectManager, Long> {
}