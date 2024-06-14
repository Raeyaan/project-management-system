package com.example.projectmanagement.controller.rest;

// DeveloperRestController.java


import com.example.projectmanagement.domain.Developer;
import com.example.projectmanagement.service.DeveloperService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/developers")
public class DeveloperRestController {
    private final DeveloperService developerService;

    public DeveloperRestController(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @GetMapping
    public List<Developer> getAllDevelopers() {
        return developerService.getAllDevelopers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable Long id) {
        Developer developer = developerService.getDeveloperById(id);
        return developer != null
                ? ResponseEntity.ok(developer)
                : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Developer> createDeveloper(@RequestBody Developer developer) {
        Developer createdDeveloper = developerService.saveDeveloper(developer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDeveloper);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Developer> updateDeveloper(@PathVariable Long id, @RequestBody Developer developer) {
        Developer existingDeveloper = developerService.getDeveloperById(id);
        if (existingDeveloper == null) {
            return ResponseEntity.notFound().build();
        }
        developer.setId(id);
        Developer updatedDeveloper = developerService.saveDeveloper(developer);
        return ResponseEntity.ok(updatedDeveloper);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeveloper(@PathVariable Long id) {
        Developer existingDeveloper = developerService.getDeveloperById(id);
        if (existingDeveloper == null) {
            return ResponseEntity.notFound().build();
        }
        developerService.deleteDeveloper(id);
        return ResponseEntity.noContent().build();
    }
}
