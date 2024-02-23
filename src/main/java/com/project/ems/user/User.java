package com.project.ems.user;

import com.project.ems.authority.Authority;
import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import com.project.ems.experience.Experience;
import com.project.ems.role.Role;
import com.project.ems.study.Study;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_user")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type", discriminatorType = DiscriminatorType.STRING)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(unique = true)
    protected String name;

    @Column(unique = true)
    protected String email;

    protected String password;

    protected String mobile;

    protected String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "role_id")
    protected Role role;

    @ManyToMany
    @JoinTable(name = "user_authority",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "authority_id"))
    protected List<Authority> authorities;

    protected Boolean isActive;

    @Enumerated(EnumType.STRING)
    protected EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    protected Position position;

    @Enumerated(EnumType.STRING)
    protected Grade grade;

    protected Double salary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    protected LocalDate hiredAt;

    @ManyToMany
    @JoinTable(name = "user_experience",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "experience_id"))
    protected List<Experience> experiences;

    @ManyToMany
    @JoinTable(name = "user_study",
          joinColumns = @JoinColumn(name = "user_id"),
          inverseJoinColumns = @JoinColumn(name = "study_id"))
    protected List<Study> studies;
}
