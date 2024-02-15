package com.project.ems.mock;

import com.project.ems.employee.enums.EmploymentType;
import com.project.ems.employee.enums.Grade;
import com.project.ems.employee.enums.Position;
import com.project.ems.trainer.Trainer;
import com.project.ems.trainer.TrainerDto;
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TrainerMock {

    public static List<Trainer> getMockedTrainers() {
        return List.of(getMockedTrainer1(), getMockedTrainer2());
    }

    public static List<Trainer> getMockedActiveTrainers() {
        return getMockedTrainers().stream().filter(Trainer::getIsActive).toList();
    }

    public static List<TrainerDto> getMockedTrainerDtos() {
        return List.of(getMockedTrainerDto1(), getMockedTrainerDto2());
    }

    public static List<TrainerDto> getMockedActiveTrainerDtos() {
        return getMockedTrainerDtos().stream().filter(TrainerDto::getIsActive).toList();
    }

    public static Trainer getMockedTrainer1() {
        return Trainer.builder()
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
              .grade(Grade.SENIOR)
              .salary(7500.0)
              .hiredAt(LocalDate.of(2020, 1, 1))
              .experiences(getMockedExperiences1())
              .studies(getMockedStudies1())
              .supervisingTrainer(null)
              .nrTrainees(2)
              .maxTrainees(3)
              .build();
    }

    public static Trainer getMockedTrainer2() {
        return Trainer.builder()
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
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.SENIOR)
              .salary(7000.0)
              .hiredAt(LocalDate.of(2020, 1, 2))
              .experiences(getMockedExperiences2())
              .studies(getMockedStudies2())
              .supervisingTrainer(getMockedTrainer1())
              .nrTrainees(1)
              .maxTrainees(2)
              .build();
    }

    public static TrainerDto getMockedTrainerDto1() {
        return TrainerDto.builder()
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
              .grade(Grade.SENIOR)
              .salary(7500.0)
              .hiredAt(LocalDate.of(2020, 1, 1))
              .experiencesIds(List.of(1, 2))
              .studiesIds(List.of(1, 2))
              .supervisingTrainerId(null)
              .nrTrainees(2)
              .maxTrainees(3)
              .build();
    }

    public static TrainerDto getMockedTrainerDto2() {
        return TrainerDto.builder()
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
              .employmentType(EmploymentType.FULL_TIME)
              .position(Position.BACKEND)
              .grade(Grade.SENIOR)
              .salary(7000.0)
              .hiredAt(LocalDate.of(2020, 1, 2))
              .experiencesIds(List.of(3, 4))
              .studiesIds(List.of(3, 4))
              .supervisingTrainerId(1)
              .nrTrainees(1)
              .maxTrainees(2)
              .build();
    }
}
