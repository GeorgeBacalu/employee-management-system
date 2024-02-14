package com.project.ems.experience;

import com.project.ems.experience.enums.ExperienceType;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {

    private Integer id;

    private String title;

    private String organization;

    private String description;

    private ExperienceType type;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;
}
