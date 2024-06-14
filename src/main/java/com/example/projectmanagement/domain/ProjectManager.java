package com.example.projectmanagement.domain;

// ProjectManager.java


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = ProjectManager.TABLE_NAME)
public class ProjectManager extends Person {

    public static final String TABLE_NAME="project_manager";

    @Column(name = "experience", nullable = false)
    private String experience;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL)
    private List<Project> managedProjects;
    @Override
    public String toString() {
        return getName();
    }
}
