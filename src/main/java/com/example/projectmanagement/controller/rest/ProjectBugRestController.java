package com.example.projectmanagement.controller.rest;

// ProjectBugRestController.java

import com.example.projectmanagement.domain.Developer;
import com.example.projectmanagement.domain.Project;
import com.example.projectmanagement.domain.ProjectBug;
import com.example.projectmanagement.service.DeveloperService;
import com.example.projectmanagement.service.ProjectBugService;
import com.example.projectmanagement.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/project-bugs")
public class ProjectBugRestController {
    private final ProjectBugService projectBugService;
    private  final DeveloperService developerService;

    private final ProjectService projectService;

    public ProjectBugRestController(ProjectBugService projectBugService, DeveloperService developerService, ProjectService projectService) {
        this.projectBugService = projectBugService;
        this.developerService = developerService;
        this.projectService = projectService;
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
        Project project = projectService.getProjectById(projectBug.getProject().getId());
        if(project != null){
            if(developerService.getDevelopersByProject(project).contains(developerService.getDeveloperById(projectBug.getAssignedDeveloper().getId()))){}
            else{return ResponseEntity.badRequest().build();}
        }
        else {
             return ResponseEntity.badRequest().build();
        }
        ProjectBug createdProjectBug = projectBugService.saveProjectBug(projectBug);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProjectBug);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectBug> updateProjectBug(@PathVariable Long id, @RequestBody ProjectBug projectBug) {
        ProjectBug existingProjectBug = projectBugService.getProjectBugById(id);
        if (existingProjectBug == null) {
            return ResponseEntity.notFound().build();
        }
        Project project = projectService.getProjectById(projectBug.getProject().getId());
        if(project != null){
            List<Developer> developer = developerService.getAllDevelopers().stream().filter(d->project.getAssignedDevelopers().contains(d)).collect(Collectors.toList());
            if(developer.contains(developerService.getDeveloperById(projectBug.getAssignedDeveloper().getId()))){
            existingProjectBug.setProject(project);
            existingProjectBug.setAssignedDeveloper(developerService.getDeveloperById(projectBug.getAssignedDeveloper().getId()));}
            else{ return ResponseEntity.badRequest().build();}
        }
        else {
            return ResponseEntity.badRequest().build();
        }
        existingProjectBug.setTitle(projectBug.getTitle());
        existingProjectBug.setDescription(projectBug.getDescription());
        existingProjectBug.setSeverity(projectBug.getSeverity());
        existingProjectBug.setStatus(projectBug.getStatus());

        ProjectBug updatedProjectBug = projectBugService.saveProjectBug(existingProjectBug);
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
