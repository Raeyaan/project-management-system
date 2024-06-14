package com.example.projectmanagement.controller.rest;

// ProjectBugRestController.java

import com.example.projectmanagement.domain.ProjectBug;
import com.example.projectmanagement.service.ProjectBugService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-bugs")
public class ProjectBugRestController {
    private final ProjectBugService projectBugService;

    public ProjectBugRestController(ProjectBugService projectBugService) {
        this.projectBugService = projectBugService;
    }

    @GetMapping
    public List<ProjectBug> getAllProjectBugs() {
        return projectBugService.getAllProjectBugs();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectBug> getProjectBugById(@PathVariable Long id) {
        ProjectBug projectBug = projectBugService.getProjectBugById(id);
        return projectBug != null
                ? ResponseEntity.ok(projectBug)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ProjectBug> createProjectBug(@RequestBody ProjectBug projectBug) {
        ProjectBug createdProjectBug = projectBugService.saveProjectBug(projectBug);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProjectBug);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectBug> updateProjectBug(@PathVariable Long id, @RequestBody ProjectBug projectBug) {
        ProjectBug existingProjectBug = projectBugService.getProjectBugById(id);
        if (existingProjectBug == null) {
            return ResponseEntity.notFound().build();
        }
        projectBug.setId(id);
        ProjectBug updatedProjectBug = projectBugService.saveProjectBug(projectBug);
        return ResponseEntity.ok(updatedProjectBug);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectBug(@PathVariable Long id) {
        ProjectBug existingProjectBug = projectBugService.getProjectBugById(id);
        if (existingProjectBug == null) {
            return ResponseEntity.notFound().build();
        }
        projectBugService.deleteProjectBug(id);
        return ResponseEntity.noContent().build();
    }
}
