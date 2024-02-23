package com.project.ems.integration.trainer;

import com.project.ems.trainer.Trainer;
import com.project.ems.trainer.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.TrainerMock.getMockedTrainersPage1;
import static com.project.ems.mock.TrainerMock.getMockedTrainersPage2;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class TrainerRepositoryIntegrationTest {

    @Autowired
    private TrainerRepository trainerRepository;

    private List<Trainer> filteredTrainersPage1;
    private List<Trainer> filteredTrainersPage2;

    @BeforeEach
    void setUp() {
        filteredTrainersPage1 = getMockedTrainersPage1();
        filteredTrainersPage2 = getMockedTrainersPage2();
    }

    @Test
    void findAllByKey_test() {
        then(trainerRepository.findAllByKey(PAGEABLE_PAGE1, USER_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredTrainersPage1);
        then(trainerRepository.findAllByKey(PAGEABLE_PAGE2, USER_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredTrainersPage2);
        then(trainerRepository.findAllByKey(PAGEABLE_PAGE3, USER_FILTER_KEY).getContent()).isEmpty();
    }

    @Test
    void findAllActiveByKey_test() {
        then(trainerRepository.findAllActiveByKey(PAGEABLE_PAGE1, USER_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredTrainersPage1);
        then(trainerRepository.findAllActiveByKey(PAGEABLE_PAGE2, USER_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredTrainersPage2);
        then(trainerRepository.findAllActiveByKey(PAGEABLE_PAGE3, USER_FILTER_KEY).getContent()).isEmpty();
    }
}
