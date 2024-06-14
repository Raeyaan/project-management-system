package com.example.projectmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "project")
public class Project extends AbstractEntity {

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "start-date", nullable = false)
    private LocalDate startDate;


    @Column(name = "end-date", nullable = false)
    private LocalDate endDate;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToMany
    @JoinTable(
            name = "project_developer",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developer_id")
    )
    @JsonIgnoreProperties({"assignedProjects", "projectBugs"})
    private List<Developer> assignedDevelopers;


    @OneToOne
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    @JsonIgnoreProperties("project")
    private ProjectManager manager;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties({"projects"})

    private Client client;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"project", "assignedDeveloper"})
    private List<ProjectBug> projectBugs;


    @Override
    public String toString() {
        return getName();
    }



}

