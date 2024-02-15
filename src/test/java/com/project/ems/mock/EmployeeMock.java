package com.project.ems.mock;

import com.project.ems.employee.Employee;
import com.project.ems.employee.EmployeeDto;
import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.project.ems.mock.AuthorityMock.getMockedAuthorities1;
import static com.project.ems.mock.AuthorityMock.getMockedAuthorities2;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiences2;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.RoleMock.getMockedRole2;
import static com.project.ems.mock.StudyMock.getMockedStudies1;
import static com.project.ems.mock.StudyMock.getMockedStudies2;
import static com.project.ems.mock.TrainerMock.getMockedTrainer1;
import static com.project.ems.mock.TrainerMock.getMockedTrainer2;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMock {

    public static List<Employee> getMockedEmployees() {
        return List.of(getMockedEmployee1(), getMockedEmployee2());
    }

    public static List<Employee> getMockedActiveEmployees() {
        return getMockedEmployees().stream().filter(Employee::getIsActive).toList();
    }

    public static List<EmployeeDto> getMockedEmployeeDtos() {
        return List.of(getMockedEmployeeDto1(), getMockedEmployeeDto2());
    }

    public static List<EmployeeDto> getMockedActiveEmployeeDtos() {
        return getMockedEmployeeDtos().stream().filter(EmployeeDto::getIsActive).toList();
    }

    public static Employee getMockedEmployee1() {
        return Employee.builder()
              .id(1)
              .name("test_name1")
              .email("test_email1@email.com")
              .password("#Test_password1")
              .mobile("+40700000000")
              .address("test_address1")
              .birthday(LocalDate.of(2000, 1, 1))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.JUNIOR)
              .salary(5000.0)
              .hiredAt(LocalDate.of(2020, 1, 1))
              .experiences(getMockedExperiences1())
              .studies(getMockedStudies1())
              .trainer(getMockedTrainer1())
              .build();
    }

    public static Employee getMockedEmployee2() {
        return Employee.builder()
              .id(2)
              .name("test_name2")
              .email("test_email2@email.com")
              .password("#Test_password2")
              .mobile("+40700000001")
              .address("test_address2")
              .birthday(LocalDate.of(2000, 1, 2))
              .role(getMockedRole2())
              .authorities(getMockedAuthorities2())
              .isActive(false)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BACKEND)
              .grade(Grade.JUNIOR)
              .salary(3500.0)
              .hiredAt(LocalDate.of(2020, 1, 2))
              .experiences(getMockedExperiences2())
              .studies(getMockedStudies2())
              .trainer(getMockedTrainer2())
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto1() {
        return EmployeeDto.builder()
              .id(1)
              .name("test_name1")
              .email("test_email1@email.com")
              .password("#Test_password1")
              .mobile("+40700000000")
              .address("test_address1")
              .birthday(LocalDate.of(2000, 1, 1))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.JUNIOR)
              .salary(5000.0)
              .hiredAt(LocalDate.of(2020, 1, 1))
              .experiencesIds(List.of(1, 2))
              .studiesIds(List.of(1, 2))
              .trainerId(1)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto2() {
        return EmployeeDto.builder()
              .id(2)
              .name("test_name2")
              .email("test_email2@email.com")
              .password("#Test_password2")
              .mobile("+40700000001")
              .address("test_address2")
              .birthday(LocalDate.of(2000, 1, 2))
              .roleId(2)
              .authoritiesIds(List.of(3, 4))
              .isActive(false)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BACKEND)
              .grade(Grade.JUNIOR)
              .salary(3500.0)
              .hiredAt(LocalDate.of(2020, 1, 2))
              .experiencesIds(List.of(3, 4))
              .studiesIds(List.of(3, 4))
              .trainerId(2)
              .build();
    }
}
