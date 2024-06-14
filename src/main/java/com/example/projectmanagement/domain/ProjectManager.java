package com.example.projectmanagement.domain;

// ProjectManager.java


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
@Table(name = ProjectManager.TABLE_NAME)
public class ProjectManager extends Person {

    public static final String TABLE_NAME="project_manager";

    @Column(name = "experience", nullable = false)
    private String experience;

    @JsonIgnoreProperties({"assignedDevelopers", "manager", "client", "projectBugs"})
    @OneToOne(mappedBy = "manager", cascade = CascadeType.ALL)
    private Project project;
    @Override
    public String toString() {
        return getName();
    }
}
