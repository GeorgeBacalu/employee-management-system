DROP TABLE IF EXISTS user_authority, trainer_study, trainer_experience, trainer_authority, employee_study, employee_experience, employee_authority, trainer, study, role, feedback, experience, employee, authority, _user;

CREATE TABLE authority (
    id SERIAL PRIMARY KEY,
    type VARCHAR(255) UNIQUE CHECK (type IN ('CREATE', 'READ', 'UPDATE', 'DELETE'))
);

CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    type VARCHAR(255) UNIQUE CHECK (type IN ('USER', 'ADMIN'))
);

CREATE TABLE experience (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    organization VARCHAR(255),
    description VARCHAR(255),
    type VARCHAR(255) CHECK (type IN ('APPRENTICESHIP', 'INTERNSHIP', 'TRAINING', 'VOLUNTEERING', 'PROJECT', 'PREVIOUS_JOB')),
    started_at DATE,
    finished_at DATE
);

CREATE TABLE study (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255),
    institution VARCHAR(255),
    description VARCHAR(255),
    type VARCHAR(255) CHECK (type IN ('BACHELORS', 'MASTERS', 'SPECIALIZED_TRAINING')),
    started_at DATE,
    finished_at DATE
);

CREATE TABLE _user (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    mobile VARCHAR(255),
    address VARCHAR(255),
    birthday DATE,
    role_id INTEGER REFERENCES role,
    is_active BOOLEAN
);

CREATE TABLE feedback (
    id SERIAL PRIMARY KEY,
    type VARCHAR(255) CHECK (type IN ('ISSUE', 'OPTIMIZATION', 'IMPROVEMENT')),
    description VARCHAR(255),
    sent_at TIMESTAMP(6),
    user_id INTEGER REFERENCES _user
);

CREATE TABLE trainer (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    mobile VARCHAR(255),
    address VARCHAR(255),
    birthday DATE,
    role_id INTEGER REFERENCES role,
    is_active BOOLEAN,
    employment_type VARCHAR(255) CHECK (employment_type IN ('PART_TIME', 'FULL_TIME')),
    position VARCHAR(255) CHECK (position IN ('FRONTEND', 'BACKEND', 'DEVOPS', 'TESTING', 'DESIGN', 'DATA_ANALYST', 'MACHINE_LEARNING', 'BUSINESS_ANALYST', 'SCRUM_MASTER')),
    grade VARCHAR(255) CHECK (grade IN ('JUNIOR', 'MID', 'SENIOR')),
    salary DOUBLE PRECISION,
    hired_at DATE,
    trainer_id INTEGER REFERENCES trainer,
    nr_trainees INTEGER,
    max_trainees INTEGER
);

CREATE TABLE employee (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    email VARCHAR(255) UNIQUE,
    password VARCHAR(255),
    mobile VARCHAR(255),
    address VARCHAR(255),
    birthday DATE,
    role_id INTEGER REFERENCES role,
    is_active BOOLEAN,
    employment_type VARCHAR(255) CHECK (employment_type IN ('PART_TIME', 'FULL_TIME')),
    position VARCHAR(255) CHECK (position IN ('FRONTEND', 'BACKEND', 'DEVOPS', 'TESTING', 'DESIGN', 'DATA_ANALYST', 'MACHINE_LEARNING', 'BUSINESS_ANALYST', 'SCRUM_MASTER')),
    grade VARCHAR(255) CHECK (grade IN ('JUNIOR', 'MID', 'SENIOR')),
    salary DOUBLE PRECISION,
    hired_at DATE,
    trainer_id INTEGER REFERENCES trainer
);

CREATE TABLE employee_authority (
    authority_id INTEGER REFERENCES authority,
    employee_id INTEGER REFERENCES employee,
    PRIMARY KEY (authority_id, employee_id)
);

CREATE TABLE employee_experience (
    employee_id INTEGER REFERENCES employee,
    experience_id INTEGER REFERENCES experience,
    PRIMARY KEY (employee_id, experience_id)
);

CREATE TABLE employee_study (
    employee_id INTEGER REFERENCES employee,
    study_id INTEGER REFERENCES study,
    PRIMARY KEY (employee_id, study_id)
);

CREATE TABLE trainer_authority (
    authority_id INTEGER REFERENCES authority,
    trainer_id INTEGER REFERENCES trainer,
    PRIMARY KEY (authority_id, trainer_id)
);

CREATE TABLE trainer_experience (
    experience_id INTEGER REFERENCES experience,
    trainer_id INTEGER REFERENCES trainer,
    PRIMARY KEY (experience_id, trainer_id)
);

CREATE TABLE trainer_study (
    study_id INTEGER REFERENCES study,
    trainer_id INTEGER REFERENCES trainer,
    PRIMARY KEY (study_id, trainer_id)
);

CREATE TABLE user_authority (
    authority_id INTEGER REFERENCES authority,
    user_id INTEGER REFERENCES _user,
    PRIMARY KEY (authority_id, user_id)
);