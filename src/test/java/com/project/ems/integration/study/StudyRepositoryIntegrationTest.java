package com.project.ems.integration.study;

import com.project.ems.study.Study;
import com.project.ems.study.StudyRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage1;
import static com.project.ems.mock.StudyMock.getMockedStudiesPage2;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class StudyRepositoryIntegrationTest {

    @Autowired
    private StudyRepository studyRepository;

    private List<Study> filteredStudiesPage1;
    private List<Study> filteredStudiesPage2;

    @BeforeEach
    void setUp() {
        filteredStudiesPage1 = getMockedStudiesPage1();
        filteredStudiesPage2 = getMockedStudiesPage2();
    }

    @Test
    void findAllByKey_test() {
        then(studyRepository.findAllByKey(PAGEABLE_PAGE1, STUDY_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredStudiesPage1);
        then(studyRepository.findAllByKey(PAGEABLE_PAGE2, STUDY_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredStudiesPage2);
        then(studyRepository.findAllByKey(PAGEABLE_PAGE3, STUDY_FILTER_KEY).getContent()).isEmpty();
    }
}
