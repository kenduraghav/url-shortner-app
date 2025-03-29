INSERT INTO users (email, password, name, role, created_at)
VALUES 
('john.doe@example.com', 'password123', 'John Doe', 'USER', CURRENT_TIMESTAMP),
('jane.smith@example.com', 'password456', 'Jane Smith', 'USER', CURRENT_TIMESTAMP),
('admin@example.com', 'admin123', 'Admin User', 'ADMIN', CURRENT_TIMESTAMP),
('alice.williams@example.com', 'password789', 'Alice Williams', 'USER', CURRENT_TIMESTAMP),
('bob.jones@example.com', 'password101', 'Bob Jones', 'USER', CURRENT_TIMESTAMP);



INSERT INTO short_urls (short_key, original_url, is_private, expires_at, created_by, click_count, created_at)
VALUES 
('abc123', 'https://www.example.com/1', FALSE, '2025-12-31 23:59:59', 1, 10, CURRENT_TIMESTAMP),
('xyz456', 'https://www.example.com/2', FALSE, '2025-11-30 23:59:59', 2, 25, CURRENT_TIMESTAMP),
('lmn789', 'https://www.example.com/3', TRUE, '2025-10-15 23:59:59', 3, 5, CURRENT_TIMESTAMP),
('def123', 'https://www.example.com/4', FALSE, '2025-09-01 23:59:59', 4, 0, CURRENT_TIMESTAMP),
('ghi456', 'https://www.example.com/5', TRUE, '2025-12-15 23:59:59', 5, 12, CURRENT_TIMESTAMP);
