package com.project.ems.integration.user;

import com.project.ems.user.User;
import com.project.ems.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.ems.constants.Constants.*;
import static com.project.ems.mock.UserMock.getMockedUsersPage1;
import static com.project.ems.mock.UserMock.getMockedUsersPage2;
import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:data-test.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:cleanup.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@Transactional
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    private List<User> filteredUsersPage1;
    private List<User> filteredUsersPage2;

    @BeforeEach
    void setUp() {
        filteredUsersPage1 = getMockedUsersPage1();
        filteredUsersPage2 = getMockedUsersPage2();
    }

    @Test
    void findAllByKey_test() {
        then(userRepository.findAllByKey(PAGEABLE_PAGE1, USER_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredUsersPage1);
        then(userRepository.findAllByKey(PAGEABLE_PAGE2, USER_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredUsersPage2);
        then(userRepository.findAllByKey(PAGEABLE_PAGE3, USER_FILTER_KEY).getContent()).isEmpty();
    }

    @Test
    void findAllActiveByKey_test() {
        then(userRepository.findAllActiveByKey(PAGEABLE_PAGE1, USER_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredUsersPage1);
        then(userRepository.findAllActiveByKey(PAGEABLE_PAGE2, USER_FILTER_KEY).getContent()).usingRecursiveComparison().isEqualTo(filteredUsersPage2);
        then(userRepository.findAllActiveByKey(PAGEABLE_PAGE3, USER_FILTER_KEY).getContent()).isEmpty();
    }
}
