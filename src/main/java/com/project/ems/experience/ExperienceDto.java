package com.project.ems.experience;

import com.project.ems.experience.enums.ExperienceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExperienceDto {

    @Positive(message = "Experience ID must be positive")
    private Integer id;

    @NotBlank(message = "Experience title must not be blank")
    private String title;

    @NotBlank(message = "Experience organization must not be blank")
    private String organization;

    @NotBlank(message = "Experience description must not be blank")
    private String description;

    @NotNull(message = "Experience type must not be null")
    private ExperienceType type;

    @NotNull(message = "Start date must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startedAt;

    @NotNull(message = "Finish date must not be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate finishedAt;
}
