package com.project.ems.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Constants {

    public static final String RESOURCE_NOT_FOUND = "Resource not found: ";
    public static final String INVALID_REQUEST = "Invalid request: ";

    public static final String AUTHORITY_NOT_FOUND = "Authority with id %d not found";
    public static final String EXPERIENCE_NOT_FOUND = "Experience with id %d not found";
    public static final String FEEDBACK_NOT_FOUND = "Feedback with id %d not found";
    public static final String ROLE_NOT_FOUND = "Role with id %d not found";
    public static final String STUDY_NOT_FOUND = "Study with id %d not found";
    public static final String USER_NOT_FOUND = "User with id %d not found";
    public static final String INVALID_PAGE_NUMBER = "Invalid page number: ";

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

    public static final String API_PAGINATION = "/pagination?page={page}&size={size}&sort={field},{direction}&key={key}";
    public static final String API_ACTIVE_PAGINATION = "/active/pagination?page={page}&size={size}&sort={field},{direction}&key={key}";
    public static final String API_PAGINATION2 = "/pagination?page=%s&size=%s&sort=%s,%s&key=%s";
    public static final String API_ACTIVE_PAGINATION2 = "/active/pagination?page=%s&size=%s&sort=%s,%s&key=%s";

    public static final String AUTHORITIES = "/authorities";
    public static final String EMPLOYEES = "/employees";
    public static final String EXPERIENCES = "/experiences";
    public static final String FEEDBACKS = "/feedbacks";
    public static final String ROLES = "/roles";
    public static final String STUDIES = "/studies";
    public static final String TRAINERS = "/trainers";
    public static final String USERS = "/users";

    public static final String PAGINATION = "?page={page}&size={size}&sort={field},{direction}&key={key}";

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

    public static final String EXPERIENCE_FILTER_QUERY = "SELECT e FROM Experience e WHERE LOWER(CONCAT(e.title, ' ', e.organization, ' ', e.description, ' ', e.type, ' ', e.startedAt, ' ', e.finishedAt)) LIKE %:key%";
    public static final String FEEDBACK_FILTER_QUERY = "SELECT f FROM Feedback f WHERE LOWER(CONCAT(f.type, ' ', f.description, ' ', f.sentAt, ' ', f.user.name)) LIKE %:key%";
    public static final String STUDY_FILTER_QUERY = "SELECT s FROM Study s WHERE LOWER(CONCAT(s.title, ' ', s.institution, ' ', s.description, ' ', s.type, ' ', s.startedAt, ' ', s.finishedAt)) LIKE %:key%";
    public static final String USER_FILTER_QUERY = """
          SELECT DISTINCT u.* FROM _user u
          LEFT JOIN role r ON u.role_id = r.id
          LEFT JOIN user_authority ua ON u.id = ua.user_id
          LEFT JOIN authority a ON ua.authority_id = a.id
          LEFT JOIN user_experience ue ON u.id = ue.user_id
          LEFT JOIN experience e ON ue.experience_id = e.id
          LEFT JOIN user_study us ON u.id = us.user_id
          LEFT JOIN study s ON us.study_id = s.id
          WHERE LOWER(CONCAT_WS(' ', u.name, u.email, u.mobile, u.address, u.birthday, r.type, a.type, u.employment_type, u.position, u.grade, u.salary, u.hired_at, e.title, s.title)) LIKE CONCAT('%', ?1, '%')""";
    public static final String USER_ACTIVE_FILTER_QUERY = USER_FILTER_QUERY + " AND u.is_active = true";

    public static final String EXPERIENCE_FILTER_KEY = "intern";
    public static final String FEEDBACK_FILTER_KEY = "optim";
    public static final String STUDY_FILTER_KEY = "special";
    public static final String USER_FILTER_KEY = "front";

    public static final Pageable PAGEABLE_PAGE1 = PageRequest.of(0, 2, Sort.Direction.ASC, "id");
    public static final Pageable PAGEABLE_PAGE2 = PageRequest.of(1, 2, Sort.Direction.ASC, "id");
    public static final Pageable PAGEABLE_PAGE3 = PageRequest.of(2, 2, Sort.Direction.ASC, "id");
    public static final Pageable PAGEABLE = PageRequest.of(0, 2, Sort.Direction.ASC, "id");
}
