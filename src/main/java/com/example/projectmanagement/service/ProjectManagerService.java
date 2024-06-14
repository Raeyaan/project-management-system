package com.example.projectmanagement.service;

// ProjectManagerService.java

import com.example.projectmanagement.domain.ProjectManager;
import com.example.projectmanagement.repository.ProjectManagerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectManagerService {
    private final ProjectManagerRepository projectManagerRepository;

    public ProjectManagerService(ProjectManagerRepository projectManagerRepository) {
        this.projectManagerRepository = projectManagerRepository;
    }

    public List<ProjectManager> getAllProjectManagers() {
        return projectManagerRepository.findAll();
    }

    public ProjectManager getProjectManagerById(Long id) {
        return projectManagerRepository.findById(id).orElse(null);
    }

    public ProjectManager saveProjectManager(ProjectManager projectManager) {
        return projectManagerRepository.save(projectManager);
    }

    public void deleteProjectManager(Long id) {
        projectManagerRepository.deleteById(id);
    }

}