
INSERT INTO assessment (id, active, created_date, description, is_published, name, created_by_id, council_id, period_id, idea_id) VALUES
(1, b'1', '2024-06-30 03:19:54', '', NULL, 'hoi dong 1 2023', NULL, 1, 1, 1);


INSERT INTO council (id, active, created_date, text) VALUES
(1, b'1', '2024-06-30 03:07:52', 'hoi dong 1');
INSERT INTO idea (id, active, created_date, text, assessment_id) VALUES
(1, b'1', '2024-06-30 03:21:35', 'de tai so hoa viec cham thi sang kien', 1),
(2, b'1', '2024-06-30 03:23:21', 'sang kien cho bach di thai lan chuyen gioi', 1);

INSERT INTO member (id, active, created_date, text, idea_id) VALUES
(1, b'1', '2024-06-30 03:23:45', 'hoang thanh tung', 1),
(2, b'1', '2024-06-30 03:24:02', 'do viet bach', 1),
(3, b'1', '2024-06-30 03:24:26', 'hoang thanh tung', 2),
(4, b'1', '2024-06-30 03:25:21', 'vuong van ninh', 2);

INSERT INTO period (id, active, created_date, text) VALUES
(1, b'1', '2024-06-30 03:07:27', '2023');

