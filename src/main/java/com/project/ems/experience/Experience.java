package com.project.ems.experience;

import com.project.ems.experience.enums.ExperienceType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
public class Experience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    private String organization;

    private String description;

    @Enumerated(EnumType.STRING)
    private ExperienceType type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;
}
