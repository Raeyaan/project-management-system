package com.example.projectmanagement.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = ProjectBug.TABLE_NAME)
public class ProjectBug extends AbstractEntity{

    public static final String TABLE_NAME="project_bug";

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "severity", nullable = false)
    private String severity;

    @Column(name = "status", nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "developer_id")
    @JsonIgnoreProperties({"assignedProjects", "projectBugs"})
    private Developer assignedDeveloper;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonIgnoreProperties({"assignedDevelopers", "manager", "client", "projectBugs"})

    private Project project;



}