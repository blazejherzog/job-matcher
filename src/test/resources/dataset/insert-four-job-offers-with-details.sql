-- Insert images (Company logos)
INSERT INTO IMAGES(id, filename, mime_type, add_date) VALUES (1, 'company_logo1.jpg', 'image/jpeg', '2024-01-01 10:00:00');
INSERT INTO IMAGES(id, filename, mime_type, add_date) VALUES (2, 'company_logo2.jpg', 'image/jpeg', '2024-01-01 11:00:00');
INSERT INTO IMAGES(id, filename, mime_type, add_date) VALUES (3, 'company_logo3.jpg', 'image/jpeg', '2024-01-01 12:00:00');
INSERT INTO IMAGES(id, filename, mime_type, add_date) VALUES (4, 'company_logo4.jpg', 'image/jpeg', '2024-01-01 13:00:00');

-- Insert job branches
INSERT INTO JOB_BRANCHES(id, name) VALUES (1, 'Engineering');
INSERT INTO JOB_BRANCHES(id, name) VALUES (2, 'Marketing');
INSERT INTO JOB_BRANCHES(id, name) VALUES (3, 'Finance');
INSERT INTO JOB_BRANCHES(id, name) VALUES (4, 'Human Resources');

-- Insert job locations
INSERT INTO JOB_LOCATIONS(id, name) VALUES (1, 'New York');
INSERT INTO JOB_LOCATIONS(id, name) VALUES (2, 'San Francisco');
INSERT INTO JOB_LOCATIONS(id, name) VALUES (3, 'London');
INSERT INTO JOB_LOCATIONS(id, name) VALUES (4, 'Berlin');

-- Insert job offers
INSERT INTO JOB_OFFERS(id, title, description, duration, image_id, job_branch_id, add_date)
VALUES (1, 'Software Engineer', 'Develop software solutions.', 30, 1, 1, '2024-09-08 09:30:00'); -- 30 days duration
INSERT INTO JOB_OFFERS(id, title, description, duration, image_id, job_branch_id, add_date)
VALUES (2, 'Marketing Specialist', 'Create marketing campaigns.', 45, 2, 2, '2024-09-07 10:30:00'); -- 45 days duration
INSERT INTO JOB_OFFERS(id, title, description, duration, image_id, job_branch_id, add_date)
VALUES (3, 'Financial Analyst', 'Analyze financial data.', 60, 3, 3, '2024-09-06 11:00:00'); -- 60 days duration
INSERT INTO JOB_OFFERS(id, title, description, duration, image_id, job_branch_id, add_date)
VALUES (4, 'HR Manager', 'Manage recruitment and employee relations.', 15, 4, 4, '2024-09-05 14:00:00'); -- 15 days duration

-- Link job offers to locations (Many-to-Many relationship)
INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (1, 1); -- Software Engineer -> New York
INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (1, 2); -- Software Engineer -> San Francisco
INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (2, 3); -- Marketing Specialist -> London
INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (3, 1); -- Financial Analyst -> New York
INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (4, 4); -- HR Manager -> Berlin
INSERT INTO JOB_OFFERS_JOB_LOCATIONS(job_offer_id, job_location_id) VALUES (4, 3); -- HR Manager -> London