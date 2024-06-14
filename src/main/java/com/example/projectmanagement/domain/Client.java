package com.example.projectmanagement.domain;

// Client.java


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "client")

public class Client extends Person{


    @Column(name = "contact-number", nullable = false, unique = true)
    @Length(min = 12, max = 12)

    private String contactNumber;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private List<Project> projects;

    @Override
    public String toString() {
        return getName();
    }

}