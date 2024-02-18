INSERT INTO role(id, type) VALUES (1, 'USER'), (2, 'ADMIN');

INSERT INTO authority(id, type) VALUES (1, 'CREATE'), (2, 'READ'), (3, 'UPDATE'), (4, 'DELETE');

INSERT INTO _user(id, name, email, password, mobile, address, birthday, role_id, is_active) VALUES
(1, 'John Doe', 'john.doe@example.com', '#John_Doe_Password0', '+40721543701', '123 Main St, Boston, USA', '1980-02-15', 2, true),
(2, 'Jane Smith', 'jane.smith@example.com', '#Jane_Smith_Password0', '+40756321802', '456 Oak St, London, UK', '1982-07-10', 2, true),
(3, 'Michael Johnson', 'michael.johnson@example.com', '#Michael_Johnson_Password0', '+40789712303', '789 Pine St, Madrid, Spain', '1990-11-20', 2, true),
(4, 'Laura Brown', 'laura.brown@example.com', '#Laura_Brown_Password0', '+40734289604', '333 Elm St, Paris, France', '1985-08-25', 2, true),
(5, 'Robert Davis', 'robert.davis@example.com', '#Robert_Davis_Password0', '+40754321805', '555 Oak St, Berlin, Germany', '1988-05-12', 1, true),
(6, 'Emily Wilson', 'emily.wilson@example.com', '#Emily_Wilson_Password0', '+40789012606', '777 Pine St, Sydney, Australia', '1995-09-08', 1, true),
(7, 'Michaela Taylor', 'michaela.taylor@example.com', '#Michaela_Taylor_Password0', '+40723145607', '999 Elm St, Rome, Italy', '1983-12-07', 1, true),
(8, 'David Anderson', 'david.anderson@example.com', '#David_Anderson_Password0', '+40787654308', '111 Oak St, Moscow, Russia', '1992-04-23', 1, true),
(9, 'Sophia Garcia', 'sophia.garcia@example.com', '#Sophia_Garcia_Password0', '+40754321809', '333 Pine St, Athens, Greece', '1998-07-30', 1, true),
(10, 'Joseph Wilson', 'joseph.wilson@example.com', '#Joseph_Wilson_Password0', '+40789012610', '555 Elm St, Madrid, Spain', '1991-03-14', 1, true),
(11, 'Olivia Martinez', 'olivia.martinez@example.com', '#Olivia_Martinez_Password0', '+40723145611', '777 Oak St, Tokyo, Japan', '1999-10-17', 1, true),
(12, 'Daniel Thompson', 'daniel.thompson@example.com', '#Daniel_Thompson_Password0', '+40787654312', '999 Elm St, Seoul, South Korea', '1994-06-09', 1, true),
(13, 'Emma Thompson', 'emma.thompson@example.com', '#Emma_Thompson_Password0', '+40754321813', '111 Pine St, Beijing, China', '2000-12-22', 1, true),
(14, 'Liam Brown', 'liam.brown@example.com', '#Liam_Brown_Password0', '+40789012614', '333 Oak St, Cape Town, South Africa', '1997-09-04', 1, true),
(15, 'Olivia Wilson', 'olivia.wilson@example.com', '#Olivia_Wilson_Password0', '+40723145615', '555 Elm St, Buenos Aires, Argentina', '2001-04-07', 1, true),
(16, 'Noah Taylor', 'noah.taylor@example.com', '#Noah_Taylor_Password0', '+40787654316', '777 Pine St, Rio de Janeiro, Brazil', '1996-11-19', 1, false),
(17, 'Ava Johnson', 'ava.johnson@example.com', '#Ava_Johnson_Password0', '+40754321817', '999 Oak St, Mexico City, Mexico', '2002-06-02', 1, true),
(18, 'William Davis', 'william.davis@example.com', '#William_Davis_Password0', '+40789012618', '111 Elm St, Vancouver, Canada', '1993-02-25', 1, true),
(19, 'Sophia Martinez', 'sophia.martinez@example.com', '#Sophia_Martinez_Password0', '+40723145619', '333 Oak St, Paris, France', '2003-09-08', 1, true),
(20, 'Isabella Anderson', 'isabella.anderson@example.com', '#Isabella_Anderson_Password0', '+40787654320', '555 Elm St, London, UK', '1999-06-22', 1, false),
(21, 'Mason Thompson', 'mason.thompson@example.com', '#Mason_Thompson_Password0', '+40754321821', '777 Pine St, Berlin, Germany', '2002-12-20', 1, true),
(22, 'Charlotte Thompson', 'charlotte.thompson@example.com', '#Charlotte_Thompson_Password0', '+40789012622', '999 Oak St, Moscow, Russia', '1998-10-18', 1, true),
(23, 'Elijah Smith', 'elijah.smith@example.com', '#Elijah_Smith_Password0', '+40723145623', '111 Elm St, Athens, Greece', '2003-05-03', 1, true),
(24, 'Amelia Johnson', 'amelia.johnson@example.com', '#Amelia_Johnson_Password0', '+40787654324', '333 Pine St, Madrid, Spain', '1998-12-14', 1, false),
(25, 'Harper Wilson', 'harper.wilson@example.com', '#Harper_Wilson_Password0', '+40754321825', '555 Oak St, Tokyo, Japan', '2001-07-27', 1, true),
(26, 'Benjamin Thompson', 'benjamin.thompson@example.com', '#Benjamin_Thompson_Password0', '+40789012626', '777 Elm St, Seoul, South Korea', '2001-02-09', 1, true),
(27, 'Liam Thompson', 'liam.thompson@example.com', '#Liam_Thompson_Password0', '+40723145627', '999 Oak St, Beijing, China', '2000-09-23', 1, true),
(28, 'Grace Martinez', 'grace.martinez@example.com', '#Grace_Martinez_Password0', '+40787654328', '111 Elm St, Cape Town, South Africa', '2002-06-06', 1, false),
(29, 'Isabella White', 'isabella.white@example.com', '#Isabella_White_Password0', '+40754321829', '333 Pine St, Buenos Aires, Argentina', '2002-01-19', 1, true),
(30, 'Logan Thompson', 'logan.thompson@example.com', '#Logan_Thompson_Password0', '+40789012630', '555 Elm St, Rio de Janeiro, Brazil', '2003-07-02', 1, true),
(31, 'Evelyn Brown', 'evelyn.brown@example.com', '#Evelyn_Brown_Password0', '+40723145631', '777 Oak St, Mexico City, Mexico', '1999-03-16', 1, true),
(32, 'Henry Davis', 'henry.davis@example.com', '#Henry_Davis_Password0', '+40787654332', '999 Elm St, Vancouver, Canada', '1998-10-29', 1, false),
(33, 'Sofia Smith', 'sofia.smith@example.com', '#Sofia_Smith_Password0', '+40754321833', '111 Oak St, Paris, France', '2000-06-11', 1, true),
(34, 'Jack Wilson', 'jack.wilson@example.com', '#Jack_Wilson_Password0', '+40789012634', '333 Elm St, London, UK', '1997-01-24', 1, true),
(35, 'Emily Anderson', 'emily.anderson@example.com', '#Emily_Anderson_Password0', '+40723145635', '555 Pine St, Berlin, Germany', '1995-08-06', 1, true),
(36, 'Adrian Thompson', 'adrian.thompson@example.com', '#Adrian_Thompson_Password0', '+40787654336', '777 Elm St, Moscow, Russia', '1996-03-20', 1, false),
(37, 'Abigail Johnson', 'abigail.johnson@example.com', '#Abigail_Johnson_Password0', '+40754321837', '999 Oak St, Athens, Greece', '2000-10-02', 1, true),
(38, 'Michael Davis', 'michael.davis@example.com', '#Michael_Davis_Password0', '+40789012638', '111 Oak St, Madrid, Spain', '1994-05-16', 1, true),
(39, 'Mia Wilson', 'mia.wilson@example.com', '#Mia_Wilson_Password0', '+40723145639', '333 Elm St, Tokyo, Japan', '1990-12-29', 1, true),
(40, 'James Lee', 'james.lee@example.com', '#James_Lee_Password0', '+40787654340', '555 Pine St, Seoul, South Korea', '1991-08-11', 1, true),
(41, 'Maria Thompson', 'maria.thompson@example.com', '#Maria_Thompson_Password0', '+40754321841', '777 Elm St, Beijing, China', '1993-03-24', 1, true),
(42, 'Ethan Smith', 'ethan.smith@example.com', '#Ethan_Smith_Password0', '+40789012642', '999 Oak St, Cape Town, South Africa', '1989-11-06', 1, true),
(43, 'Olivia Smith', 'olivia.smith@example.com', '#Olivia_Smith_Password0', '+40723145643', '111 Elm St, Buenos Aires, Argentina', '1994-06-19', 1, true),
(44, 'Emily Davis', 'emily.davis@example.com', '#Emily_Davis_Password0', '+40787654344', '333 Elm St, Rio de Janeiro, Brazil', '1998-01-01', 1, true),
(45, 'Henry Wilson', 'henry.wilson@example.com', '#Henry_Wilson_Password0', '+40754321845', '555 Pine St, Mexico City, Mexico', '2001-08-14', 1, true),
(46, 'Scarlett Thompson', 'scarlett.thompson@example.com', '#Scarlett_Thompson_Password0', '+40789012646', '777 Elm St, Vancouver, Canada', '2002-03-28', 1, true),
(47, 'Jacob Brown', 'jacob.brown@example.com', '#Jacob_Brown_Password0', '+40723145647', '999 Pine St, Paris, France', '1999-11-10', 1, true),
(48, 'Ava Smith', 'ava.smith@example.com', '#Ava_Smith_Password0', '+40787654348', '111 Pine St, London, UK', '1986-06-23', 1, true),
(49, 'Oliver Johnson', 'oliver.johnson@example.com', '#Oliver_Johnson_Password0', '+40754321849', '333 Elm St, Berlin, Germany', '1988-02-05', 1, false),
(50, 'Sophia Wilson', 'sophia.wilson@example.com', '#Sophia_Wilson_Password0', '+40789012650', '555 Elm St, Moscow, Russia', '1994-09-19', 1, true),
(51, 'William Harris', 'william.harris@example.com', '#William_Harris_Password0', '+40723145651', '777 Pine St, Athens, Greece', '1996-04-03', 1, true),
(52, 'Mia Johnson', 'mia.johnson@example.com', '#Mia_Johnson_Password0', '+40787654352', '999 Oak St, Madrid, Spain', '1998-11-16', 1, true),
(53, 'Jacob Lee', 'jacob.lee@example.com', '#Jacob_Lee_Password0', '+40754321853', '111 Elm St, Tokyo, Japan', '1997-06-29', 1, false),
(54, 'Charlotte Brown', 'charlotte.brown@example.com', '#Charlotte_Brown_Password0', '+40789012654', '333 Pine St, Seoul, South Korea', '2000-02-12', 1, true),
(55, 'Ethan Johnson', 'ethan.johnson@example.com', '#Ethan_Johnson_Password0', '+40723145655', '555 Elm St, Beijing, China', '1998-09-25', 1, true),
(56, 'Sophia Davis', 'sophia.davis@example.com', '#Sophia_Davis_Password0', '+40787654356', '777 Elm St, Cape Town, South Africa', '2001-07-09', 1, true),
(57, 'Jacob Thompson', 'jacob.thompson@example.com', '#Jacob_Thompson_Password0', '+40754321857', '999 Pine St, Buenos Aires, Argentina', '1996-02-22', 1, false),
(58, 'Liam Wilson', 'liam.wilson@example.com', '#Liam_Wilson_Password0', '+40789012658', '111 Elm St, Rio de Janeiro, Brazil', '1995-10-07', 1, true),
(59, 'Ava Brown', 'ava.brown@example.com', '#Ava_Brown_Password0', '+40723145659', '333 Elm St, Mexico City, Mexico', '1998-07-21', 1, true),
(60, 'Scarlett Wilson', 'scarlett.wilson@example.com', '#Scarlett_Wilson_Password0', '+40787654360', '555 Elm St, Vancouver, Canada', '2001-05-05', 1, true),
(61, 'Noah Martinez', 'noah.martinez@example.com', '#Noah_Martinez_Password0', '+40754321861', '777 Pine St, Paris, France', '1999-12-17', 1, false),
(62, 'Mia Anderson', 'mia.anderson@example.com', '#Mia_Anderson_Password0', '+40789012662', '999 Oak St, London, UK', '2002-09-30', 1, true),
(63, 'Grace Lee', 'grace.lee@example.com', '#Grace_Lee_Password0', '+40723145663', '111 Elm St, Berlin, Germany', '1996-05-14', 1, true),
(64, 'Benjamin Taylor', 'benjamin.taylor@example.com', '#Benjamin_Taylor_Password0', '+40787654364', '333 Elm St, Moscow, Russia', '2002-12-27', 1, true),
(65, 'Elijah Roberts', 'elijah.roberts@example.com', '#Elijah_Roberts_Password0', '+40754321865', '555 Pine St, Sydney, Australia', '2003-01-27', 1, false),
(66, 'Amelia Walker', 'amelia.walker@example.com', '#Amelia_Walker_Password0', '+40789012666', '777 Oak St, Rome, Italy', '1992-08-12', 1, true),
(67, 'Daniel Green', 'daniel.green@example.com', '#Daniel_Green_Password0', '+40723145667', '999 Elm St, Moscow, Russia', '1996-03-27', 1, true),
(68, 'Liam Hall', 'liam.hall@example.com', '#Liam_Hall_Password0', '+40787654368', '111 Oak St, Athens, Greece', '1998-11-09', 1, true),
(69, 'Sophia Young', 'sophia.young@example.com', '#Sophia_Young_Password0', '+40754321869', '333 Pine St, Madrid, Spain', '1995-06-23', 1, false),
(70, 'Noah Clark', 'noah.clark@example.com', '#Noah_Clark_Password0', '+40789012670', '555 Elm St, Tokyo, Japan', '1997-02-05', 1, true),
(71, 'Olivia Hill', 'olivia.hill@example.com', '#Olivia_Hill_Password0', '+40723145671', '777 Oak St, Seoul, South Korea', '2001-09-20', 1, true),
(72, 'Michaela Allen', 'michaela.allen@example.com', '#Michaela_Allen_Password0', '+40787654372', '999 Pine St, Beijing, China', '1998-05-06', 1, true);

INSERT INTO feedback(id, type, description, user_id, sent_at) VALUES
(1, 'OPTIMIZATION', 'Improve page load time for the dashboard.', 1, '2024-04-21 09:15'),
(2, 'OPTIMIZATION', 'Optimize search functionality for better results.', 2, '2024-04-25 10:50'),
(3, 'OPTIMIZATION', 'Compress images to improve page load time.', 3, '2024-05-01 09:45'),
(4, 'OPTIMIZATION', 'Implement lazy loading for faster initial load.', 4, '2024-05-04 17:20'),
(5, 'ISSUE', 'App crashes when submitting a form.', 5, '2024-04-20 14:30'),
(6, 'ISSUE', 'Error message appears when uploading an image.', 6, '2024-04-23 11:10'),
(7, 'ISSUE', 'Login issues after resetting the password.', 7, '2024-04-26 17:20'),
(8, 'ISSUE', 'Notifications not appearing on the mobile app.', 8, '2024-05-02 16:10'),
(9, 'IMPROVEMENT', 'Add a dark mode for better user experience.', 9, '2024-04-22 16:45'),
(10, 'IMPROVEMENT', 'Add more filtering options in the search bar.', 10, '2024-04-27 15:40'),
(11, 'IMPROVEMENT', 'Include an option to save items to a wishlist.', 11, '2024-04-30 14:15'),
(12, 'IMPROVEMENT', 'Allow users to customize their profile layout.', 12, '2024-05-03 11:35');

INSERT INTO experience(id, title, organization, description, type, started_at, finished_at) VALUES
(1, 'Software Engineering Intern', 'Google', 'Worked as a Software Engineering Intern at Google, gaining hands-on experience in developing scalable software solutions.', 'INTERNSHIP', '2019-06-01', '2019-08-31'),
(2, 'Data Science Intern', 'Facebook', 'Completed a Data Science Internship at Facebook, working on data analysis and machine learning projects to gain insights and drive business decisions.', 'INTERNSHIP', '2020-06-01', '2020-08-31'),
(3, 'Backend Developer Intern', 'Amazon', 'Interned as a Backend Developer at Amazon, contributing to the development of high-performance backend systems for e-commerce applications.', 'INTERNSHIP', '2019-05-01', '2019-07-31'),
(4, 'Frontend Developer Intern', 'Microsoft', 'Participated in a Frontend Developer Internship at Microsoft, collaborating on the development of user-friendly and responsive web interfaces.', 'INTERNSHIP', '2020-05-15', '2020-08-15'),
(5, 'Machine Learning Trainee', 'Apple', 'Underwent comprehensive training in Machine Learning at Apple, exploring advanced algorithms and applying them to real-world data analysis challenges.', 'TRAINING', '2018-06-15', '2018-09-15'),
(6, 'Web Development Trainee', 'IBM', 'Completed a rigorous training program in Web Development at IBM, acquiring skills in front-end and back-end development and working on industry projects.', 'TRAINING', '2017-06-01', '2017-12-31'),
(7, 'Software Developer Trainee', 'Intel', 'Engaged in a Software Developer Training program at Intel, learning best practices in software engineering and gaining practical experience in the software development lifecycle.', 'TRAINING', '2018-06-01', '2018-08-31'),
(8, 'Mobile Application Developer Trainee', 'Uber', 'Participated in a Mobile Application Development Training program at Uber, learning mobile development frameworks and building innovative mobile applications.', 'TRAINING', '2019-09-01', '2019-11-30'),
(9, 'Cloud Computing Apprentice', 'Oracle', 'Completed an apprenticeship program in Cloud Computing at Oracle, gaining expertise in cloud infrastructure and deploying scalable solutions.', 'APPRENTICESHIP', '2019-01-01', '2019-06-30'),
(10, 'Full Stack Developer Apprentice', 'Airbnb', 'Engaged in an apprenticeship program as a Full Stack Developer at Airbnb, developing end-to-end web applications and learning agile development methodologies.', 'APPRENTICESHIP', '2018-01-15', '2018-07-15'),
(11, 'Cyber-security Apprentice', 'Cisco', 'Participated in an apprenticeship program in Cyber-security at Cisco, learning about network security, vulnerability assessments, and threat mitigation strategies.', 'APPRENTICESHIP', '2019-02-01', '2019-08-31'),
(12, 'Database Administration Apprentice', 'Salesforce', 'Completed an apprenticeship program in Database Administration at Salesforce, gaining expertise in managing and optimizing database systems.', 'APPRENTICESHIP', '2020-03-01', '2020-09-30'),
(13, 'Open Source Contributor', 'Mozilla', 'Contributed to open source projects at Mozilla, collaborating with developers worldwide to enhance software functionality and address issues.', 'VOLUNTEERING', '2017-09-01', '2018-03-31'),
(14, 'Code Mentor', 'Codecademy', 'Volunteered as a Code Mentor at Codecademy, providing guidance and support to individuals learning to code and helping them overcome programming challenges.', 'VOLUNTEERING', '2020-02-01', '2020-06-30'),
(15, 'Hackathon Organizer', 'TechCrunch', 'Organized hackathons at TechCrunch, bringing together developers, designers, and innovators to collaborate and create innovative solutions within a limited timeframe.', 'VOLUNTEERING', '2019-01-01', '2019-06-30'),
(16, 'Teaching Assistant', 'Coursera', 'Served as a Teaching Assistant for online courses on Coursera, providing guidance and support to learners, grading assignments, and facilitating discussions.', 'VOLUNTEERING', '2018-09-01', '2018-12-31');

INSERT INTO study(id, title, institution, description, type, started_at, finished_at) VALUES
(1, 'Data Science Bootcamp', 'DataCamp', 'Data Science specialized training at DataCamp', 'SPECIALIZED_TRAINING', '2022-01-15', '2022-06-30'),
(2, 'Web Development Immersive', 'General Assembly', 'Web Development specialized training at General Assembly', 'SPECIALIZED_TRAINING', '2021-07-01', '2022-01-31'),
(3, 'Digital Marketing Certification', 'HubSpot Academy', 'Digital Marketing Certification from HubSpot Academy', 'SPECIALIZED_TRAINING', '2023-03-01', '2023-06-30'),
(4, 'UX/UI Design Workshop', 'Interaction Design Foundation', 'UX/UI Design Workshop at Interaction Design Foundation', 'SPECIALIZED_TRAINING', '2022-09-01', '2022-11-30'),
(5, 'Harvard University', 'Faculty of Arts and Sciences', 'Bachelor''s degree in Economics at Harvard University - Faculty of Arts and Sciences', 'BACHELORS', '2012-08-31', '2016-06-01'),
(6, 'University of Chicago', 'Booth School of Business', 'Bachelor''s in Business Analysis at University of Chicago - Booth School of Business', 'BACHELORS', '2017-06-01', '2020-03-01'),
(7, 'Massachusetts Institute of Technology', 'School of Science', 'Bachelor''s degree in Physics at Massachusetts Institute of Technology - School of Science', 'BACHELORS', '2013-09-01', '2017-05-31'),
(8, 'University of Cambridge', 'Faculty of Mathematics', 'Bachelor''s degree in Mathematics at University of Cambridge - Faculty of Mathematics', 'BACHELORS', '2019-10-01', '2022-06-30'),
(9, 'Stanford University', 'School of Engineering', 'Master''s degree in Computer Science at Stanford University - School of Engineering', 'MASTERS', '2016-08-31', '2019-06-01'),
(10, 'University College London', 'Faculty of Engineering', 'Master''s degree in Software Engineering at University College London - Faculty of Engineering', 'MASTERS', '2020-03-01', '2023-03-01'),
(11, 'ETH Zurich', 'Department of Computer Science', 'Bachelor''s degree in Artificial Intelligence at ETH Zurich - Department of Computer Science', 'MASTERS', '2017-09-01', '2020-08-31'),
(12, 'California Institute of Technology', 'Division of Engineering and Applied Science', 'Master''s degree in Electrical Engineering at California Institute of Technology - Division of Engineering and Applied Science', 'MASTERS', '2018-09-01', '2021-05-31');

INSERT INTO trainer(id, name, email, password, mobile, address, birthday, role_id, is_active, employment_type, position, grade, salary, hired_at, trainer_id, nr_trainees, max_trainees) VALUES
(1, 'John Doe', 'john.doe@example.com', '#John_Doe_Password0', '+40721543701', '123 Main St, Boston, USA', '1980-02-15', 2, true, 'FULL_TIME', 'FRONTEND', 'SENIOR', 21000, '2015-01-01', null, 7, 10),
(2, 'Jane Smith', 'jane.smith@example.com', '#Jane_Smith_Password0', '+40756321802', '456 Oak St, London, UK', '1982-07-10', 2, true, 'FULL_TIME', 'FRONTEND', 'SENIOR', 19000, '2017-01-01', null, 7, 10),
(3, 'Michael Johnson', 'michael.johnson@example.com', '#Michael_Johnson_Password0', '+40789712303', '789 Pine St, Madrid, Spain', '1990-11-20', 2, true, 'FULL_TIME', 'FRONTEND', 'SENIOR', 17000, '2018-01-01', null, 7, 10),
(4, 'Laura Brown', 'laura.brown@example.com', '#Laura_Brown_Password0', '+40734289604', '333 Elm St, Paris, France', '1985-08-25', 2, true, 'FULL_TIME', 'FRONTEND', 'SENIOR', 16500, '2018-02-04', null, 7, 10),
(5, 'Robert Davis', 'robert.davis@example.com', '#Robert_Davis_Password0', '+40754321805', '555 Oak St, Berlin, Germany', '1988-05-12', 1, true, 'FULL_TIME', 'BACKEND', 'SENIOR', 16750, '2017-03-18', null, 7, 10),
(6, 'Emily Wilson', 'emily.wilson@example.com', '#Emily_Wilson_Password0', '+40789012606', '777 Pine St, Sydney, Australia', '1995-09-08', 1, true, 'FULL_TIME', 'BACKEND', 'SENIOR', 15000, '2018-06-20', null, 7, 10),
(7, 'Michaela Taylor', 'michaela.taylor@example.com', '#Michaela_Taylor_Password0', '+40723145607', '999 Elm St, Rome, Italy', '1983-12-07', 1, true, 'FULL_TIME', 'BACKEND', 'SENIOR', 15750, '2019-01-20', 1, 1, 5),
(8, 'David Anderson', 'david.anderson@example.com', '#David_Anderson_Password0', '+40787654308', '111 Oak St, Moscow, Russia', '1992-04-23', 1, true, 'FULL_TIME', 'BACKEND', 'SENIOR', 18250, '2016-04-09', 1, 1, 5),
(9, 'Sophia Garcia', 'sophia.garcia@example.com', '#Sophia_Garcia_Password0', '+40754321809', '333 Pine St, Athens, Greece', '1998-07-30', 1, true, 'FULL_TIME', 'DEVOPS', 'SENIOR', 18000, '2014-11-29', 1, 1, 5),
(10, 'Joseph Wilson', 'joseph.wilson@example.com', '#Joseph_Wilson_Password0', '+40789012610', '555 Elm St, Madrid, Spain', '1991-03-14', 1, true, 'FULL_TIME', 'DEVOPS', 'SENIOR', 17000, '2017-08-14', 1, 1, 5),
(11, 'Olivia Martinez', 'olivia.martinez@example.com', '#Olivia_Martinez_Password0', '+40723145611', '777 Oak St, Tokyo, Japan', '1999-10-17', 1, true, 'FULL_TIME', 'DEVOPS', 'SENIOR', 19500, '2015-10-10', 1, 1, 5),
(12, 'Daniel Thompson', 'daniel.thompson@example.com', '#Daniel_Thompson_Password0', '+40787654312', '999 Elm St, Seoul, South Korea', '1994-06-09', 1, true, 'FULL_TIME', 'DEVOPS', 'SENIOR', 14750, '2016-02-15', 1, 1, 5),
(13, 'Emma Thompson', 'emma.thompson@example.com', '#Emma_Thompson_Password0', '+40754321813', '111 Pine St, Beijing, China', '2000-12-22', 1, true, 'FULL_TIME', 'TESTING', 'SENIOR', 16250, '2018-04-08', 2, 1, 5),
(14, 'Liam Brown', 'liam.brown@example.com', '#Liam_Brown_Password0', '+40789012614', '333 Oak St, Cape Town, South Africa', '1997-09-04', 1, true, 'FULL_TIME', 'TESTING', 'SENIOR', 17250, '2017-12-13', 2, 1, 5),
(15, 'Olivia Wilson', 'olivia.wilson@example.com', '#Olivia_Wilson_Password0', '+40723145615', '555 Elm St, Buenos Aires, Argentina', '2001-04-07', 1, true, 'FULL_TIME', 'TESTING', 'SENIOR', 14500, '2018-07-25', 2, 1, 5),
(16, 'Noah Taylor', 'noah.taylor@example.com', '#Noah_Taylor_Password0', '+40787654316', '777 Pine St, Rio de Janeiro, Brazil', '1996-11-19', 1, false, 'FULL_TIME', 'TESTING', 'SENIOR', 15250, '2017-09-30', 2, 1, 5),
(17, 'Ava Johnson', 'ava.johnson@example.com', '#Ava_Johnson_Password0', '+40754321817', '999 Oak St, Mexico City, Mexico', '2002-06-02', 1, true, 'FULL_TIME', 'DESIGN', 'SENIOR', 16000, '2017-05-17', 2, 1, 5),
(18, 'William Davis', 'william.davis@example.com', '#William_Davis_Password0', '+40789012618', '111 Elm St, Vancouver, Canada', '1993-02-25', 1, true, 'FULL_TIME', 'DESIGN', 'SENIOR', 18250, '2016-04-16', 2, 1, 5),
(19, 'Sophia Martinez', 'sophia.martinez@example.com', '#Sophia_Martinez_Password0', '+40723145619', '333 Oak St, Paris, France', '2003-09-08', 1, true, 'PART_TIME', 'DESIGN', 'SENIOR', 19750, '2014-09-09', 3, 1, 5),
(20, 'Isabella Anderson', 'isabella.anderson@example.com', '#Isabella_Anderson_Password0', '+40787654320', '555 Elm St, London, UK', '1999-06-22', 1, false, 'PART_TIME', 'DESIGN', 'SENIOR', 19250, '2015-07-14', 3, 1, 5),
(21, 'Mason Thompson', 'mason.thompson@example.com', '#Mason_Thompson_Password0', '+40754321821', '777 Pine St, Berlin, Germany', '2002-12-20', 1, true, 'PART_TIME', 'DATA_ANALYST', 'SENIOR', 17750, '2017-11-20', 3, 1, 5),
(22, 'Charlotte Thompson', 'charlotte.thompson@example.com', '#Charlotte_Thompson_Password0', '+40789012622', '999 Oak St, Moscow, Russia', '1998-10-18', 1, true, 'PART_TIME', 'DATA_ANALYST', 'SENIOR', 18750, '2016-03-08', 3, 1, 5),
(23, 'Elijah Smith', 'elijah.smith@example.com', '#Elijah_Smith_Password0', '+40723145623', '111 Elm St, Athens, Greece', '2003-05-03', 1, true, 'PART_TIME', 'DATA_ANALYST', 'SENIOR', 17000, '2017-10-18', 3, 1, 5),
(24, 'Amelia Johnson', 'amelia.johnson@example.com', '#Amelia_Johnson_Password0', '+40787654324', '333 Pine St, Madrid, Spain', '1998-12-14', 1, false, 'PART_TIME', 'DATA_ANALYST', 'SENIOR', 15500, '2018-06-03', 3, 1, 5),
(25, 'Harper Wilson', 'harper.wilson@example.com', '#Harper_Wilson_Password0', '+40754321825', '555 Oak St, Tokyo, Japan', '2001-07-27', 1, true, 'PART_TIME', 'MACHINE_LEARNING', 'SENIOR', 20000, '2015-08-17', 4, 1, 5),
(26, 'Benjamin Thompson', 'benjamin.thompson@example.com', '#Benjamin_Thompson_Password0', '+40789012626', '777 Elm St, Seoul, South Korea', '2001-02-09', 1, true, 'PART_TIME', 'MACHINE_LEARNING', 'SENIOR', 20250, '2016-02-20', 4, 1, 5),
(27, 'Liam Thompson', 'liam.thompson@example.com', '#Liam_Thompson_Password0', '+40723145627', '999 Oak St, Beijing, China', '2000-09-23', 1, true, 'PART_TIME', 'MACHINE_LEARNING', 'SENIOR', 18500, '2017-04-20', 4, 1, 5),
(28, 'Grace Martinez', 'grace.martinez@example.com', '#Grace_Martinez_Password0', '+40787654328', '111 Elm St, Cape Town, South Africa', '2002-06-06', 1, false, 'PART_TIME', 'MACHINE_LEARNING', 'SENIOR', 19500, '2016-12-18', 4, 1, 5),
(29, 'Isabella White', 'isabella.white@example.com', '#Isabella_White_Password0', '+40754321829', '333 Pine St, Buenos Aires, Argentina', '2002-01-19', 1, true, 'PART_TIME', 'BUSINESS_ANALYST', 'SENIOR', 16000, '2018-05-14', 4, 1, 5),
(30, 'Logan Thompson', 'logan.thompson@example.com', '#Logan_Thompson_Password0', '+40789012630', '555 Elm St, Rio de Janeiro, Brazil', '2003-07-02', 1, true, 'PART_TIME', 'BUSINESS_ANALYST', 'SENIOR', 20500, '2015-08-07', 4, 1, 5),
(31, 'Evelyn Brown', 'evelyn.brown@example.com', '#Evelyn_Brown_Password0', '+40723145631', '777 Oak St, Mexico City, Mexico', '1999-03-16', 1, true, 'PART_TIME', 'BUSINESS_ANALYST', 'SENIOR', 15500, '2019-03-19', 5, 1, 5),
(32, 'Henry Davis', 'henry.davis@example.com', '#Henry_Davis_Password0', '+40787654332', '999 Elm St, Vancouver, Canada', '1998-10-29', 1, false, 'PART_TIME', 'BUSINESS_ANALYST', 'SENIOR', 18250, '2018-06-12', 5, 1, 5),
(33, 'Sofia Smith', 'sofia.smith@example.com', '#Sofia_Smith_Password0', '+40754321833', '111 Oak St, Paris, France', '2000-06-11', 1, true, 'PART_TIME', 'SCRUM_MASTER', 'SENIOR', 16500, '2019-11-11', 5, 1, 5),
(34, 'Jack Wilson', 'jack.wilson@example.com', '#Jack_Wilson_Password0', '+40789012634', '333 Elm St, London, UK', '1997-01-24', 1, true, 'PART_TIME', 'SCRUM_MASTER', 'SENIOR', 18750, '2018-04-28', 5, 1, 5),
(35, 'Emily Anderson', 'emily.anderson@example.com', '#Emily_Anderson_Password0', '+40723145635', '555 Pine St, Berlin, Germany', '1995-08-06', 1, true, 'PART_TIME', 'SCRUM_MASTER', 'SENIOR', 19000, '2017-01-01', 5, 1, 5),
(36, 'Adrian Thompson', 'adrian.thompson@example.com', '#Adrian_Thompson_Password0', '+40787654336', '777 Elm St, Moscow, Russia', '1996-03-20', 1, false, 'PART_TIME', 'SCRUM_MASTER', 'SENIOR', 20750, '2016-09-14', 5, 1, 5);

INSERT INTO employee(id, name, email, password, mobile, address, birthday, role_id, is_active, employment_type, position, grade, salary, hired_at, trainer_id) VALUES
(1, 'Abigail Johnson', 'abigail.johnson@example.com', '#Abigail_Johnson_Password0', '+40754321837', '999 Oak St, Athens, Greece', '2000-10-02', 1, true, 'FULL_TIME', 'FRONTEND', 'JUNIOR', 4000, '2023-01-01', 1),
(2, 'Michael Davis', 'michael.davis@example.com', '#Michael_Davis_Password0', '+40789012638', '111 Oak St, Madrid, Spain', '1994-05-16', 1, true, 'FULL_TIME', 'FRONTEND', 'JUNIOR', 3500, '2022-10-04', 2),
(3, 'Mia Wilson', 'mia.wilson@example.com', '#Mia_Wilson_Password0', '+40723145639', '333 Elm St, Tokyo, Japan', '1990-12-29', 1, true, 'FULL_TIME', 'FRONTEND', 'MID', 5000, '2020-09-12', 3),
(4, 'James Lee', 'james.lee@example.com', '#James_Lee_Password0', '+40787654340', '555 Pine St, Seoul, South Korea', '1991-08-11', 1, true, 'FULL_TIME', 'FRONTEND', 'SENIOR', 6500, '2021-08-15', 4),
(5, 'Maria Thompson', 'maria.thompson@example.com', '#Maria_Thompson_Password0', '+40754321841', '777 Elm St, Beijing, China', '1993-03-24', 1, true, 'FULL_TIME', 'BACKEND', 'JUNIOR', 3000, '2023-02-09', 5),
(6, 'Ethan Smith', 'ethan.smith@example.com', '#Ethan_Smith_Password0', '+40789012642', '999 Oak St, Cape Town, South Africa', '1989-11-06', 1, true, 'FULL_TIME', 'BACKEND', 'JUNIOR', 2500, '2022-11-16', 6),
(7, 'Olivia Smith', 'olivia.smith@example.com', '#Olivia_Smith_Password0', '+40723145643', '111 Elm St, Buenos Aires, Argentina', '1994-06-19', 1, true, 'FULL_TIME', 'BACKEND', 'MID', 4000, '2021-07-19', 7),
(8, 'Emily Davis', 'emily.davis@example.com', '#Emily_Davis_Password0', '+40787654344', '333 Elm St, Rio de Janeiro, Brazil', '1998-01-01', 1, true, 'FULL_TIME', 'BACKEND', 'SENIOR', 7000, '2020-02-16', 8),
(9, 'Henry Wilson', 'henry.wilson@example.com', '#Henry_Wilson_Password0', '+40754321845', '555 Pine St, Mexico City, Mexico', '2001-08-14', 1, true, 'FULL_TIME', 'DEVOPS', 'JUNIOR', 4500, '2022-10-14', 9),
(10, 'Scarlett Thompson', 'scarlett.thompson@example.com', '#Scarlett_Thompson_Password0', '+40789012646', '777 Elm St, Vancouver, Canada', '2002-03-28', 1, true, 'FULL_TIME', 'DEVOPS', 'JUNIOR', 5000, '2022-03-28', 10),
(11, 'Jacob Brown', 'jacob.brown@example.com', '#Jacob_Brown_Password0', '+40723145647', '999 Pine St, Paris, France', '1999-11-10', 1, true, 'FULL_TIME', 'DEVOPS', 'MID', 6000, '2021-11-10', 11),
(12, 'Ava Smith', 'ava.smith@example.com', '#Ava_Smith_Password0', '+40787654348', '111 Pine St, London, UK', '1986-06-23', 1, true, 'FULL_TIME', 'DEVOPS', 'SENIOR', 7500, '2020-05-28', 12),
(13, 'Oliver Johnson', 'oliver.johnson@example.com', '#Oliver_Johnson_Password0', '+40754321849', '333 Elm St, Berlin, Germany', '1988-02-05', 1, false, 'FULL_TIME', 'TESTING', 'JUNIOR', 4000, '2023-01-20', 13),
(14, 'Sophia Wilson', 'sophia.wilson@example.com', '#Sophia_Wilson_Password0', '+40789012650', '555 Elm St, Moscow, Russia', '1994-09-19', 1, true, 'FULL_TIME', 'TESTING', 'JUNIOR', 4500, '2022-04-16', 14),
(15, 'William Harris', 'william.harris@example.com', '#William_Harris_Password0', '+40723145651', '777 Pine St, Athens, Greece', '1996-04-03', 1, true, 'FULL_TIME', 'TESTING', 'MID', 6000, '2021-05-18', 15),
(16, 'Mia Johnson', 'mia.johnson@example.com', '#Mia_Johnson_Password0', '+40787654352', '999 Oak St, Madrid, Spain', '1998-11-16', 1, true, 'FULL_TIME', 'TESTING', 'SENIOR', 7000, '2020-09-15', 16),
(17, 'Jacob Lee', 'jacob.lee@example.com', '#Jacob_Lee_Password0', '+40754321853', '111 Elm St, Tokyo, Japan', '1997-06-29', 1, false, 'FULL_TIME', 'DESIGN', 'JUNIOR', 3500, '2023-01-31', 17),
(18, 'Charlotte Brown', 'charlotte.brown@example.com', '#Charlotte_Brown_Password0', '+40789012654', '333 Pine St, Seoul, South Korea', '2000-02-12', 1, true, 'FULL_TIME', 'DESIGN', 'JUNIOR', 4000, '2023-03-17', 18),
(19, 'Ethan Johnson', 'ethan.johnson@example.com', '#Ethan_Johnson_Password0', '+40723145655', '555 Elm St, Beijing, China', '1998-09-25', 1, true, 'PART_TIME', 'DESIGN', 'MID', 5500, '2022-02-28', 19),
(20, 'Sophia Davis', 'sophia.davis@example.com', '#Sophia_Davis_Password0', '+40787654356', '777 Elm St, Cape Town, South Africa', '2001-07-09', 1, true, 'PART_TIME', 'DESIGN', 'SENIOR', 6500, '2021-08-09', 20),
(21, 'Jacob Thompson', 'jacob.thompson@example.com', '#Jacob_Thompson_Password0', '+40754321857', '999 Pine St, Buenos Aires, Argentina', '1996-02-22', 1, false, 'PART_TIME', 'DATA_ANALYST', 'JUNIOR', 5000, '2023-06-12', 21),
(22, 'Liam Wilson', 'liam.wilson@example.com', '#Liam_Wilson_Password0', '+40789012658', '111 Elm St, Rio de Janeiro, Brazil', '1995-10-07', 1, true, 'PART_TIME', 'DATA_ANALYST', 'JUNIOR', 5500, '2022-10-30', 22),
(23, 'Ava Brown', 'ava.brown@example.com', '#Ava_Brown_Password0', '+40723145659', '333 Elm St, Mexico City, Mexico', '1998-07-21', 1, true, 'PART_TIME', 'DATA_ANALYST', 'MID', 7000, '2020-12-15', 23),
(24, 'Scarlett Wilson', 'scarlett.wilson@example.com', '#Scarlett_Wilson_Password0', '+40787654360', '555 Elm St, Vancouver, Canada', '2001-05-05', 1, true, 'PART_TIME', 'DATA_ANALYST', 'SENIOR', 8000, '2020-04-10', 24),
(25, 'Noah Martinez', 'noah.martinez@example.com', '#Noah_Martinez_Password0', '+40754321861', '777 Pine St, Paris, France', '1999-12-17', 1, false, 'PART_TIME', 'MACHINE_LEARNING', 'JUNIOR', 4750, '2023-03-27', 25),
(26, 'Mia Anderson', 'mia.anderson@example.com', '#Mia_Anderson_Password0', '+40789012662', '999 Oak St, London, UK', '2002-09-30', 1, true, 'PART_TIME', 'MACHINE_LEARNING', 'JUNIOR', 5250, '2022-11-18', 26),
(27, 'Grace Lee', 'grace.lee@example.com', '#Grace_Lee_Password0', '+40723145663', '111 Elm St, Berlin, Germany', '1996-05-14', 1, true, 'PART_TIME', 'MACHINE_LEARNING', 'MID', 7000, '2022-06-29', 27),
(28, 'Benjamin Taylor', 'benjamin.taylor@example.com', '#Benjamin_Taylor_Password0', '+40787654364', '333 Elm St, Moscow, Russia', '2002-12-27', 1, true, 'PART_TIME', 'MACHINE_LEARNING', 'SENIOR', 8750, '2020-07-16', 28),
(29, 'Elijah Roberts', 'elijah.roberts@example.com', '#Elijah_Roberts_Password0', '+40754321865', '555 Pine St, Sydney, Australia', '2003-01-27', 1, false, 'PART_TIME', 'BUSINESS_ANALYST', 'JUNIOR', 4500, '2023-05-19', 29),
(30, 'Amelia Walker', 'amelia.walker@example.com', '#Amelia_Walker_Password0', '+40789012666', '777 Oak St, Rome, Italy', '1992-08-12', 1, true, 'PART_TIME', 'BUSINESS_ANALYST', 'JUNIOR', 4750, '2023-03-19', 30),
(31, 'Daniel Green', 'daniel.green@example.com', '#Daniel_Green_Password0', '+40723145667', '999 Elm St, Moscow, Russia', '1996-03-27', 1, true, 'PART_TIME', 'BUSINESS_ANALYST', 'MID', 5500, '2022-08-24', 31),
(32, 'Liam Hall', 'liam.hall@example.com', '#Liam_Hall_Password0', '+40787654368', '111 Oak St, Athens, Greece', '1998-11-09', 1, true, 'PART_TIME', 'BUSINESS_ANALYST', 'SENIOR', 6500, '2021-01-23', 32),
(33, 'Sophia Young', 'sophia.young@example.com', '#Sophia_Young_Password0', '+40754321869', '333 Pine St, Madrid, Spain', '1995-06-23', 1, false, 'PART_TIME', 'SCRUM_MASTER', 'JUNIOR', 6000, '2021-08-17', 33),
(34, 'Noah Clark', 'noah.clark@example.com', '#Noah_Clark_Password0', '+40789012670', '555 Elm St, Tokyo, Japan', '1997-02-05', 1, true, 'PART_TIME', 'SCRUM_MASTER', 'JUNIOR', 6250, '2021-03-30', 34),
(35, 'Olivia Hill', 'olivia.hill@example.com', '#Olivia_Hill_Password0', '+40723145671', '777 Oak St, Seoul, South Korea', '2001-09-20', 1, true, 'PART_TIME', 'SCRUM_MASTER', 'MID', 7750, '2020-11-08', 35),
(36, 'Michaela Allen', 'michaela.allen@example.com', '#Michaela_Allen_Password0', '+40787654372', '999 Pine St, Beijing, China', '1998-05-06', 1, true, 'PART_TIME', 'SCRUM_MASTER', 'SENIOR', 8750, '2019-12-03', 36);

INSERT INTO user_authority(user_id, authority_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4),
(2, 1), (2, 2), (2, 3), (2, 4),
(3, 1), (3, 2), (3, 3), (3, 4),
(4, 1), (4, 2), (4, 3), (4, 4),
(5, 1), (5, 2), (6, 1), (6, 2),
(7, 1), (7, 2), (8, 1), (8, 2),
(9, 1), (9, 2), (10, 1), (10, 2),
(11, 1), (11, 2), (12, 1), (12, 2),
(13, 1), (13, 2), (14, 1), (14, 2),
(15, 1), (15, 2), (16, 1), (16, 2),
(17, 1), (17, 2), (18, 1), (18, 2),
(19, 1), (19, 2), (20, 1), (20, 2),
(21, 1), (21, 2), (22, 1), (22, 2),
(23, 1), (23, 2), (24, 1), (24, 2),
(25, 1), (25, 2), (26, 1), (26, 2),
(27, 1), (27, 2), (28, 1), (28, 2),
(29, 1), (29, 2), (30, 1), (30, 2),
(31, 1), (31, 2), (32, 1), (32, 2),
(33, 1), (33, 2), (34, 1), (34, 2),
(35, 1), (35, 2), (36, 1), (36, 2),
(37, 1), (37, 2), (38, 1), (38, 2),
(39, 1), (39, 2), (40, 1), (40, 2),
(41, 1), (41, 2), (42, 1), (42, 2),
(43, 1), (43, 2), (44, 1), (44, 2),
(45, 1), (45, 2), (46, 1), (46, 2),
(47, 1), (47, 2), (48, 1), (48, 2),
(49, 1), (49, 2), (50, 1), (50, 2),
(51, 1), (51, 2), (52, 1), (52, 2),
(53, 1), (53, 2), (54, 1), (54, 2),
(55, 1), (55, 2), (56, 1), (56, 2),
(57, 1), (57, 2), (58, 1), (58, 2),
(59, 1), (59, 2), (60, 1), (60, 2),
(61, 1), (61, 2), (62, 1), (62, 2),
(63, 1), (63, 2), (64, 1), (64, 2),
(65, 1), (65, 2), (66, 1), (66, 2),
(67, 1), (67, 2), (68, 1), (68, 2),
(69, 1), (69, 2), (70, 1), (70, 2),
(71, 1), (71, 2), (72, 1), (72, 2);

INSERT INTO trainer_authority(trainer_id, authority_id) VALUES
(1, 1), (1, 2), (1, 3), (1, 4),
(2, 1), (2, 2), (2, 3), (2, 4),
(3, 1), (3, 2), (3, 3), (3, 4),
(4, 1), (4, 2), (4, 3), (4, 4),
(5, 1), (5, 2), (6, 1), (6, 2),
(7, 1), (7, 2), (8, 1), (8, 2),
(9, 1), (9, 2), (10, 1), (10, 2),
(11, 1), (11, 2), (12, 1), (12, 2),
(13, 1), (13, 2), (14, 1), (14, 2),
(15, 1), (15, 2), (16, 1), (16, 2),
(17, 1), (17, 2), (18, 1), (18, 2),
(19, 1), (19, 2), (20, 1), (20, 2),
(21, 1), (21, 2), (22, 1), (22, 2),
(23, 1), (23, 2), (24, 1), (24, 2),
(25, 1), (25, 2), (26, 1), (26, 2),
(27, 1), (27, 2), (28, 1), (28, 2),
(29, 1), (29, 2), (30, 1), (30, 2),
(31, 1), (31, 2), (32, 1), (32, 2),
(33, 1), (33, 2), (34, 1), (34, 2),
(35, 1), (35, 2), (36, 1), (36, 2);

INSERT INTO trainer_experience(trainer_id, experience_id) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4),
(3, 5), (3, 6),
(4, 7), (4, 8),
(5, 9), (5, 10),
(6, 11), (6, 12),
(7, 13), (7, 14),
(8, 15), (8, 16),
(9, 1), (9, 2),
(10, 3), (10, 4),
(11, 5), (11, 6),
(12, 7), (12, 8),
(13, 9), (13, 10),
(14, 11), (14, 12),
(15, 13), (15, 14),
(16, 15), (16, 16),
(17, 1), (17, 2),
(18, 3), (18, 4),
(19, 5), (19, 6),
(20, 7), (20, 8),
(21, 9), (21, 10),
(22, 11), (22, 12),
(23, 13), (23, 14),
(24, 15), (24, 16),
(25, 1), (25, 2),
(26, 3), (26, 4),
(27, 5), (27, 6),
(28, 7), (28, 8),
(29, 9), (29, 10),
(30, 11), (30, 12),
(31, 13), (31, 14),
(32, 15), (32, 16),
(33, 1), (33, 2),
(34, 3), (34, 4),
(35, 5), (35, 6),
(36, 7), (36, 8);

INSERT INTO trainer_study(trainer_id, study_id) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4),
(3, 5), (3, 6),
(4, 7), (4, 8),
(5, 9), (5, 10),
(6, 11), (6, 12),
(7, 1), (7, 2),
(8, 3), (8, 4),
(9, 5), (9, 6),
(10, 7), (10, 8),
(11, 9), (11, 10),
(12, 11), (12, 12),
(13, 1), (13, 2),
(14, 3), (14, 4),
(15, 5), (15, 6),
(16, 7), (16, 8),
(17, 9), (17, 10),
(18, 11), (18, 12),
(19, 1), (19, 2),
(20, 3), (20, 4),
(21, 5), (21, 6),
(22, 7), (22, 8),
(23, 9), (23, 10),
(24, 11), (24, 12),
(25, 1), (25, 2),
(26, 3), (26, 4),
(27, 5), (27, 6),
(28, 7), (28, 8),
(29, 9), (29, 10),
(30, 11), (30, 12),
(31, 1), (31, 2),
(32, 3), (32, 4),
(33, 5), (33, 6),
(34, 7), (34, 8),
(35, 9), (35, 10),
(36, 11), (36, 12);

INSERT INTO employee_authority(employee_id, authority_id) VALUES
(1, 1), (1, 2), (2, 1), (2, 2),
(3, 1), (3, 2), (4, 1), (4, 2),
(5, 1), (5, 2), (6, 1), (6, 2),
(7, 1), (7, 2), (8, 1), (8, 2),
(9, 1), (9, 2), (10, 1), (10, 2),
(11, 1), (11, 2), (12, 1), (12, 2),
(13, 1), (13, 2), (14, 1), (14, 2),
(15, 1), (15, 2), (16, 1), (16, 2),
(17, 1), (17, 2), (18, 1), (18, 2),
(19, 1), (19, 2), (20, 1), (20, 2),
(21, 1), (21, 2), (22, 1), (22, 2),
(23, 1), (23, 2), (24, 1), (24, 2),
(25, 1), (25, 2), (26, 1), (26, 2),
(27, 1), (27, 2), (28, 1), (28, 2),
(29, 1), (29, 2), (30, 1), (30, 2),
(31, 1), (31, 2), (32, 1), (32, 2),
(33, 1), (33, 2), (34, 1), (34, 2),
(35, 1), (35, 2), (36, 1), (36, 2);

INSERT INTO employee_experience(employee_id, experience_id) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4),
(3, 5), (3, 6),
(4, 7), (4, 8),
(5, 9), (5, 10),
(6, 11), (6, 12),
(7, 13), (7, 14),
(8, 15), (8, 16),
(9, 1), (9, 2),
(10, 3), (10, 4),
(11, 5), (11, 6),
(12, 7), (12, 8),
(13, 9), (13, 10),
(14, 11), (14, 12),
(15, 13), (15, 14),
(16, 15), (16, 16),
(17, 1), (17, 2),
(18, 3), (18, 4),
(19, 5), (19, 6),
(20, 7), (20, 8),
(21, 9), (21, 10),
(22, 11), (22, 12),
(23, 13), (23, 14),
(24, 15), (24, 16),
(25, 1), (25, 2),
(26, 3), (26, 4),
(27, 5), (27, 6),
(28, 7), (28, 8),
(29, 9), (29, 10),
(30, 11), (30, 12),
(31, 13), (31, 14),
(32, 15), (32, 16),
(33, 1), (33, 2),
(34, 3), (34, 4),
(35, 5), (35, 6),
(36, 7), (36, 8);

INSERT INTO employee_study(employee_id, study_id) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4),
(3, 5), (3, 6),
(4, 7), (4, 8),
(5, 9), (5, 10),
(6, 11), (6, 12),
(7, 1), (7, 2),
(8, 3), (8, 4),
(9, 5), (9, 6),
(10, 7), (10, 8),
(11, 9), (11, 10),
(12, 11), (12, 12),
(13, 1), (13, 2),
(14, 3), (14, 4),
(15, 5), (15, 6),
(16, 7), (16, 8),
(17, 9), (17, 10),
(18, 11), (18, 12),
(19, 1), (19, 2),
(20, 3), (20, 4),
(21, 5), (21, 6),
(22, 7), (22, 8),
(23, 9), (23, 10),
(24, 11), (24, 12),
(25, 1), (25, 2),
(26, 3), (26, 4),
(27, 5), (27, 6),
(28, 7), (28, 8),
(29, 9), (29, 10),
(30, 11), (30, 12),
(31, 1), (31, 2),
(32, 3), (32, 4),
(33, 5), (33, 6),
(34, 7), (34, 8),
(35, 9), (35, 10),
(36, 11), (36, 12);