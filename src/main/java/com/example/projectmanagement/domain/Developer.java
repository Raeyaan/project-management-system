package com.example.projectmanagement.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
@Table(name = "developer")
public class Developer extends Person{

    @Column(name = "skill-set",  nullable = false)
    private String skillSet;

    @JsonIgnoreProperties({"assignedDevelopers", "manager", "client", "projectBugs"})
    @ManyToMany(mappedBy = "assignedDevelopers")
    private List<Project> assignedProjects;

    @Override
    public String toString() {
        return getName();
    }


}
