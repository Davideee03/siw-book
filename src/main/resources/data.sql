ALTER TABLE book ALTER COLUMN plot TYPE TEXT;

INSERT INTO author (first_name, last_name, birth_date, death_date, nationality) VALUES
('George', 'Orwell', '1903-06-25', '1950-01-21', 'British'),
('Jane', 'Austen', '1775-12-16', '1817-07-18', 'British'),
('Mark', 'Twain', '1835-11-30', '1910-04-21', 'American'),
('J.K.', 'Rowling', '1965-07-31', NULL, 'British'),
('Leo', 'Tolstoy', '1828-09-09', '1910-11-20', 'Russian');


INSERT INTO book (title, year, plot) VALUES
('1984', 1948, 'In a dystopian future, Winston Smith lives under the totalitarian regime of the Party, which controls every aspect of life. His inner rebellion leads him to secretly write a diary and start a forbidden relationship with Julia. Together, they try to escape constant surveillance but are arrested and subjected to psychological torture until they fully submit to the Party.'),
('Pride and Prejudice', 1813, 'Elizabeth Bennet, a young woman in rural England, faces the challenges of social conventions and family expectations. Her initial dislike of the wealthy and proud Mr. Darcy turns to love as she discovers his true nature and noble deeds. The story explores themes of pride, prejudice, and personal growth.'),
('Adventures of Huckleberry Finn', 1884, 'Huck Finn, an orphaned boy, escapes his abusive father and teams up with Jim, a runaway slave. Together, they journey down the Mississippi River, facing adventures and dangers. The trip becomes a coming-of-age experience for Huck, who confronts society''s injustices and his own moral beliefs.'),
('Harry Potter and the Philosopher''s Stone', 1997, 'Harry Potter, an orphaned boy, discovers he is a wizard and is admitted to Hogwarts School of Witchcraft and Wizardry. There, he makes friends and faces challenges, including uncovering a mysterious magical object. His curiosity leads him to confront dark forces and learn secrets about his past and destiny.'),
('War and Peace', 1869, 'Set during the Napoleonic Wars, the novel follows the intertwined lives of five Russian aristocratic families. The stories of Pierre Bezukhov, Andrei Bolkonsky, and Natasha Rostov explore themes of love, war, faith, and fate. Tolstoy weaves real historical events with the characters'' personal experiences, offering reflections on history and the human condition.'),
('Animal Farm', 1945, 'A satirical allegory about a group of farm animals who overthrow their human farmer, only to face a new tyranny under the pigs. The story explores themes of power, corruption, and propaganda, reflecting events leading up to the Russian Revolution and its aftermath.'),
('Emma', 1815, 'Emma Woodhouse, a young, clever, and wealthy woman, enjoys matchmaking in her small English community. Her meddling causes romantic misunderstandings and personal growth as she learns about love, friendship, and humility.');

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

-- Animal Farm di George Orwell (autore id 1)
INSERT INTO book_authors (book_id, author_id) VALUES (6, 1);

-- Emma di Jane Austen (autore id 2)
INSERT INTO book_authors (book_id, author_id) VALUES (7, 2);
