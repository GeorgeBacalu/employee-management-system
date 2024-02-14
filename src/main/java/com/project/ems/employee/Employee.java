package com.project.ems.employee;

import com.project.ems.authority.Authority;
import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import com.project.ems.experience.Experience;
import com.project.ems.role.Role;
import com.project.ems.study.Study;
import com.project.ems.trainer.Trainer;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String mobile;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToMany
    @JoinTable(name = "employee_authority",
          joinColumns = @JoinColumn(name = "employee_id"),
          inverseJoinColumns = @JoinColumn(name = "authority_id"))
    private List<Authority> authorities;

    private Boolean isActive;

    @Enumerated(EnumType.STRING)
    private EmploymentType employmentType;

    @Enumerated(EnumType.STRING)
    private Position position;

    @Enumerated(EnumType.STRING)
    private Grade grade;

    private Double salary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiredAt;

    @ManyToMany
    @JoinTable(name = "employee_experience",
          joinColumns = @JoinColumn(name = "employee_id"),
          inverseJoinColumns = @JoinColumn(name = "experience_id"))
    private List<Experience> experiences;

    @ManyToMany
    @JoinTable(name = "employee_study",
          joinColumns = @JoinColumn(name = "employee_id"),
          inverseJoinColumns = @JoinColumn(name = "study_id"))
    private List<Study> studies;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;
}
