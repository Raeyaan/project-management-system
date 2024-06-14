package com.example.projectmanagement.service;


import com.example.projectmanagement.domain.Developer;
import com.example.projectmanagement.repository.DeveloperRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperService {
    private final DeveloperRepository developerRepository;

    public DeveloperService(DeveloperRepository developerRepository) {
        this.developerRepository = developerRepository;
    }

    public List<Developer> getAllDevelopers() {
        return developerRepository.findAll();
    }

    public Developer getDeveloperById(Long id) {
        return developerRepository.findById(id).orElse(null);
    }

    public Developer saveDeveloper(Developer developer) {
        return developerRepository.save(developer);
    }

    public void deleteDeveloper(Long id) {
        developerRepository.deleteById(id);
    }

}
