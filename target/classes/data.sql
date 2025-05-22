INSERT INTO author (first_name, last_name, birth_date, death_date, nationality) VALUES
('George', 'Orwell', '1903-06-25', '1950-01-21', 'British'),
('Jane', 'Austen', '1775-12-16', '1817-07-18', 'British'),
('Mark', 'Twain', '1835-11-30', '1910-04-21', 'American'),
('J.K.', 'Rowling', '1965-07-31', NULL, 'British'),
('Leo', 'Tolstoy', '1828-09-09', '1910-11-20', 'Russian');
INSERT INTO book (title, year) VALUES
('1984', 1948),
('Pride and Prejudice', 1813),
('Adventures of Huckleberry Finn', 1884),
('Harry Potter and the Philosopher s Stone', 1997),
('War and Peace', 1869);
-- 1984 di George Orwell
INSERT INTO book_authors (book_id, author_id) VALUES (1, 1);

-- Pride and Prejudice di Jane Austen
INSERT INTO book_authors (book_id, author_id) VALUES (2, 2);

-- Huck Finn di Mark Twain
INSERT INTO book_authors (book_id, author_id) VALUES (3, 3);

-- Harry Potter di J.K. Rowling
INSERT INTO book_authors (book_id, author_id) VALUES (4, 4);

-- War and Peace di Leo Tolstoy
INSERT INTO book_authors (book_id, author_id) VALUES (5, 5);
