package com.example.projectmanagement.controller.web;

// ProjectManagerController.java

import com.example.projectmanagement.domain.ProjectManager;
import com.example.projectmanagement.service.ProjectManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project-managers")
public class ProjectManagerController {
    private final ProjectManagerService projectManagerService;

    public ProjectManagerController(ProjectManagerService projectManagerService) {
        this.projectManagerService = projectManagerService;
    }

    @GetMapping
    public String listProjectManagers(Model model) {
        model.addAttribute("projectManagers", projectManagerService.getAllProjectManagers());
        return "project-managers/project-managers";
    }

    @GetMapping("/add")
    public String showAddProjectManagerForm(Model model) {
        model.addAttribute("projectManager", new ProjectManager());
        return "project-managers/add-project-manager";
    }

    @GetMapping("/edit/{id}")
    public String showEditProjectManagerForm(@PathVariable Long id, Model model) {
        ProjectManager projectManager = projectManagerService.getProjectManagerById(id);
        if (projectManager == null) {
            // Handle project manager not found
            return "error";
        }
        model.addAttribute("projectManager", projectManager);
        return "project-managers/edit-project-manager";
    }

    @PostMapping("/add")
    public String addProjectManager(@ModelAttribute("projectManager") ProjectManager projectManager) {
        projectManagerService.saveProjectManager(projectManager);
        return "redirect:/project-managers";
    }

    @PutMapping("/edit/{id}")
    public String updateProjectManager(@PathVariable Long id, @ModelAttribute("projectManager") ProjectManager projectManager) {
        ProjectManager existingProjectManager = projectManagerService.getProjectManagerById(id);
        if (existingProjectManager == null) {
            // Handle project manager not found
            return "error";
        }
        existingProjectManager.setName(projectManager.getName());
        existingProjectManager.setEmail(projectManager.getEmail());
        existingProjectManager.setExperience(projectManager.getExperience());
        projectManagerService.saveProjectManager(existingProjectManager);
        return "redirect:/project-managers";
    }

    @GetMapping("/delete/{id}")
    public String deleteProjectManager(@PathVariable Long id) {
        ProjectManager existingProjectManager = projectManagerService.getProjectManagerById(id);
        if (existingProjectManager == null) {
            // Handle project manager not found
            return "error";
        }
        projectManagerService.deleteProjectManager(id);
        return "redirect:/project-managers";
    }

}