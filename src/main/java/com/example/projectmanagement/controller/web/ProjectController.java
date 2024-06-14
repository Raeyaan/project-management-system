package com.example.projectmanagement.controller.web;

import com.example.projectmanagement.domain.Developer;
import com.example.projectmanagement.domain.Project;
import com.example.projectmanagement.domain.ProjectManager;
import com.example.projectmanagement.service.ClientService;
import com.example.projectmanagement.service.DeveloperService;
import com.example.projectmanagement.service.ProjectManagerService;
import com.example.projectmanagement.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {
    private final ProjectService projectService;
    private final ClientService clientService;

    private final DeveloperService developerService;

    private final ProjectManagerService projectManagerService;

    public ProjectController(ProjectService projectService, ClientService clientService, DeveloperService developerService, ProjectManagerService projectManagerService) {
        this.projectService = projectService;
        this.clientService = clientService;
        this.developerService = developerService;
        this.projectManagerService = projectManagerService;
    }

    @GetMapping
    public String listProjects(Model model) {
        model.addAttribute("projects", projectService.getAllProjects());
        return "projects/projects";
    }

    @GetMapping("/add")
    public String showAddProjectForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("projectManagers", projectManagerService.getAllProjectManagers());
        model.addAttribute("assignedDevelopers",developerService.getAllDevelopers());
        return "projects/add-project";
    }

    @GetMapping("/edit/{id}")
    public String showEditProjectForm(@PathVariable Long id, Model model) {
        Project project = projectService.getProjectById(id);
        if (project == null) {
            // Handle project not found
            return "error";
        }
        model.addAttribute("project", project);
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("projectManagers", projectManagerService.getAllProjectManagers());
        model.addAttribute("assignedDevelopers",developerService.getAllDevelopers());
        return "projects/edit-project";
    }

    @PostMapping("/add")
    public String addProject(@ModelAttribute("project") Project project) {
        List<Developer> developers = project.getAssignedDevelopers();
        developers.forEach(developer -> {
            List<Project> assignedProjects = new ArrayList<>(developer.getAssignedProjects());
            assignedProjects.add(project);
            developer.setAssignedProjects(assignedProjects);
            developerService.saveDeveloper(developer);
        });
        ProjectManager selectedProjectManager = project.getManager();
        if (selectedProjectManager != null) {
            Project existingProject = selectedProjectManager.getProject();
            if (existingProject != null) {
                existingProject.setManager(null);
                projectService.saveProject(existingProject);
            }
            selectedProjectManager.setProject(project);
            projectManagerService.saveProjectManager(selectedProjectManager);
        }

        projectService.saveProject(project);
        return "redirect:/projects";
    }

    @PutMapping("/edit/{id}")
    public String updateProject(@PathVariable Long id, @ModelAttribute("project") Project project) {
        Project existingProject = projectService.getProjectById(id);
        if (existingProject == null) {
            // Handle project not found
            return "error";
        }
        ProjectManager selectedProjectManager = project.getManager();
        if (selectedProjectManager != null) {
            Project oldProject = selectedProjectManager.getProject();
            if (oldProject != null && !oldProject.getId().equals(id)) {
                oldProject.setManager(null);
                projectService.saveProject(oldProject);
            }
            selectedProjectManager.setProject(existingProject);
            projectManagerService.saveProjectManager(selectedProjectManager);
        }
        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setStartDate(project.getStartDate());
        existingProject.setEndDate(project.getEndDate());
        existingProject.setStatus(project.getStatus());
        existingProject.setAssignedDevelopers(project.getAssignedDevelopers());
        existingProject.setManager(project.getManager());
        existingProject.setClient(project.getClient());
        // Update other properties as needed
        projectService.saveProject(existingProject);
        return "redirect:/projects";
    }

    @GetMapping("/delete/{id}")
    public String deleteProject(@PathVariable Long id) {
        Project existingProject = projectService.getProjectById(id);
        if (existingProject == null) {
            // Handle project not found
            return "error";
        }
        projectService.deleteProject(id);
        return "redirect:/projects";
    }
}
