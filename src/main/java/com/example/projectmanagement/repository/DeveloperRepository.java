package com.example.projectmanagement.repository;

import com.example.projectmanagement.domain.Developer;
import com.example.projectmanagement.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    List<Developer> findByAssignedProjectsContaining(Project project);

}
