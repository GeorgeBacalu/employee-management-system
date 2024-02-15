package com.project.ems.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String RESOURCE_NOT_FOUND = "Resource not found: ";
    public static final String INVALID_REQUEST = "Invalid request: ";

    public static final String AUTHORITY_NOT_FOUND = "Authority with id %d not found";
    public static final String EMPLOYEE_NOT_FOUND = "Employee with id %d not found";
    public static final String EXPERIENCE_NOT_FOUND = "Experience with id %d not found";
    public static final String FEEDBACK_NOT_FOUND = "Feedback with id %d not found";
    public static final String ROLE_NOT_FOUND = "Role with id %d not found";
    public static final String STUDY_NOT_FOUND = "Study with id %d not found";
    public static final String TRAINER_NOT_FOUND = "Trainer with id %d not found";
    public static final String USER_NOT_FOUND = "User with id %d not found";

    public static final Integer VALID_ID = 1;
    public static final Integer INVALID_ID = 999;

    public static final String API_AUTHORITIES = "/api/authorities";
    public static final String API_EMPLOYEES = "/api/employees";
    public static final String API_EXPERIENCES = "/api/experiences";
    public static final String API_FEEDBACKS = "/api/feedbacks";
    public static final String API_ROLES = "/api/roles";
    public static final String API_STUDIES = "/api/studies";
    public static final String API_TRAINERS = "/api/trainers";
    public static final String API_USERS = "/api/users";
}
