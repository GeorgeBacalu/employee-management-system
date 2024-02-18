package com.project.ems.integration.experience;

import com.project.ems.experience.Experience;
import com.project.ems.experience.ExperienceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage1;
import static com.project.ems.mock.ExperienceMock.getMockedExperiencesPage2;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class ExperienceRepositoryIntegrationTest {

    @Autowired
    private ExperienceRepository experienceRepository;

    private List<Experience> filteredExperiencesPage1;
    private List<Experience> filteredExperiencesPage2;

    @BeforeEach
    void setUp() {
        filteredExperiencesPage1 = getMockedExperiencesPage1();
        filteredExperiencesPage2 = getMockedExperiencesPage2();
    }

    @Test
    void findAllByKey_test() {
        then(experienceRepository.findAllByKey(PAGEABLE_PAGE1, EXPERIENCE_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredExperiencesPage1);
        then(experienceRepository.findAllByKey(PAGEABLE_PAGE2, EXPERIENCE_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredExperiencesPage2);
        then(experienceRepository.findAllByKey(PAGEABLE_PAGE3, EXPERIENCE_FILTER_KEY).getContent()).isEmpty();
    }
}
