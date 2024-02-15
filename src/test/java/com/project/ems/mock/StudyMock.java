package com.project.ems.mock;

import com.project.ems.study.Study;
import com.project.ems.study.StudyDto;
import com.project.ems.study.enums.StudyType;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyMock {

    public static List<Study> getMockedStudies() {
        return Stream.of(getMockedStudies1(), getMockedStudies2()).flatMap(List::stream).toList();
    }

    public static List<StudyDto> getMockedStudyDtos() {
        return Stream.of(getMockedStudyDtos1(), getMockedStudyDtos2()).flatMap(List::stream).toList();
    }

    public static List<Study> getMockedStudies1() {
        return List.of(getMockedStudy1(), getMockedStudy2());
    }

    public static List<Study> getMockedStudies2() {
        return List.of(getMockedStudy3(), getMockedStudy4());
    }

    public static List<StudyDto> getMockedStudyDtos1() {
        return List.of(getMockedStudyDto1(), getMockedStudyDto2());
    }

    public static List<StudyDto> getMockedStudyDtos2() {
        return List.of(getMockedStudyDto3(), getMockedStudyDto4());
    }

    public static Study getMockedStudy1() {
        return Study.builder()
              .id(1)
              .title("test_title1")
              .institution("test_institution1")
              .description("test_description1")
              .type(StudyType.BACHELORS)
              .startedAt(LocalDate.of(2020, 1, 1))
              .finishedAt(LocalDate.of(2021, 1, 1))
              .build();
    }

    public static Study getMockedStudy2() {
        return Study.builder()
              .id(2)
              .title("test_title2")
              .institution("test_institution2")
              .description("test_description2")
              .type(StudyType.MASTERS)
              .startedAt(LocalDate.of(2020, 1, 2))
              .finishedAt(LocalDate.of(2021, 1, 2))
              .build();
    }

    public static Study getMockedStudy3() {
        return Study.builder()
              .id(3)
              .title("test_title3")
              .institution("test_institution3")
              .description("test_description3")
              .type(StudyType.BACHELORS)
              .startedAt(LocalDate.of(2020, 1, 3))
              .finishedAt(LocalDate.of(2021, 1, 3))
              .build();
    }

    public static Study getMockedStudy4() {
        return Study.builder()
              .id(4)
              .title("test_title4")
              .institution("test_institution4")
              .description("test_description4")
              .type(StudyType.MASTERS)
              .startedAt(LocalDate.of(2020, 1, 4))
              .finishedAt(LocalDate.of(2021, 1, 4))
              .build();
    }

    public static StudyDto getMockedStudyDto1() {
        return StudyDto.builder()
              .id(1)
              .title("test_title1")
              .institution("test_institution1")
              .description("test_description1")
              .type(StudyType.BACHELORS)
              .startedAt(LocalDate.of(2020, 1, 1))
              .finishedAt(LocalDate.of(2021, 1, 1))
              .build();
    }

    public static StudyDto getMockedStudyDto2() {
        return StudyDto.builder()
              .id(2)
              .title("test_title2")
              .institution("test_institution2")
              .description("test_description2")
              .type(StudyType.MASTERS)
              .startedAt(LocalDate.of(2020, 1, 2))
              .finishedAt(LocalDate.of(2021, 1, 2))
              .build();
    }

    public static StudyDto getMockedStudyDto3() {
        return StudyDto.builder()
              .id(3)
              .title("test_title3")
              .institution("test_institution3")
              .description("test_description3")
              .type(StudyType.BACHELORS)
              .startedAt(LocalDate.of(2020, 1, 3))
              .finishedAt(LocalDate.of(2021, 1, 3))
              .build();
    }

    public static StudyDto getMockedStudyDto4() {
        return StudyDto.builder()
              .id(4)
              .title("test_title4")
              .institution("test_institution4")
              .description("test_description4")
              .type(StudyType.MASTERS)
              .startedAt(LocalDate.of(2020, 1, 4))
              .finishedAt(LocalDate.of(2021, 1, 4))
              .build();
    }
}
