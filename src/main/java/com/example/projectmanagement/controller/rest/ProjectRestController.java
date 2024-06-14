package com.example.projectmanagement.controller.rest;

// ProjectRestController.java


import com.example.projectmanagement.domain.Developer;
import com.example.projectmanagement.domain.Project;
import com.example.projectmanagement.domain.ProjectManager;
import com.example.projectmanagement.service.ClientService;
import com.example.projectmanagement.service.DeveloperService;
import com.example.projectmanagement.service.ProjectManagerService;
import com.example.projectmanagement.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectRestController {
    private final ProjectService projectService;

    private ProjectManagerService projectManagerService;
    private final DeveloperService developerService;
    private final ClientService clientService;

    public ProjectRestController(ProjectService projectService, ProjectManagerService projectManagerService, DeveloperService developerService, ClientService clientService) {
        this.projectService = projectService;
        this.projectManagerService = projectManagerService;
        this.developerService = developerService;
        this.clientService = clientService;
    }

    @GetMapping
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id);
        return project != null
                ? ResponseEntity.ok(project)
                : ResponseEntity.notFound().build();
    }
    @GetMapping("/{projectId}/developers")
    public ResponseEntity<List<Developer>> getDevelopersByProject(@PathVariable Long projectId) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        List<Developer> developers = developerService.getDevelopersByProject(project);
        return ResponseEntity.ok(developers);
    }
    @GetMapping("/{projectId}/manager")
    public ResponseEntity<ProjectManager> getProjectManagerByProject(@PathVariable Long projectId) {
        Project project = projectService.getProjectById(projectId);
        if (project == null) {
            return ResponseEntity.notFound().build();
        }

        ProjectManager projectManager = project.getManager();
        return projectManager != null
                ? ResponseEntity.ok(projectManager)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        Project newProject =  new Project();
        if (project == null) {
            return ResponseEntity.notFound().build();
        }
        newProject.setName(project.getName());
        newProject.setDescription(project.getDescription());
        newProject.setStartDate(project.getStartDate());
        newProject.setEndDate(project.getEndDate());
        newProject.setStatus(project.getStatus());
        newProject.setClient(clientService.getClientById(project.getClient().getId()));

        List<Developer> developerList = new ArrayList<>();
        for( Developer d: project.getAssignedDevelopers()){
            developerList.add(developerService.getDeveloperById(d.getId()));
        }
        newProject.setAssignedDevelopers(developerList);


        ProjectManager selectedProjectManager = projectManagerService.getProjectManagerById(project.getManager().getId());
        if (selectedProjectManager != null) {
            Project oldProject = selectedProjectManager.getProject();
            if (oldProject != null && !oldProject.getId().equals(project.getId())) {
                oldProject.setManager(null);
                projectService.saveProject(oldProject);
            }
            selectedProjectManager.setProject(newProject);
            projectManagerService.saveProjectManager(selectedProjectManager);
        }
        newProject.setManager(selectedProjectManager);
        Project createdProject = projectService.saveProject(newProject);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProject);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        Project existingProject = projectService.getProjectById(id);
        if (existingProject == null) {
            return ResponseEntity.notFound().build();
        }
        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setEndDate(project.getEndDate());
        existingProject.setStatus(project.getStatus());
        existingProject.setClient(clientService.getClientById(project.getClient().getId()));

        List<Developer> developerList = new ArrayList<>();
        for( Developer d: project.getAssignedDevelopers()){
            developerList.add(developerService.getDeveloperById(d.getId()));
        }
        existingProject.setAssignedDevelopers(developerList);


        ProjectManager selectedProjectManager = projectManagerService.getProjectManagerById(project.getManager().getId());
        if (selectedProjectManager != null) {
            Project oldProject = selectedProjectManager.getProject();
            if (oldProject != null && !oldProject.getId().equals(id)) {
                oldProject.setManager(null);
                projectService.saveProject(oldProject);
            }
            selectedProjectManager.setProject(existingProject);
            projectManagerService.saveProjectManager(selectedProjectManager);
        }
        existingProject.setManager(projectManagerService.getProjectManagerById(project.getManager().getId()));
        Project updatedProject = projectService.saveProject(existingProject);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        Project existingProject = projectService.getProjectById(id);
        if (existingProject == null) {
            return ResponseEntity.notFound().build();
        }
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
