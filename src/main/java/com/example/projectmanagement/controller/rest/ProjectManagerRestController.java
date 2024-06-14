package com.example.projectmanagement.controller.rest;

// ProjectManagerRestController.java

import com.example.projectmanagement.domain.ProjectManager;
import com.example.projectmanagement.service.ProjectManagerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-managers")
public class ProjectManagerRestController {
    private final ProjectManagerService projectManagerService;

    public ProjectManagerRestController(ProjectManagerService projectManagerService) {
        this.projectManagerService = projectManagerService;
    }

    @GetMapping
    public List<ProjectManager> getAllProjectManagers() {
        return projectManagerService.getAllProjectManagers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectManager> getProjectManagerById(@PathVariable Long id) {
        ProjectManager projectManager = projectManagerService.getProjectManagerById(id);
        return projectManager != null
                ? ResponseEntity.ok(projectManager)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProjectManager> createProjectManager(@RequestBody ProjectManager projectManager) {
        ProjectManager createdProjectManager = projectManagerService.saveProjectManager(projectManager);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProjectManager);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectManager> updateProjectManager(@PathVariable Long id, @RequestBody ProjectManager projectManager) {
        ProjectManager existingProjectManager = projectManagerService.getProjectManagerById(id);
        if (existingProjectManager == null) {
            return ResponseEntity.notFound().build();
        }
        projectManager.setId(id);
        ProjectManager updatedProjectManager = projectManagerService.saveProjectManager(projectManager);
        return ResponseEntity.ok(updatedProjectManager);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectManager(@PathVariable Long id) {
        ProjectManager existingProjectManager = projectManagerService.getProjectManagerById(id);
        if (existingProjectManager == null) {
            return ResponseEntity.notFound().build();
        }
        projectManagerService.deleteProjectManager(id);
        return ResponseEntity.noContent().build();
    }
}