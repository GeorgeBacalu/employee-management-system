INSERT INTO role(type) VALUES ('USER'), ('ADMIN');

INSERT INTO authority(type) VALUES ('CREATE'), ('READ'), ('UPDATE'), ('DELETE');

INSERT INTO _user(name, email, password, mobile, address, birthday, role_id, is_active) VALUES
('test_name1', 'test_email1@email.com', '#Test_password1', '+40700000000', 'test_address1', '2000-01-01', 1, true),
('test_name2', 'test_email2@email.com', '#Test_password2', '+40700000001', 'test_address2', '2000-01-02', 2, false);

INSERT INTO feedback(type, description, user_id, sent_at) VALUES
('ISSUE', 'test_description1', 1, '2024-01-01 00:00'),
('IMPROVEMENT', 'test_description2', 2, '2024-01-01 00:00');

INSERT INTO experience(title, organization, description, type, started_at, finished_at) VALUES
('test_title1', 'test_organization1', 'test_description1', 'APPRENTICESHIP', '2020-01-01', '2021-01-01'),
('test_title2', 'test_organization2', 'test_description2', 'INTERNSHIP', '2020-01-02', '2021-01-02'),
('test_title3', 'test_organization3', 'test_description3', 'TRAINING', '2020-01-03', '2021-01-03'),
('test_title4', 'test_organization4', 'test_description4', 'VOLUNTEERING', '2020-01-04', '2021-01-04');

INSERT INTO study(title, institution, description, type, started_at, finished_at) VALUES
('test_title1', 'test_institution1', 'test_description1', 'BACHELORS', '2020-01-01', '2021-01-01'),
('test_title2', 'test_institution2', 'test_description2', 'MASTERS', '2020-01-02', '2021-01-02'),
('test_title3', 'test_institution3', 'test_description3', 'BACHELORS', '2020-01-03', '2021-01-03'),
('test_title4', 'test_institution4', 'test_description4', 'MASTERS', '2020-01-04', '2021-01-04');

INSERT INTO trainer(name, email, password, mobile, address, birthday, role_id, is_active, employment_type, position, grade, salary, hired_at, trainer_id, nr_trainees, max_trainees) VALUES
('test_name1', 'test_email1@email.com', '#Test_password1', '+40700000000', 'test_address1', '2000-01-01', 1, true, 'FULL_TIME', 'FRONTEND', 'SENIOR', 7500.0, '2020-01-01', null, 2, 3),
('test_name2', 'test_email2@email.com', '#Test_password2', '+40700000001', 'test_address2', '2000-01-02', 2, false, 'FULL_TIME', 'BACKEND', 'SENIOR', 7000.0, '2020-01-02', 1, 1, 2);

INSERT INTO employee(name, email, password, mobile, address, birthday, role_id, is_active, employment_type, position, grade, salary, hired_at, trainer_id) VALUES
('test_name1', 'test_email1@email.com', '#Test_password1', '+40700000000', 'test_address1', '2000-01-01', 1, true, 'FULL_TIME', 'FRONTEND', 'JUNIOR', 5000.0, '2020-01-01', 1),
('test_name2', 'test_email2@email.com', '#Test_password2', '+40700000001', 'test_address2', '2000-01-02', 2, false, 'PART_TIME', 'BACKEND', 'JUNIOR', 3500.0, '2020-01-02', 2);

INSERT INTO user_authority(user_id, authority_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);

INSERT INTO trainer_authority(trainer_id, authority_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);

INSERT INTO trainer_experience(trainer_id, experience_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);

INSERT INTO trainer_study(trainer_id, study_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);

INSERT INTO employee_authority(employee_id, authority_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);

INSERT INTO employee_experience(employee_id, experience_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);

INSERT INTO employee_study(employee_id, study_id) VALUES (1, 1), (1, 2), (2, 3), (2, 4);