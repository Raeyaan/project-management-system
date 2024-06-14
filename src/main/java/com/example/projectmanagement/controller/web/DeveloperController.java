package com.example.projectmanagement.controller.web;

import com.example.projectmanagement.domain.Developer;
import com.example.projectmanagement.service.DeveloperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/developers")
public class DeveloperController {
    private final DeveloperService developerService;

    public DeveloperController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    public String listDevelopers(Model model) {
        model.addAttribute("developers", developerService.getAllDevelopers());
        return "developers/developers";
    }

    @GetMapping("/add")
    public String showAddDeveloperForm(Model model) {
        model.addAttribute("developer", new Developer());
        return "developers/add-developer";
    }

    @GetMapping("/edit/{id}")
    public String showEditDeveloperForm(@PathVariable Long id, Model model) {
        Developer developer = developerService.getDeveloperById(id);
        if (developer == null) {
            // Handle developer not found
            return "error";
        }
        model.addAttribute("developer", developer);
        return "developers/edit-developer";
    }

    @PostMapping("/add")
    public String addDeveloper(@ModelAttribute("developer") Developer developer) {
        developerService.saveDeveloper(developer);
        return "redirect:/developers";
    }

    @PutMapping("/edit/{id}")
    public String updateDeveloper(@PathVariable Long id, @ModelAttribute("developer") Developer developer) {
        Developer existingDeveloper = developerService.getDeveloperById(id);
        if (existingDeveloper == null) {
            // Handle developer not found
            return "error";
        }
        existingDeveloper.setName(developer.getName());
        existingDeveloper.setEmail(developer.getEmail());
        existingDeveloper.setSkillSet(developer.getSkillSet());
        developerService.saveDeveloper(existingDeveloper);
        return "redirect:/developers";
    }

    @GetMapping("/delete/{id}")
    public String deleteDeveloper(@PathVariable Long id) {
        Developer existingDeveloper = developerService.getDeveloperById(id);
        if (existingDeveloper == null) {
            // Handle developer not found
            return "error";
        }
        developerService.deleteDeveloper(id);
        return "redirect:/developers";
    }

}
