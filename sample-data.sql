-- Sample data for testing the Student Score Management System
-- You can run this after the application starts

-- Insert sample students
INSERT INTO students (name, math_score, english_score, science_score, history_score, geography_score, created_at)
VALUES 
    ('Alice Johnson', 95.0, 88.0, 92.0, 85.0, 90.0, CURRENT_TIMESTAMP),
    ('Bob Smith', 78.0, 82.0, 75.0, 80.0, 85.0, CURRENT_TIMESTAMP),
    ('Charlie Brown', 88.0, 90.0, 85.0, 92.0, 87.0, CURRENT_TIMESTAMP),
    ('Diana Prince', 92.0, 95.0, 90.0, 88.0, 94.0, CURRENT_TIMESTAMP),
    ('Eve Wilson', 70.0, 75.0, 72.0, 78.0, 76.0, CURRENT_TIMESTAMP);

-- Check the data
SELECT * FROM students;

