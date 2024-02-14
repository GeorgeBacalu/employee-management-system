package com.project.ems.trainer;

import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDto {

    private Integer id;

    private String name;

    private String email;

    private String password;

    private String mobile;

    private String address;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    private Integer roleId;

    private List<Integer> authoritiesIds;

    private Boolean isActive;

    private EmploymentType employmentType;

    private Position position;

    private Grade grade;

    private Double salary;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate hiredAt;

    private List<Integer> experiencesIds;

    private List<Integer> studiesIds;

    private Integer supervisingTrainerId;

    private Integer nrTrainees;

    private Integer maxTrainees;
}
