package com.project.ems.trainer;

import com.project.ems.authority.Authority;
import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import com.project.ems.experience.Experience;
import com.project.ems.role.Role;
import com.project.ems.study.Study;
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
public class Trainer {

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
    @JoinTable(name = "trainer_authority",
          joinColumns = @JoinColumn(name = "trainer_id"),
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
    @JoinTable(name = "trainer_experience",
          joinColumns = @JoinColumn(name = "trainer_id"),
          inverseJoinColumns = @JoinColumn(name = "experience_id"))
    private List<Experience> experiences;

    @ManyToMany
    @JoinTable(name = "trainer_study",
          joinColumns = @JoinColumn(name = "trainer_id"),
          inverseJoinColumns = @JoinColumn(name = "study_id"))
    private List<Study> studies;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer supervisingTrainer;

    private Integer nrTrainees;

    private Integer maxTrainees;
}
