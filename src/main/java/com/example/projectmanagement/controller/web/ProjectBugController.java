package com.example.projectmanagement.controller.web;

// ProjectBugController.java


import com.example.projectmanagement.domain.ProjectBug;
import com.example.projectmanagement.service.DeveloperService;
import com.example.projectmanagement.service.ProjectBugService;
import com.example.projectmanagement.service.ProjectService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/project-bugs")
public class ProjectBugController {
    private final ProjectBugService projectBugService;
    private final ProjectService projectService;

    private  final DeveloperService developerService;

    public ProjectBugController(ProjectBugService projectBugService, ProjectService projectService, DeveloperService developerService) {
        this.projectBugService = projectBugService;
        this.projectService = projectService;
        this.developerService = developerService;
    }

    @GetMapping
    public String listProjectBugs(Model model) {
        model.addAttribute("projectBugs", projectBugService.getAllProjectBugs());
        return "project-bugs/project-bugs";
    }

    @GetMapping("/add")
    public String showAddProjectBugForm(Model model) {
        model.addAttribute("projectBug", new ProjectBug());
        model.addAttribute("developers", developerService.getAllDevelopers());
        model.addAttribute("projects", projectService.getAllProjects());
        return "project-bugs/add-project-bug";
    }

    @GetMapping("/edit/{id}")
    public String showEditProjectBugForm(@PathVariable Long id, Model model) {
        ProjectBug projectBug = projectBugService.getProjectBugById(id);
        if (projectBug == null) {
            return "error";
        }
        model.addAttribute("projectBug", projectBug);
        model.addAttribute("developers", developerService.getAllDevelopers());
        model.addAttribute("projects", projectService.getAllProjects());
        return "project-bugs/edit-project-bug";
    }

    @PostMapping("/add")
    public String addProjectBug(@ModelAttribute("projectBug") ProjectBug projectBug) {
        projectBugService.saveProjectBug(projectBug);
        return "redirect:/project-bugs";
    }

    @PutMapping("/edit/{id}")
    public String updateProjectBug(@PathVariable Long id, @ModelAttribute("projectBug") ProjectBug projectBug) {
        ProjectBug existingProjectBug = projectBugService.getProjectBugById(id);
        if (existingProjectBug == null) {
            // Handle project bug not found
            return "error";
        }
        existingProjectBug.setTitle(projectBug.getTitle());
        existingProjectBug.setDescription(projectBug.getDescription());
        existingProjectBug.setSeverity(projectBug.getSeverity());
        existingProjectBug.setStatus(projectBug.getStatus());
        existingProjectBug.setProject(projectBug.getProject());
        existingProjectBug.setAssignedDeveloper(projectBug.getAssignedDeveloper());
        // Update other properties as needed
        projectBugService.saveProjectBug(existingProjectBug);
        return "redirect:/project-bugs";
    }

    @GetMapping("/delete/{id}")
    public String deleteProjectBug(@PathVariable Long id) {
        ProjectBug existingProjectBug = projectBugService.getProjectBugById(id);
        if (existingProjectBug == null) {
            // Handle project bug not found
            return "error";
        }
        projectBugService.deleteProjectBug(id);
        return "redirect:/project-bugs";
    }

}