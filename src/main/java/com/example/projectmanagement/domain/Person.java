package com.example.projectmanagement.domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;


@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)

public class Person extends AbstractEntity{
    @Column(name = "name", unique = false, nullable = false)
    @Length(min = 2, max = 50)
    private String name;

    @Column(name = "email", unique = true)
    private String email;
}
