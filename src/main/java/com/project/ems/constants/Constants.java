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

    public static final String AUTHORITIES = "/authorities";
    public static final String EMPLOYEES = "/employees";
    public static final String EXPERIENCES = "/experiences";
    public static final String FEEDBACKS = "/feedbacks";
    public static final String ROLES = "/roles";
    public static final String STUDIES = "/studies";
    public static final String TRAINERS = "/trainers";
    public static final String USERS = "/users";

    public static final String AUTHORITIES_VIEW = "authority/authorities";
    public static final String AUTHORITY_DETAILS_VIEW = "authority/authority-details";
    public static final String SAVE_AUTHORITY_VIEW = "authority/save-authority";
    public static final String REDIRECT_AUTHORITIES_VIEW = "redirect:/authorities";

    public static final String EMPLOYEES_VIEW = "employee/employees";
    public static final String EMPLOYEE_DETAILS_VIEW = "employee/employee-details";
    public static final String SAVE_EMPLOYEE_VIEW = "employee/save-employee";
    public static final String REDIRECT_EMPLOYEES_VIEW = "redirect:/employees";

    public static final String EXPERIENCES_VIEW = "experience/experiences";
    public static final String EXPERIENCE_DETAILS_VIEW = "experience/experience-details";
    public static final String SAVE_EXPERIENCE_VIEW = "experience/save-experience";
    public static final String REDIRECT_EXPERIENCES_VIEW = "redirect:/experiences";

    public static final String FEEDBACKS_VIEW = "feedback/feedbacks";
    public static final String FEEDBACK_DETAILS_VIEW = "feedback/feedback-details";
    public static final String SAVE_FEEDBACK_VIEW = "feedback/save-feedback";
    public static final String REDIRECT_FEEDBACKS_VIEW = "redirect:/feedbacks";

    public static final String ROLES_VIEW = "role/roles";
    public static final String ROLE_DETAILS_VIEW = "role/role-details";
    public static final String SAVE_ROLE_VIEW = "role/save-role";
    public static final String REDIRECT_ROLES_VIEW = "redirect:/roles";

    public static final String STUDIES_VIEW = "study/studies";
    public static final String STUDY_DETAILS_VIEW = "study/study-details";
    public static final String SAVE_STUDY_VIEW = "study/save-study";
    public static final String REDIRECT_STUDIES_VIEW = "redirect:/studies";

    public static final String TRAINERS_VIEW = "trainer/trainers";
    public static final String TRAINER_DETAILS_VIEW = "trainer/trainer-details";
    public static final String SAVE_TRAINER_VIEW = "trainer/save-trainer";
    public static final String REDIRECT_TRAINERS_VIEW = "redirect:/trainers";

    public static final String USERS_VIEW = "user/users";
    public static final String USER_DETAILS_VIEW = "user/user-details";
    public static final String SAVE_USER_VIEW = "user/save-user";
    public static final String REDIRECT_USERS_VIEW = "redirect:/users";

    public static final String AUTHORITIES_ATTRIBUTE = "authorities";
    public static final String AUTHORITY_ATTRIBUTE = "authority";
    public static final String AUTHORITY_DTO_ATTRIBUTE = "authorityDto";

    public static final String EMPLOYEES_ATTRIBUTE = "employees";
    public static final String EMPLOYEE_ATTRIBUTE = "employee";
    public static final String EMPLOYEE_DTO_ATTRIBUTE = "employeeDto";

    public static final String EXPERIENCES_ATTRIBUTE = "experiences";
    public static final String EXPERIENCE_ATTRIBUTE = "experience";
    public static final String EXPERIENCE_DTO_ATTRIBUTE = "experienceDto";

    public static final String FEEDBACKS_ATTRIBUTE = "feedbacks";
    public static final String FEEDBACK_ATTRIBUTE = "feedback";
    public static final String FEEDBACK_DTO_ATTRIBUTE = "feedbackDto";

    public static final String ROLES_ATTRIBUTE = "roles";
    public static final String ROLE_ATTRIBUTE = "role";
    public static final String ROLE_DTO_ATTRIBUTE = "roleDto";

    public static final String STUDIES_ATTRIBUTE = "studies";
    public static final String STUDY_ATTRIBUTE = "study";
    public static final String STUDY_DTO_ATTRIBUTE = "studyDto";

    public static final String TRAINERS_ATTRIBUTE = "trainers";
    public static final String TRAINER_ATTRIBUTE = "trainer";
    public static final String TRAINER_DTO_ATTRIBUTE = "trainerDto";

    public static final String USERS_ATTRIBUTE = "users";
    public static final String USER_ATTRIBUTE = "user";
    public static final String USER_DTO_ATTRIBUTE = "userDto";

    public static final String TEXT_HTML_UTF8 = "text/html;charset=UTF-8";
}
