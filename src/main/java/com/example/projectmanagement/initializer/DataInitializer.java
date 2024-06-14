package com.example.projectmanagement.initializer;

import com.example.projectmanagement.domain.*;
import com.example.projectmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final DeveloperRepository developerRepository;
    private final ProjectRepository projectRepository;
    private final ProjectManagerRepository projectManagerRepository;
    private final ProjectBugRepository projectBugRepository;

    public DataInitializer(ClientRepository clientRepository,
                           DeveloperRepository developerRepository,
                           ProjectRepository projectRepository,
                           ProjectManagerRepository projectManagerRepository,
                           ProjectBugRepository projectBugRepository) {
        this.clientRepository = clientRepository;
        this.developerRepository = developerRepository;
        this.projectRepository = projectRepository;
        this.projectManagerRepository = projectManagerRepository;
        this.projectBugRepository = projectBugRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initializeClients();
        initializeDevelopers();
        initializeProjectManagers();
        initializeProjects();
        initializeProjectBugs();
    }

    private void initializeClients() {
        List<Client> clients = Arrays.asList(
                Client.builder()
                        .name("Client A")
                        .email("client.a@example.com")
                        .contactNumber("1234567890")
                        .build(),
                Client.builder()
                        .name("Client B")
                        .email("client.b@example.com")
                        .contactNumber("0987654321")
                        .build(),
                Client.builder()
                        .name("Client C")
                        .email("client.c@example.com")
                        .contactNumber("1122334455")
                        .build()
                // Add more clients as needed
        );
        clientRepository.saveAll(clients);
    }

    private void initializeDevelopers() {
        List<Developer> developers = Arrays.asList(
                Developer.builder()
                        .name("Developer A")
                        .email("developer.a@example.com")
                        .skillSet("Java, Spring")
                        .build(),
                Developer.builder()
                        .name("Developer B")
                        .email("developer.b@example.com")
                        .skillSet("Java, Spring")
                        .build(),
                Developer.builder()
                        .name("Developer C")
                        .email("developer.c@example.com")
                        .skillSet("Java, Spring")
                        .build(),
                Developer.builder()
                        .name("Developer D")
                        .email("developer.d@example.com")
                        .skillSet("Java, Spring Boot")
                        .build(),
                Developer.builder()
                        .name("Developer E")
                        .email("developer.e@example.com")
                        .skillSet("JavaScript, React")
                        .build()
                // Add more developers as needed
        );
        developerRepository.saveAll(developers);
    }

    private void initializeProjectManagers() {
        List<ProjectManager> projectManagers = Arrays.asList(
                ProjectManager.builder()
                        .name("ProjectManager A")
                        .email("pm.a@example.com")
                        .experience("3")
                        .build(),
                ProjectManager.builder()
                        .name("ProjectManager B")
                        .email("pm.b@example.com")
                        .experience("5")
                        .build(),
                ProjectManager.builder()
                        .name("ProjectManager C")
                        .email("pm.c@example.com")
                        .experience("7")
                        .build()
                // Add more project managers as needed
        );
        projectManagerRepository.saveAll(projectManagers);
    }

    private void initializeProjects() {
        List<Client> clients = clientRepository.findAll();
        List<Developer> developers = developerRepository.findAll();
        List<ProjectManager> projectManagers = projectManagerRepository.findAll();

        List<Project> projects = Arrays.asList(
                Project.builder()
                        .name("Project A")
                        .description("This is Project A")
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusMonths(3))
                        .status("In Progress")
                        .client(clients.get(0)) // Assign the first client
                        .assignedDevelopers(developers.subList(0, 2)) // Assign the first two developers
                        .manager(projectManagers.get(0))
                        .build(),
                Project.builder()
                        .name("Project B")
                        .description("This is Project B")
                        .startDate(LocalDate.now().plusDays(1))
                        .endDate(LocalDate.now().plusMonths(6))
                        .status("Not Started")
                        .client(clients.get(1)) // Assign the second client
                        .assignedDevelopers(developers.subList(2, 4)) // Assign the next two developers
                        .manager(projectManagers.get(1))
                        .build(),
                Project.builder()
                        .name("Project C")
                        .description("This is Project C")
                        .startDate(LocalDate.now().minusDays(10))
                        .endDate(LocalDate.now().plusMonths(1))
                        .status("Completed")
                        .client(clients.get(2)) // Assign the third client
                        .assignedDevelopers(developers.subList(1, 3)) // Assign developers
                        .manager(projectManagers.get(2))
                        .build()
                // Add more projects as needed
        );
        projectRepository.saveAll(projects);
    }

    private void initializeProjectBugs() {
        List<Developer> developers = developerRepository.findAll();
        List<Project> projects = projectRepository.findAll();

        List<ProjectBug> projectBugs = Arrays.asList(
                ProjectBug.builder()
                        .title("ProjectBug A")
                        .description("This is ProjectBug A")
                        .status("In Progress")
                        .severity("High")
                        .assignedDeveloper(developers.get(0))
                        .project(projects.get(0))
                        .build(),
                ProjectBug.builder()
                        .title("ProjectBug B")
                        .description("This is ProjectBug B")
                        .status("Open")
                        .severity("Medium")
                        .assignedDeveloper(developers.get(1))
                        .project(projects.get(1))
                        .build(),
                ProjectBug.builder()
                        .title("ProjectBug C")
                        .description("This is ProjectBug C")
                        .status("Closed")
                        .severity("Low")
                        .assignedDeveloper(developers.get(2))
                        .project(projects.get(2))
                        .build()
                // Add more project bugs as needed
        );
        projectBugRepository.saveAll(projectBugs);
    }
}
