package com.project.ems.mock;

import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceDto;
import com.project.ems.experience.enums.ExperienceType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExperienceMock {

    public static List<Experience> getMockedExperiences() {
        return Stream.of(getMockedExperiences1(), getMockedExperiences2()).flatMap(List::stream).toList();
    }

    public static List<ExperienceDto> getMockedExperienceDtos() {
        return Stream.of(getMockedExperienceDtos1(), getMockedExperienceDtos2()).flatMap(List::stream).toList();
    }

    public static List<Experience> getMockedExperiences1() {
        return new ArrayList<>(List.of(getMockedExperience1(), getMockedExperience2()));
    }

    public static List<Experience> getMockedExperiences2() {
        return new ArrayList<>(List.of(getMockedExperience3(), getMockedExperience4()));
    }

    public static List<ExperienceDto> getMockedExperienceDtos1() {
        return new ArrayList<>(List.of(getMockedExperienceDto1(), getMockedExperienceDto2()));
    }

    public static List<ExperienceDto> getMockedExperienceDtos2() {
        return new ArrayList<>(List.of(getMockedExperienceDto3(), getMockedExperienceDto4()));
    }

    public static Experience getMockedExperience1() {
        return Experience.builder()
              .id(1)
              .title("test_title1")
              .organization("test_organization1")
              .description("test_description1")
              .type(ExperienceType.APPRENTICESHIP)
              .startedAt(LocalDate.of(2020, 1, 1))
              .finishedAt(LocalDate.of(2021, 1, 1))
              .build();
    }

    public static Experience getMockedExperience2() {
        return Experience.builder()
              .id(2)
              .title("test_title2")
              .organization("test_organization2")
              .description("test_description2")
              .type(ExperienceType.INTERNSHIP)
              .startedAt(LocalDate.of(2020, 1, 2))
              .finishedAt(LocalDate.of(2021, 1, 2))
              .build();
    }

    public static Experience getMockedExperience3() {
        return Experience.builder()
              .id(3)
              .title("test_title3")
              .organization("test_organization3")
              .description("test_description3")
              .type(ExperienceType.TRAINING)
              .startedAt(LocalDate.of(2020, 1, 3))
              .finishedAt(LocalDate.of(2021, 1, 3))
              .build();
    }

    public static Experience getMockedExperience4() {
        return Experience.builder()
              .id(4)
              .title("test_title4")
              .organization("test_organization4")
              .description("test_description4")
              .type(ExperienceType.VOLUNTEERING)
              .startedAt(LocalDate.of(2020, 1, 4))
              .finishedAt(LocalDate.of(2021, 1, 4))
              .build();
    }

    public static ExperienceDto getMockedExperienceDto1() {
        return ExperienceDto.builder()
              .id(1)
              .title("test_title1")
              .organization("test_organization1")
              .description("test_description1")
              .type(ExperienceType.APPRENTICESHIP)
              .startedAt(LocalDate.of(2020, 1, 1))
              .finishedAt(LocalDate.of(2021, 1, 1))
              .build();
    }

    public static ExperienceDto getMockedExperienceDto2() {
        return ExperienceDto.builder()
              .id(2)
              .title("test_title2")
              .organization("test_organization2")
              .description("test_description2")
              .type(ExperienceType.INTERNSHIP)
              .startedAt(LocalDate.of(2020, 1, 2))
              .finishedAt(LocalDate.of(2021, 1, 2))
              .build();
    }

    public static ExperienceDto getMockedExperienceDto3() {
        return ExperienceDto.builder()
              .id(3)
              .title("test_title3")
              .organization("test_organization3")
              .description("test_description3")
              .type(ExperienceType.TRAINING)
              .startedAt(LocalDate.of(2020, 1, 3))
              .finishedAt(LocalDate.of(2021, 1, 3))
              .build();
    }

    public static ExperienceDto getMockedExperienceDto4() {
        return ExperienceDto.builder()
              .id(4)
              .title("test_title4")
              .organization("test_organization4")
              .description("test_description4")
              .type(ExperienceType.VOLUNTEERING)
              .startedAt(LocalDate.of(2020, 1, 4))
              .finishedAt(LocalDate.of(2021, 1, 4))
              .build();
    }
}
