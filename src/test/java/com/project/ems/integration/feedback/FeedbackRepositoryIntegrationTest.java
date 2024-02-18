package com.project.ems.integration.feedback;

import com.project.ems.feedback.Feedback;
import com.project.ems.feedback.FeedbackRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage1;
import static com.project.ems.mock.FeedbackMock.getMockedFeedbacksPage2;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
class FeedbackRepositoryIntegrationTest {

    @Autowired
    private FeedbackRepository feedbackRepository;

    private List<Feedback> filteredFeedbacksPage1;
    private List<Feedback> filteredFeedbacksPage2;

    @BeforeEach
    void setUp() {
        filteredFeedbacksPage1 = getMockedFeedbacksPage1();
        filteredFeedbacksPage2 = getMockedFeedbacksPage2();
    }

    @Test
    void findAllByKey_test() {
        then(feedbackRepository.findAllByKey(PAGEABLE_PAGE1, FEEDBACK_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredFeedbacksPage1);
        then(feedbackRepository.findAllByKey(PAGEABLE_PAGE2, FEEDBACK_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredFeedbacksPage2);
        then(feedbackRepository.findAllByKey(PAGEABLE_PAGE3, FEEDBACK_FILTER_KEY).getContent()).isEmpty();
    }
}
