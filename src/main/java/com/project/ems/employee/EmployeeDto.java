package com.project.ems.employee;

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
public class EmployeeDto {

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

    private Integer trainerId;
}
