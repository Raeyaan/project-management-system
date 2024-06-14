package com.example.projectmanagement.service;

// ProjectBugService.java

import com.example.projectmanagement.domain.ProjectBug;
import com.example.projectmanagement.repository.ProjectBugRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectBugService {
    private final ProjectBugRepository projectBugRepository;

    public ProjectBugService(ProjectBugRepository projectBugRepository) {
        this.projectBugRepository = projectBugRepository;
    }

    public List<ProjectBug> getAllProjectBugs() {
        return projectBugRepository.findAll();
    }

    public ProjectBug getProjectBugById(Long id) {
        return projectBugRepository.findById(id).orElse(null);
    }

    public ProjectBug saveProjectBug(ProjectBug projectBug) {
        return projectBugRepository.save(projectBug);
    }

    public void deleteProjectBug(Long id) {
        projectBugRepository.deleteById(id);
    }

}
