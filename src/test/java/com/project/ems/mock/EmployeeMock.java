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
import static com.project.ems.mock.ExperienceMock.*;
import static com.project.ems.mock.RoleMock.getMockedRole1;
import static com.project.ems.mock.StudyMock.*;
import static com.project.ems.mock.TrainerMock.*;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeMock {

    public static List<Employee> getMockedEmployees() {
        return List.of(getMockedEmployee1(), getMockedEmployee2(), getMockedEmployee3(), getMockedEmployee4(), getMockedEmployee5(), getMockedEmployee6(), getMockedEmployee7(), getMockedEmployee8(), getMockedEmployee9(),
              getMockedEmployee10(), getMockedEmployee11(), getMockedEmployee12(), getMockedEmployee13(), getMockedEmployee14(), getMockedEmployee15(), getMockedEmployee16(), getMockedEmployee17(), getMockedEmployee18(),
              getMockedEmployee19(), getMockedEmployee20(), getMockedEmployee21(), getMockedEmployee22(), getMockedEmployee23(), getMockedEmployee24(), getMockedEmployee25(), getMockedEmployee26(), getMockedEmployee27(),
              getMockedEmployee28(), getMockedEmployee29(), getMockedEmployee30(), getMockedEmployee31(), getMockedEmployee32(), getMockedEmployee33(), getMockedEmployee34(), getMockedEmployee35(), getMockedEmployee36());
    }

    public static List<EmployeeDto> getMockedEmployeeDtos() {
        return List.of(getMockedEmployeeDto1(), getMockedEmployeeDto2(), getMockedEmployeeDto3(), getMockedEmployeeDto4(), getMockedEmployeeDto5(), getMockedEmployeeDto6(), getMockedEmployeeDto7(), getMockedEmployeeDto8(), getMockedEmployeeDto9(),
              getMockedEmployeeDto10(), getMockedEmployeeDto11(), getMockedEmployeeDto12(), getMockedEmployeeDto13(), getMockedEmployeeDto14(), getMockedEmployeeDto15(), getMockedEmployeeDto16(), getMockedEmployeeDto17(), getMockedEmployeeDto18(),
              getMockedEmployeeDto19(), getMockedEmployeeDto20(), getMockedEmployeeDto21(), getMockedEmployeeDto22(), getMockedEmployeeDto23(), getMockedEmployeeDto24(), getMockedEmployeeDto25(), getMockedEmployeeDto26(), getMockedEmployeeDto27(),
              getMockedEmployeeDto28(), getMockedEmployeeDto29(), getMockedEmployeeDto30(), getMockedEmployeeDto31(), getMockedEmployeeDto32(), getMockedEmployeeDto33(), getMockedEmployeeDto34(), getMockedEmployeeDto35(), getMockedEmployeeDto36());
    }

    public static List<Employee> getMockedActiveEmployees() {
        return getMockedEmployees().stream().filter(Employee::getIsActive).toList();
    }

    public static List<EmployeeDto> getMockedActiveEmployeeDtos() {
        return getMockedEmployeeDtos().stream().filter(EmployeeDto::getIsActive).toList();
    }

    public static List<Employee> getMockedEmployeesPage1() {
        return List.of(getMockedEmployee1(), getMockedEmployee2());
    }

    public static List<Employee> getMockedEmployeesPage2() {
        return List.of(getMockedEmployee3(), getMockedEmployee4());
    }

    public static List<Employee> getMockedEmployeesPage3() {
        return List.of(getMockedEmployee5(), getMockedEmployee6());
    }

    public static List<EmployeeDto> getMockedEmployeeDtosPage1() {
        return List.of(getMockedEmployeeDto1(), getMockedEmployeeDto2());
    }

    public static List<EmployeeDto> getMockedEmployeeDtosPage2() {
        return List.of(getMockedEmployeeDto3(), getMockedEmployeeDto4());
    }

    public static List<EmployeeDto> getMockedEmployeeDtosPage3() {
        return List.of(getMockedEmployeeDto5(), getMockedEmployeeDto6());
    }

    public static Employee getMockedEmployee1() {
        return Employee.builder()
              .id(1)
              .name("Abigail Johnson")
              .email("abigail.johnson@example.com")
              .password("#Abigail_Johnson_Password0")
              .mobile("+40754321837")
              .address("999 Oak St, Athens, Greece")
              .birthday(LocalDate.of(2000, 10, 2))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.JUNIOR)
              .salary(4000.0)
              .hiredAt(LocalDate.of(2023, 1, 1))
              .experiences(getMockedExperiences1())
              .studies(getMockedStudies1())
              .trainer(getMockedTrainer1())
              .build();
    }

    public static Employee getMockedEmployee2() {
        return Employee.builder()
              .id(2)
              .name("Michael Davis")
              .email("michael.davis@example.com")
              .password("#Michael_Davis_Password0")
              .mobile("+40789012638")
              .address("111 Oak St, Madrid, Spain")
              .birthday(LocalDate.of(1994, 5, 16))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.JUNIOR)
              .salary(3500.0)
              .hiredAt(LocalDate.of(2022, 10, 4))
              .experiences(getMockedExperiences2())
              .studies(getMockedStudies2())
              .trainer(getMockedTrainer2())
              .build();
    }

    public static Employee getMockedEmployee3() {
        return Employee.builder()
              .id(3)
              .name("Mia Wilson")
              .email("mia.wilson@example.com")
              .password("#Mia_Wilson_Password0")
              .mobile("+40723145639")
              .address("333 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1990, 12, 29))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.MID)
              .salary(5000.0)
              .hiredAt(LocalDate.of(2020, 9, 12))
              .experiences(getMockedExperiences3())
              .studies(getMockedStudies3())
              .trainer(getMockedTrainer3())
              .build();
    }

    public static Employee getMockedEmployee4() {
        return Employee.builder()
              .id(4)
              .name("James Lee")
              .email("james.lee@example.com")
              .password("#James_Lee_Password0")
              .mobile("+40787654340")
              .address("555 Pine St, Seoul, South Korea")
              .birthday(LocalDate.of(1991, 8, 11))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.SENIOR)
              .salary(6500.0)
              .hiredAt(LocalDate.of(2021, 8, 15))
              .experiences(getMockedExperiences4())
              .studies(getMockedStudies4())
              .trainer(getMockedTrainer4())
              .build();
    }

    public static Employee getMockedEmployee5() {
        return Employee.builder()
              .id(5)
              .name("Maria Thompson")
              .email("maria.thompson@example.com")
              .password("#Maria_Thompson_Password0")
              .mobile("+40754321841")
              .address("777 Elm St, Beijing, China")
              .birthday(LocalDate.of(1993, 3, 24))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.JUNIOR)
              .salary(3000.0)
              .hiredAt(LocalDate.of(2023, 2, 9))
              .experiences(getMockedExperiences5())
              .studies(getMockedStudies5())
              .trainer(getMockedTrainer5())
              .build();
    }

    public static Employee getMockedEmployee6() {
        return Employee.builder()
              .id(6)
              .name("Ethan Smith")
              .email("ethan.smith@example.com")
              .password("#Ethan_Smith_Password0")
              .mobile("+40789012642")
              .address("999 Oak St, Cape Town, South Africa")
              .birthday(LocalDate.of(1989, 11, 6))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.JUNIOR)
              .salary(2500.0)
              .hiredAt(LocalDate.of(2022, 11, 16))
              .experiences(getMockedExperiences6())
              .studies(getMockedStudies6())
              .trainer(getMockedTrainer6())
              .build();
    }

    public static Employee getMockedEmployee7() {
        return Employee.builder()
              .id(7)
              .name("Olivia Smith")
              .email("olivia.smith@example.com")
              .password("#Olivia_Smith_Password0")
              .mobile("+40723145643")
              .address("111 Elm St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(1994, 6, 19))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.MID)
              .salary(4000.0)
              .hiredAt(LocalDate.of(2021, 7, 19))
              .experiences(getMockedExperiences7())
              .studies(getMockedStudies1())
              .trainer(getMockedTrainer7())
              .build();
    }

    public static Employee getMockedEmployee8() {
        return Employee.builder()
              .id(8)
              .name("Emily Davis")
              .email("emily.davis@example.com")
              .password("#Emily_Davis_Password0")
              .mobile("+40787654344")
              .address("333 Elm St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(1998, 1, 1))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.SENIOR)
              .salary(7000.0)
              .hiredAt(LocalDate.of(2020, 2, 16))
              .experiences(getMockedExperiences8())
              .studies(getMockedStudies2())
              .trainer(getMockedTrainer8())
              .build();
    }

    public static Employee getMockedEmployee9() {
        return Employee.builder()
              .id(9)
              .name("Henry Wilson")
              .email("henry.wilson@example.com")
              .password("#Henry_Wilson_Password0")
              .mobile("+40754321845")
              .address("555 Pine St, Mexico City, Mexico")
              .birthday(LocalDate.of(2001, 8, 14))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.JUNIOR)
              .salary(4500.0)
              .hiredAt(LocalDate.of(2022, 10, 14))
              .experiences(getMockedExperiences1())
              .studies(getMockedStudies3())
              .trainer(getMockedTrainer9())
              .build();
    }

    public static Employee getMockedEmployee10() {
        return Employee.builder()
              .id(10)
              .name("Scarlett Thompson")
              .email("scarlett.thompson@example.com")
              .password("#Scarlett_Thompson_Password0")
              .mobile("+40789012646")
              .address("777 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(2002, 3, 28))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.JUNIOR)
              .salary(5000.0)
              .hiredAt(LocalDate.of(2022, 3, 28))
              .experiences(getMockedExperiences2())
              .studies(getMockedStudies4())
              .trainer(getMockedTrainer10())
              .build();
    }

    public static Employee getMockedEmployee11() {
        return Employee.builder()
              .id(11)
              .name("Jacob Brown")
              .email("jacob.brown@example.com")
              .password("#Jacob_Brown_Password0")
              .mobile("+40723145647")
              .address("999 Pine St, Paris, France")
              .birthday(LocalDate.of(1999, 11, 10))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.MID)
              .salary(6000.0)
              .hiredAt(LocalDate.of(2021, 11, 10))
              .experiences(getMockedExperiences3())
              .studies(getMockedStudies5())
              .trainer(getMockedTrainer11())
              .build();
    }

    public static Employee getMockedEmployee12() {
        return Employee.builder()
              .id(12)
              .name("Ava Smith")
              .email("ava.smith@example.com")
              .password("#Ava_Smith_Password0")
              .mobile("+40787654348")
              .address("111 Pine St, London, UK")
              .birthday(LocalDate.of(1986, 6, 23))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.SENIOR)
              .salary(7500.0)
              .hiredAt(LocalDate.of(2020, 5, 28))
              .experiences(getMockedExperiences4())
              .studies(getMockedStudies6())
              .trainer(getMockedTrainer12())
              .build();
    }

    public static Employee getMockedEmployee13() {
        return Employee.builder()
              .id(13)
              .name("Oliver Johnson")
              .email("oliver.johnson@example.com")
              .password("#Oliver_Johnson_Password0")
              .mobile("+40754321849")
              .address("333 Elm St, Berlin, Germany")
              .birthday(LocalDate.of(1988, 2, 5))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.JUNIOR)
              .salary(4000.0)
              .hiredAt(LocalDate.of(2023, 1, 20))
              .experiences(getMockedExperiences5())
              .studies(getMockedStudies1())
              .trainer(getMockedTrainer13())
              .build();
    }

    public static Employee getMockedEmployee14() {
        return Employee.builder()
              .id(14)
              .name("Sophia Wilson")
              .email("sophia.wilson@example.com")
              .password("#Sophia_Wilson_Password0")
              .mobile("+40789012650")
              .address("555 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(1994, 9, 19))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.JUNIOR)
              .salary(4500.0)
              .hiredAt(LocalDate.of(2022, 4, 16))
              .experiences(getMockedExperiences6())
              .studies(getMockedStudies2())
              .trainer(getMockedTrainer14())
              .build();
    }

    public static Employee getMockedEmployee15() {
        return Employee.builder()
              .id(15)
              .name("William Harris")
              .email("william.harris@example.com")
              .password("#William_Harris_Password0")
              .mobile("+40723145651")
              .address("777 Pine St, Athens, Greece")
              .birthday(LocalDate.of(1996, 4, 3))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.MID)
              .salary(6000.0)
              .hiredAt(LocalDate.of(2021, 5, 18))
              .experiences(getMockedExperiences7())
              .studies(getMockedStudies3())
              .trainer(getMockedTrainer15())
              .build();
    }

    public static Employee getMockedEmployee16() {
        return Employee.builder()
              .id(16)
              .name("Mia Johnson")
              .email("mia.johnson@example.com")
              .password("#Mia_Johnson_Password0")
              .mobile("+40787654352")
              .address("999 Oak St, Madrid, Spain")
              .birthday(LocalDate.of(1998, 11, 16))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.SENIOR)
              .salary(7000.0)
              .hiredAt(LocalDate.of(2020, 9, 15))
              .experiences(getMockedExperiences8())
              .studies(getMockedStudies4())
              .trainer(getMockedTrainer16())
              .build();
    }

    public static Employee getMockedEmployee17() {
        return Employee.builder()
              .id(17)
              .name("Jacob Lee")
              .email("jacob.lee@example.com")
              .password("#Jacob_Lee_Password0")
              .mobile("+40754321853")
              .address("111 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1997, 6, 29))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DESIGN)
              .grade(Grade.JUNIOR)
              .salary(3500.0)
              .hiredAt(LocalDate.of(2023, 1, 31))
              .experiences(getMockedExperiences1())
              .studies(getMockedStudies5())
              .trainer(getMockedTrainer17())
              .build();
    }

    public static Employee getMockedEmployee18() {
        return Employee.builder()
              .id(18)
              .name("Charlotte Brown")
              .email("charlotte.brown@example.com")
              .password("#Charlotte_Brown_Password0")
              .mobile("+40789012654")
              .address("333 Pine St, Seoul, South Korea")
              .birthday(LocalDate.of(2000, 2, 12))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DESIGN)
              .grade(Grade.JUNIOR)
              .salary(4000.0)
              .hiredAt(LocalDate.of(2023, 3, 17))
              .experiences(getMockedExperiences2())
              .studies(getMockedStudies6())
              .trainer(getMockedTrainer18())
              .build();
    }

    public static Employee getMockedEmployee19() {
        return Employee.builder()
              .id(19)
              .name("Ethan Johnson")
              .email("ethan.johnson@example.com")
              .password("#Ethan_Johnson_Password0")
              .mobile("+40723145655")
              .address("555 Elm St, Beijing, China")
              .birthday(LocalDate.of(1998, 9, 25))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DESIGN)
              .grade(Grade.MID)
              .salary(5500.0)
              .hiredAt(LocalDate.of(2022, 2, 28))
              .experiences(getMockedExperiences3())
              .studies(getMockedStudies1())
              .trainer(getMockedTrainer19())
              .build();
    }

    public static Employee getMockedEmployee20() {
        return Employee.builder()
              .id(20)
              .name("Sophia Davis")
              .email("sophia.davis@example.com")
              .password("#Sophia_Davis_Password0")
              .mobile("+40787654356")
              .address("777 Elm St, Cape Town, South Africa")
              .birthday(LocalDate.of(2001, 7, 9))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DESIGN)
              .grade(Grade.SENIOR)
              .salary(6500.0)
              .hiredAt(LocalDate.of(2021, 8, 9))
              .experiences(getMockedExperiences4())
              .studies(getMockedStudies2())
              .trainer(getMockedTrainer20())
              .build();
    }

    public static Employee getMockedEmployee21() {
        return Employee.builder()
              .id(21)
              .name("Jacob Thompson")
              .email("jacob.thompson@example.com")
              .password("#Jacob_Thompson_Password0")
              .mobile("+40754321857")
              .address("999 Pine St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(1996, 2, 22))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.JUNIOR)
              .salary(5000.0)
              .hiredAt(LocalDate.of(2023, 6, 12))
              .experiences(getMockedExperiences5())
              .studies(getMockedStudies3())
              .trainer(getMockedTrainer21())
              .build();
    }

    public static Employee getMockedEmployee22() {
        return Employee.builder()
              .id(22)
              .name("Liam Wilson")
              .email("liam.wilson@example.com")
              .password("#Liam_Wilson_Password0")
              .mobile("+40789012658")
              .address("111 Elm St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(1995, 10, 7))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.JUNIOR)
              .salary(5500.0)
              .hiredAt(LocalDate.of(2022, 10, 30))
              .experiences(getMockedExperiences6())
              .studies(getMockedStudies4())
              .trainer(getMockedTrainer22())
              .build();
    }

    public static Employee getMockedEmployee23() {
        return Employee.builder()
              .id(23)
              .name("Ava Brown")
              .email("ava.brown@example.com")
              .password("#Ava_Brown_Password0")
              .mobile("+40723145659")
              .address("333 Elm St, Mexico City, Mexico")
              .birthday(LocalDate.of(1998, 7, 21))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.MID)
              .salary(7000.0)
              .hiredAt(LocalDate.of(2020, 12, 15))
              .experiences(getMockedExperiences7())
              .studies(getMockedStudies5())
              .trainer(getMockedTrainer23())
              .build();
    }

    public static Employee getMockedEmployee24() {
        return Employee.builder()
              .id(24)
              .name("Scarlett Wilson")
              .email("scarlett.wilson@example.com")
              .password("#Scarlett_Wilson_Password0")
              .mobile("+40787654360")
              .address("555 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(2001, 5, 5))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.SENIOR)
              .salary(8000.0)
              .hiredAt(LocalDate.of(2020, 4, 10))
              .experiences(getMockedExperiences8())
              .studies(getMockedStudies6())
              .trainer(getMockedTrainer24())
              .build();
    }

    public static Employee getMockedEmployee25() {
        return Employee.builder()
              .id(25)
              .name("Noah Martinez")
              .email("noah.martinez@example.com")
              .password("#Noah_Martinez_Password0")
              .mobile("+40754321861")
              .address("777 Pine St, Paris, France")
              .birthday(LocalDate.of(1999, 12, 17))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.MACHINE_LEARNING)
              .grade(Grade.JUNIOR)
              .salary(4750.0)
              .hiredAt(LocalDate.of(2023, 3, 27))
              .experiences(getMockedExperiences1())
              .studies(getMockedStudies1())
              .trainer(getMockedTrainer25())
              .build();
    }

    public static Employee getMockedEmployee26() {
        return Employee.builder()
              .id(26)
              .name("Mia Anderson")
              .email("mia.anderson@example.com")
              .password("#Mia_Anderson_Password0")
              .mobile("+40789012662")
              .address("999 Oak St, London, UK")
              .birthday(LocalDate.of(2002, 9, 30))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.MACHINE_LEARNING)
              .grade(Grade.JUNIOR)
              .salary(5250.0)
              .hiredAt(LocalDate.of(2022, 11, 18))
              .experiences(getMockedExperiences2())
              .studies(getMockedStudies2())
              .trainer(getMockedTrainer26())
              .build();
    }

    public static Employee getMockedEmployee27() {
        return Employee.builder()
              .id(27)
              .name("Grace Lee")
              .email("grace.lee@example.com")
              .password("#Grace_Lee_Password0")
              .mobile("+40723145663")
              .address("111 Elm St, Berlin, Germany")
              .birthday(LocalDate.of(1996, 5, 14))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.MACHINE_LEARNING)
              .grade(Grade.MID)
              .salary(7000.0)
              .hiredAt(LocalDate.of(2022, 6, 29))
              .experiences(getMockedExperiences3())
              .studies(getMockedStudies3())
              .trainer(getMockedTrainer27())
              .build();
    }

    public static Employee getMockedEmployee28() {
        return Employee.builder()
              .id(28)
              .name("Benjamin Taylor")
              .email("benjamin.taylor@example.com")
              .password("#Benjamin_Taylor_Password0")
              .mobile("+40787654364")
              .address("333 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(2002, 12, 27))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.MACHINE_LEARNING)
              .grade(Grade.SENIOR)
              .salary(8750.0)
              .hiredAt(LocalDate.of(2020, 7, 16))
              .experiences(getMockedExperiences4())
              .studies(getMockedStudies4())
              .trainer(getMockedTrainer28())
              .build();
    }

    public static Employee getMockedEmployee29() {
        return Employee.builder()
              .id(29)
              .name("Elijah Roberts")
              .email("elijah.roberts@example.com")
              .password("#Elijah_Roberts_Password0")
              .mobile("+40754321865")
              .address("555 Pine St, Sydney, Australia")
              .birthday(LocalDate.of(2003, 1, 27))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.JUNIOR)
              .salary(4500.0)
              .hiredAt(LocalDate.of(2023, 5, 19))
              .experiences(getMockedExperiences5())
              .studies(getMockedStudies5())
              .trainer(getMockedTrainer29())
              .build();
    }

    public static Employee getMockedEmployee30() {
        return Employee.builder()
              .id(30)
              .name("Amelia Walker")
              .email("amelia.walker@example.com")
              .password("#Amelia_Walker_Password0")
              .mobile("+40789012666")
              .address("777 Oak St, Rome, Italy")
              .birthday(LocalDate.of(1992, 8, 12))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.JUNIOR)
              .salary(4750.0)
              .hiredAt(LocalDate.of(2023, 3, 19))
              .experiences(getMockedExperiences6())
              .studies(getMockedStudies6())
              .trainer(getMockedTrainer30())
              .build();
    }

    public static Employee getMockedEmployee31() {
        return Employee.builder()
              .id(31)
              .name("Daniel Green")
              .email("daniel.green@example.com")
              .password("#Daniel_Green_Password0")
              .mobile("+40723145667")
              .address("999 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(1996, 3, 27))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.MID)
              .salary(5500.0)
              .hiredAt(LocalDate.of(2022, 8, 24))
              .experiences(getMockedExperiences7())
              .studies(getMockedStudies1())
              .trainer(getMockedTrainer31())
              .build();
    }

    public static Employee getMockedEmployee32() {
        return Employee.builder()
              .id(32)
              .name("Liam Hall")
              .email("liam.hall@example.com")
              .password("#Liam_Hall_Password0")
              .mobile("+40787654368")
              .address("111 Oak St, Athens, Greece")
              .birthday(LocalDate.of(1998, 11, 9))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.SENIOR)
              .salary(6500.0)
              .hiredAt(LocalDate.of(2021, 1, 23))
              .experiences(getMockedExperiences8())
              .studies(getMockedStudies2())
              .trainer(getMockedTrainer32())
              .build();
    }

    public static Employee getMockedEmployee33() {
        return Employee.builder()
              .id(33)
              .name("Sophia Young")
              .email("sophia.young@example.com")
              .password("#Sophia_Young_Password0")
              .mobile("+40754321869")
              .address("333 Pine St, Madrid, Spain")
              .birthday(LocalDate.of(1995, 6, 23))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(false)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.JUNIOR)
              .salary(6000.0)
              .hiredAt(LocalDate.of(2021, 8, 17))
              .experiences(getMockedExperiences1())
              .studies(getMockedStudies3())
              .trainer(getMockedTrainer33())
              .build();
    }

    public static Employee getMockedEmployee34() {
        return Employee.builder()
              .id(34)
              .name("Noah Clark")
              .email("noah.clark@example.com")
              .password("#Noah_Clark_Password0")
              .mobile("+40789012670")
              .address("555 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1997, 2, 5))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.JUNIOR)
              .salary(6250.0)
              .hiredAt(LocalDate.of(2021, 3, 30))
              .experiences(getMockedExperiences2())
              .studies(getMockedStudies4())
              .trainer(getMockedTrainer34())
              .build();
    }

    public static Employee getMockedEmployee35() {
        return Employee.builder()
              .id(35)
              .name("Olivia Hill")
              .email("olivia.hill@example.com")
              .password("#Olivia_Hill_Password0")
              .mobile("+40723145671")
              .address("777 Oak St, Seoul, South Korea")
              .birthday(LocalDate.of(2001, 9, 20))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.MID)
              .salary(7750.0)
              .hiredAt(LocalDate.of(2020, 11, 8))
              .experiences(getMockedExperiences3())
              .studies(getMockedStudies5())
              .trainer(getMockedTrainer35())
              .build();
    }

    public static Employee getMockedEmployee36() {
        return Employee.builder()
              .id(36)
              .name("Michaela Allen")
              .email("michaela.allen@example.com")
              .password("#Michaela_Allen_Password0")
              .mobile("+40787654372")
              .address("999 Pine St, Beijing, China")
              .birthday(LocalDate.of(1998, 5, 6))
              .role(getMockedRole1())
              .authorities(getMockedAuthorities1())
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.SENIOR)
              .salary(8750.0)
              .hiredAt(LocalDate.of(2019, 12, 3))
              .experiences(getMockedExperiences4())
              .studies(getMockedStudies6())
              .trainer(getMockedTrainer36())
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto1() {
        return EmployeeDto.builder()
              .id(1)
              .name("Abigail Johnson")
              .email("abigail.johnson@example.com")
              .password("#Abigail_Johnson_Password0")
              .mobile("+40754321837")
              .address("999 Oak St, Athens, Greece")
              .birthday(LocalDate.of(2000, 10, 2))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.JUNIOR)
              .salary(4000.0)
              .hiredAt(LocalDate.of(2023, 1, 1))
              .experiencesIds(List.of(1, 2))
              .studiesIds(List.of(1, 2))
              .trainerId(1)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto2() {
        return EmployeeDto.builder()
              .id(2)
              .name("Michael Davis")
              .email("michael.davis@example.com")
              .password("#Michael_Davis_Password0")
              .mobile("+40789012638")
              .address("111 Oak St, Madrid, Spain")
              .birthday(LocalDate.of(1994, 5, 16))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.JUNIOR)
              .salary(3500.0)
              .hiredAt(LocalDate.of(2022, 10, 4))
              .experiencesIds(List.of(3, 4))
              .studiesIds(List.of(3, 4))
              .trainerId(2)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto3() {
        return EmployeeDto.builder()
              .id(3)
              .name("Mia Wilson")
              .email("mia.wilson@example.com")
              .password("#Mia_Wilson_Password0")
              .mobile("+40723145639")
              .address("333 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1990, 12, 29))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.MID)
              .salary(5000.0)
              .hiredAt(LocalDate.of(2020, 9, 12))
              .experiencesIds(List.of(5, 6))
              .studiesIds(List.of(5, 6))
              .trainerId(3)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto4() {
        return EmployeeDto.builder()
              .id(4)
              .name("James Lee")
              .email("james.lee@example.com")
              .password("#James_Lee_Password0")
              .mobile("+40787654340")
              .address("555 Pine St, Seoul, South Korea")
              .birthday(LocalDate.of(1991, 8, 11))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.FRONTEND)
              .grade(Grade.SENIOR)
              .salary(6500.0)
              .hiredAt(LocalDate.of(2021, 8, 15))
              .experiencesIds(List.of(7, 8))
              .studiesIds(List.of(7, 8))
              .trainerId(4)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto5() {
        return EmployeeDto.builder()
              .id(5)
              .name("Maria Thompson")
              .email("maria.thompson@example.com")
              .password("#Maria_Thompson_Password0")
              .mobile("+40754321841")
              .address("777 Elm St, Beijing, China")
              .birthday(LocalDate.of(1993, 3, 24))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.JUNIOR)
              .salary(3000.0)
              .hiredAt(LocalDate.of(2023, 2, 9))
              .experiencesIds(List.of(9, 10))
              .studiesIds(List.of(9, 10))
              .trainerId(5)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto6() {
        return EmployeeDto.builder()
              .id(6)
              .name("Ethan Smith")
              .email("ethan.smith@example.com")
              .password("#Ethan_Smith_Password0")
              .mobile("+40789012642")
              .address("999 Oak St, Cape Town, South Africa")
              .birthday(LocalDate.of(1989, 11, 6))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.JUNIOR)
              .salary(2500.0)
              .hiredAt(LocalDate.of(2022, 11, 16))
              .experiencesIds(List.of(11, 12))
              .studiesIds(List.of(11, 12))
              .trainerId(6)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto7() {
        return EmployeeDto.builder()
              .id(7)
              .name("Olivia Smith")
              .email("olivia.smith@example.com")
              .password("#Olivia_Smith_Password0")
              .mobile("+40723145643")
              .address("111 Elm St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(1994, 6, 19))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.MID)
              .salary(4000.0)
              .hiredAt(LocalDate.of(2021, 7, 19))
              .experiencesIds(List.of(13, 14))
              .studiesIds(List.of(1, 2))
              .trainerId(7)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto8() {
        return EmployeeDto.builder()
              .id(8)
              .name("Emily Davis")
              .email("emily.davis@example.com")
              .password("#Emily_Davis_Password0")
              .mobile("+40787654344")
              .address("333 Elm St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(1998, 1, 1))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.SENIOR)
              .salary(7000.0)
              .hiredAt(LocalDate.of(2020, 2, 16))
              .experiencesIds(List.of(15, 16))
              .studiesIds(List.of(3, 4))
              .trainerId(8)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto9() {
        return EmployeeDto.builder()
              .id(9)
              .name("Henry Wilson")
              .email("henry.wilson@example.com")
              .password("#Henry_Wilson_Password0")
              .mobile("+40754321845")
              .address("555 Pine St, Mexico City, Mexico")
              .birthday(LocalDate.of(2001, 8, 14))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.JUNIOR)
              .salary(4500.0)
              .hiredAt(LocalDate.of(2022, 10, 14))
              .experiencesIds(List.of(1, 2))
              .studiesIds(List.of(5, 6))
              .trainerId(9)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto10() {
        return EmployeeDto.builder()
              .id(10)
              .name("Scarlett Thompson")
              .email("scarlett.thompson@example.com")
              .password("#Scarlett_Thompson_Password0")
              .mobile("+40789012646")
              .address("777 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(2002, 3, 28))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.JUNIOR)
              .salary(5000.0)
              .hiredAt(LocalDate.of(2022, 3, 28))
              .experiencesIds(List.of(3, 4))
              .studiesIds(List.of(7, 8))
              .trainerId(10)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto11() {
        return EmployeeDto.builder()
              .id(11)
              .name("Jacob Brown")
              .email("jacob.brown@example.com")
              .password("#Jacob_Brown_Password0")
              .mobile("+40723145647")
              .address("999 Pine St, Paris, France")
              .birthday(LocalDate.of(1999, 11, 10))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.MID)
              .salary(6000.0)
              .hiredAt(LocalDate.of(2021, 11, 10))
              .experiencesIds(List.of(5, 6))
              .studiesIds(List.of(9, 10))
              .trainerId(11)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto12() {
        return EmployeeDto.builder()
              .id(12)
              .name("Ava Smith")
              .email("ava.smith@example.com")
              .password("#Ava_Smith_Password0")
              .mobile("+40787654348")
              .address("111 Pine St, London, UK")
              .birthday(LocalDate.of(1986, 6, 23))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DEVOPS)
              .grade(Grade.SENIOR)
              .salary(7500.0)
              .hiredAt(LocalDate.of(2020, 5, 28))
              .experiencesIds(List.of(7, 8))
              .studiesIds(List.of(11, 12))
              .trainerId(12)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto13() {
        return EmployeeDto.builder()
              .id(13)
              .name("Oliver Johnson")
              .email("oliver.johnson@example.com")
              .password("#Oliver_Johnson_Password0")
              .mobile("+40754321849")
              .address("333 Elm St, Berlin, Germany")
              .birthday(LocalDate.of(1988, 2, 5))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.JUNIOR)
              .salary(4000.0)
              .hiredAt(LocalDate.of(2023, 1, 20))
              .experiencesIds(List.of(9, 10))
              .studiesIds(List.of(1, 2))
              .trainerId(13)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto14() {
        return EmployeeDto.builder()
              .id(14)
              .name("Sophia Wilson")
              .email("sophia.wilson@example.com")
              .password("#Sophia_Wilson_Password0")
              .mobile("+40789012650")
              .address("555 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(1994, 9, 19))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.JUNIOR)
              .salary(4500.0)
              .hiredAt(LocalDate.of(2022, 4, 16))
              .experiencesIds(List.of(11, 12))
              .studiesIds(List.of(3, 4))
              .trainerId(14)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto15() {
        return EmployeeDto.builder()
              .id(15)
              .name("William Harris")
              .email("william.harris@example.com")
              .password("#William_Harris_Password0")
              .mobile("+40723145651")
              .address("777 Pine St, Athens, Greece")
              .birthday(LocalDate.of(1996, 4, 3))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.MID)
              .salary(6000.0)
              .hiredAt(LocalDate.of(2021, 5, 18))
              .experiencesIds(List.of(13, 14))
              .studiesIds(List.of(5, 6))
              .trainerId(15)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto16() {
        return EmployeeDto.builder()
              .id(16)
              .name("Mia Johnson")
              .email("mia.johnson@example.com")
              .password("#Mia_Johnson_Password0")
              .mobile("+40787654352")
              .address("999 Oak St, Madrid, Spain")
              .birthday(LocalDate.of(1998, 11, 16))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.TESTING)
              .grade(Grade.SENIOR)
              .salary(7000.0)
              .hiredAt(LocalDate.of(2020, 9, 15))
              .experiencesIds(List.of(15, 16))
              .studiesIds(List.of(7, 8))
              .trainerId(16)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto17() {
        return EmployeeDto.builder()
              .id(17)
              .name("Jacob Lee")
              .email("jacob.lee@example.com")
              .password("#Jacob_Lee_Password0")
              .mobile("+40754321853")
              .address("111 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1997, 6, 29))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DESIGN)
              .grade(Grade.JUNIOR)
              .salary(3500.0)
              .hiredAt(LocalDate.of(2023, 1, 31))
              .experiencesIds(List.of(1, 2))
              .studiesIds(List.of(9, 10))
              .trainerId(17)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto18() {
        return EmployeeDto.builder()
              .id(18)
              .name("Charlotte Brown")
              .email("charlotte.brown@example.com")
              .password("#Charlotte_Brown_Password0")
              .mobile("+40789012654")
              .address("333 Pine St, Seoul, South Korea")
              .birthday(LocalDate.of(2000, 2, 12))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.DESIGN)
              .grade(Grade.JUNIOR)
              .salary(4000.0)
              .hiredAt(LocalDate.of(2023, 3, 17))
              .experiencesIds(List.of(3, 4))
              .studiesIds(List.of(11, 12))
              .trainerId(18)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto19() {
        return EmployeeDto.builder()
              .id(19)
              .name("Ethan Johnson")
              .email("ethan.johnson@example.com")
              .password("#Ethan_Johnson_Password0")
              .mobile("+40723145655")
              .address("555 Elm St, Beijing, China")
              .birthday(LocalDate.of(1998, 9, 25))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DESIGN)
              .grade(Grade.MID)
              .salary(5500.0)
              .hiredAt(LocalDate.of(2022, 2, 28))
              .experiencesIds(List.of(5, 6))
              .studiesIds(List.of(1, 2))
              .trainerId(19)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto20() {
        return EmployeeDto.builder()
              .id(20)
              .name("Sophia Davis")
              .email("sophia.davis@example.com")
              .password("#Sophia_Davis_Password0")
              .mobile("+40787654356")
              .address("777 Elm St, Cape Town, South Africa")
              .birthday(LocalDate.of(2001, 7, 9))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DESIGN)
              .grade(Grade.SENIOR)
              .salary(6500.0)
              .hiredAt(LocalDate.of(2021, 8, 9))
              .experiencesIds(List.of(7, 8))
              .studiesIds(List.of(3, 4))
              .trainerId(20)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto21() {
        return EmployeeDto.builder()
              .id(21)
              .name("Jacob Thompson")
              .email("jacob.thompson@example.com")
              .password("#Jacob_Thompson_Password0")
              .mobile("+40754321857")
              .address("999 Pine St, Buenos Aires, Argentina")
              .birthday(LocalDate.of(1996, 2, 22))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.JUNIOR)
              .salary(5000.0)
              .hiredAt(LocalDate.of(2023, 6, 12))
              .experiencesIds(List.of(9, 10))
              .studiesIds(List.of(5, 6))
              .trainerId(21)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto22() {
        return EmployeeDto.builder()
              .id(22)
              .name("Liam Wilson")
              .email("liam.wilson@example.com")
              .password("#Liam_Wilson_Password0")
              .mobile("+40789012658")
              .address("111 Elm St, Rio de Janeiro, Brazil")
              .birthday(LocalDate.of(1995, 10, 7))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.JUNIOR)
              .salary(5500.0)
              .hiredAt(LocalDate.of(2022, 10, 30))
              .experiencesIds(List.of(11, 12))
              .studiesIds(List.of(7, 8))
              .trainerId(22)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto23() {
        return EmployeeDto.builder()
              .id(23)
              .name("Ava Brown")
              .email("ava.brown@example.com")
              .password("#Ava_Brown_Password0")
              .mobile("+40723145659")
              .address("333 Elm St, Mexico City, Mexico")
              .birthday(LocalDate.of(1998, 7, 21))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.MID)
              .salary(7000.0)
              .hiredAt(LocalDate.of(2020, 12, 15))
              .experiencesIds(List.of(13, 14))
              .studiesIds(List.of(9, 10))
              .trainerId(23)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto24() {
        return EmployeeDto.builder()
              .id(24)
              .name("Scarlett Wilson")
              .email("scarlett.wilson@example.com")
              .password("#Scarlett_Wilson_Password0")
              .mobile("+40787654360")
              .address("555 Elm St, Vancouver, Canada")
              .birthday(LocalDate.of(2001, 5, 5))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.DATA_ANALYST)
              .grade(Grade.SENIOR)
              .salary(8000.0)
              .hiredAt(LocalDate.of(2020, 4, 10))
              .experiencesIds(List.of(15, 16))
              .studiesIds(List.of(11, 12))
              .trainerId(24)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto25() {
        return EmployeeDto.builder()
              .id(25)
              .name("Noah Martinez")
              .email("noah.martinez@example.com")
              .password("#Noah_Martinez_Password0")
              .mobile("+40754321861")
              .address("777 Pine St, Paris, France")
              .birthday(LocalDate.of(1999, 12, 17))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.MACHINE_LEARNING)
              .grade(Grade.JUNIOR)
              .salary(4750.0)
              .hiredAt(LocalDate.of(2023, 3, 27))
              .experiencesIds(List.of(1, 2))
              .studiesIds(List.of(1, 2))
              .trainerId(25)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto26() {
        return EmployeeDto.builder()
              .id(26)
              .name("Mia Anderson")
              .email("mia.anderson@example.com")
              .password("#Mia_Anderson_Password0")
              .mobile("+40789012662")
              .address("999 Oak St, London, UK")
              .birthday(LocalDate.of(2002, 9, 30))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.MACHINE_LEARNING)
              .grade(Grade.JUNIOR)
              .salary(5250.0)
              .hiredAt(LocalDate.of(2022, 11, 18))
              .experiencesIds(List.of(3, 4))
              .studiesIds(List.of(3, 4))
              .trainerId(26)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto27() {
        return EmployeeDto.builder()
              .id(27)
              .name("Grace Lee")
              .email("grace.lee@example.com")
              .password("#Grace_Lee_Password0")
              .mobile("+40723145663")
              .address("111 Elm St, Berlin, Germany")
              .birthday(LocalDate.of(1996, 5, 14))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.MACHINE_LEARNING)
              .grade(Grade.MID)
              .salary(7000.0)
              .hiredAt(LocalDate.of(2022, 6, 29))
              .experiencesIds(List.of(5, 6))
              .studiesIds(List.of(5, 6))
              .trainerId(27)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto28() {
        return EmployeeDto.builder()
              .id(28)
              .name("Benjamin Taylor")
              .email("benjamin.taylor@example.com")
              .password("#Benjamin_Taylor_Password0")
              .mobile("+40787654364")
              .address("333 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(2002, 12, 27))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.MACHINE_LEARNING)
              .grade(Grade.SENIOR)
              .salary(8750.0)
              .hiredAt(LocalDate.of(2020, 7, 16))
              .experiencesIds(List.of(7, 8))
              .studiesIds(List.of(7, 8))
              .trainerId(28)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto29() {
        return EmployeeDto.builder()
              .id(29)
              .name("Elijah Roberts")
              .email("elijah.roberts@example.com")
              .password("#Elijah_Roberts_Password0")
              .mobile("+40754321865")
              .address("555 Pine St, Sydney, Australia")
              .birthday(LocalDate.of(2003, 1, 27))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.JUNIOR)
              .salary(4500.0)
              .hiredAt(LocalDate.of(2023, 5, 19))
              .experiencesIds(List.of(9, 10))
              .studiesIds(List.of(9, 10))
              .trainerId(29)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto30() {
        return EmployeeDto.builder()
              .id(30)
              .name("Amelia Walker")
              .email("amelia.walker@example.com")
              .password("#Amelia_Walker_Password0")
              .mobile("+40789012666")
              .address("777 Oak St, Rome, Italy")
              .birthday(LocalDate.of(1992, 8, 12))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.JUNIOR)
              .salary(4750.0)
              .hiredAt(LocalDate.of(2023, 3, 19))
              .experiencesIds(List.of(11, 12))
              .studiesIds(List.of(11, 12))
              .trainerId(30)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto31() {
        return EmployeeDto.builder()
              .id(31)
              .name("Daniel Green")
              .email("daniel.green@example.com")
              .password("#Daniel_Green_Password0")
              .mobile("+40723145667")
              .address("999 Elm St, Moscow, Russia")
              .birthday(LocalDate.of(1996, 3, 27))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.MID)
              .salary(5500.0)
              .hiredAt(LocalDate.of(2022, 8, 24))
              .experiencesIds(List.of(13, 14))
              .studiesIds(List.of(1, 2))
              .trainerId(31)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto32() {
        return EmployeeDto.builder()
              .id(32)
              .name("Liam Hall")
              .email("liam.hall@example.com")
              .password("#Liam_Hall_Password0")
              .mobile("+40787654368")
              .address("111 Oak St, Athens, Greece")
              .birthday(LocalDate.of(1998, 11, 9))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.BUSINESS_ANALYST)
              .grade(Grade.SENIOR)
              .salary(6500.0)
              .hiredAt(LocalDate.of(2021, 1, 23))
              .experiencesIds(List.of(15, 16))
              .studiesIds(List.of(3, 4))
              .trainerId(32)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto33() {
        return EmployeeDto.builder()
              .id(33)
              .name("Sophia Young")
              .email("sophia.young@example.com")
              .password("#Sophia_Young_Password0")
              .mobile("+40754321869")
              .address("333 Pine St, Madrid, Spain")
              .birthday(LocalDate.of(1995, 6, 23))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(false)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.JUNIOR)
              .salary(6000.0)
              .hiredAt(LocalDate.of(2021, 8, 17))
              .experiencesIds(List.of(1, 2))
              .studiesIds(List.of(5, 6))
              .trainerId(33)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto34() {
        return EmployeeDto.builder()
              .id(34)
              .name("Noah Clark")
              .email("noah.clark@example.com")
              .password("#Noah_Clark_Password0")
              .mobile("+40789012670")
              .address("555 Elm St, Tokyo, Japan")
              .birthday(LocalDate.of(1997, 2, 5))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.JUNIOR)
              .salary(6250.0)
              .hiredAt(LocalDate.of(2021, 3, 30))
              .experiencesIds(List.of(3, 4))
              .studiesIds(List.of(7, 8))
              .trainerId(34)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto35() {
        return EmployeeDto.builder()
              .id(35)
              .name("Olivia Hill")
              .email("olivia.hill@example.com")
              .password("#Olivia_Hill_Password0")
              .mobile("+40723145671")
              .address("777 Oak St, Seoul, South Korea")
              .birthday(LocalDate.of(2001, 9, 20))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.MID)
              .salary(7750.0)
              .hiredAt(LocalDate.of(2020, 11, 8))
              .experiencesIds(List.of(5, 6))
              .studiesIds(List.of(9, 10))
              .trainerId(35)
              .build();
    }

    public static EmployeeDto getMockedEmployeeDto36() {
        return EmployeeDto.builder()
              .id(36)
              .name("Michaela Allen")
              .email("michaela.allen@example.com")
              .password("#Michaela_Allen_Password0")
              .mobile("+40787654372")
              .address("999 Pine St, Beijing, China")
              .birthday(LocalDate.of(1998, 5, 6))
              .roleId(1)
              .authoritiesIds(List.of(1, 2))
              .isActive(true)
              .employmentType(EmploymentType.PART_TIME)
              .position(Position.SCRUM_MASTER)
              .grade(Grade.SENIOR)
              .salary(8750.0)
              .hiredAt(LocalDate.of(2019, 12, 3))
              .experiencesIds(List.of(7, 8))
              .studiesIds(List.of(11, 12))
              .trainerId(36)
              .build();
    }
}
