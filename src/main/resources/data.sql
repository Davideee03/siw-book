-- Ensure the 'plot' column allows long text
ALTER TABLE book ALTER COLUMN plot TYPE TEXT;

-- Authors
INSERT INTO author (first_name, last_name, birth_date, death_date, nationality) VALUES
('George', 'Orwell', '1903-06-25', '1950-01-21', 'British'),
('Jane', 'Austen', '1775-12-16', '1817-07-18', 'British'),
('Mark', 'Twain', '1835-11-30', '1910-04-21', 'American'),
('J.K.', 'Rowling', '1965-07-31', NULL, 'British'),
('Leo', 'Tolstoy', '1828-09-09', '1910-11-20', 'Russian'),
('F. Scott', 'Fitzgerald', '1896-09-24', '1940-12-21', 'American'),
('Harper', 'Lee', '1926-04-28', '2016-02-19', 'American'),
('Mary', 'Shelley', '1797-08-30', '1851-02-01', 'British'),
('Dan', 'Brown', '1964-06-22', NULL, 'American'),
('E. L.', 'James', '1963-03-07', NULL, 'British'),
('Stephen', 'King', '1947-09-21', NULL, 'American'),
('Peter', 'Straub', '1943-03-02', '2022-09-04', 'American');

-- Books
INSERT INTO book (title, year, plot) VALUES
('1984', 1948, 'In a dystopian future, Winston Smith lives under the totalitarian regime of the Party, which controls every aspect of life. His inner rebellion leads him to secretly write a diary and start a forbidden relationship with Julia. Together, they try to escape constant surveillance but are arrested and subjected to psychological torture until they fully submit to the Party.'),
('Pride and Prejudice', 1813, 'Elizabeth Bennet, a young woman in rural England, faces the challenges of social conventions and family expectations. Her initial dislike of the wealthy and proud Mr. Darcy turns to love as she discovers his true nature and noble deeds. The story explores themes of pride, prejudice, and personal growth.'),
('Adventures of Huckleberry Finn', 1884, 'Huck Finn, an orphaned boy, escapes his abusive father and teams up with Jim, a runaway slave. Together, they journey down the Mississippi River, facing adventures and dangers. The trip becomes a coming-of-age experience for Huck, who confronts society''s injustices and his own moral beliefs.'),
('Harry Potter and the Philosopher''s Stone', 1997, 'Harry Potter, an orphaned boy, discovers he is a wizard and is admitted to Hogwarts School of Witchcraft and Wizardry. There, he makes friends and faces challenges, including uncovering a mysterious magical object. His curiosity leads him to confront dark forces and learn secrets about his past and destiny.'),
('War and Peace', 1869, 'Set during the Napoleonic Wars, the novel follows the intertwined lives of five Russian aristocratic families. The stories of Pierre Bezukhov, Andrei Bolkonsky, and Natasha Rostov explore themes of love, war, faith, and fate. Tolstoy weaves real historical events with the characters'' personal experiences, offering reflections on history and the human condition.'),
('Animal Farm', 1945, 'A satirical allegory about a group of farm animals who overthrow their human farmer, only to face a new tyranny under the pigs. The story explores themes of power, corruption, and propaganda, reflecting events leading up to the Russian Revolution and its aftermath.'),
('Emma', 1815, 'Emma Woodhouse, a young, clever, and wealthy woman, enjoys matchmaking in her small English community. Her meddling causes romantic misunderstandings and personal growth as she learns about love, friendship, and humility.'),
('The Great Gatsby', 1925, 'Jay Gatsby, a mysterious millionaire, hosts extravagant parties in hopes of reuniting with Daisy Buchanan. Set in the Roaring Twenties, the novel explores themes of love, wealth, and the American Dream.'),
('To Kill a Mockingbird', 1960, 'Scout Finch recounts her childhood in the racially divided American South. Her father, Atticus Finch, defends a black man falsely accused of raping a white woman, revealing deep-rooted prejudices.'),
('Frankenstein', 1818, 'Victor Frankenstein creates a sentient creature in a scientific experiment. Horrified by his creation, he abandons it, leading to tragic consequences as the creature seeks revenge and understanding.'),
('The Da Vinci Code', 2003, 'Robert Langdon investigates a murder in the Louvre and uncovers a religious conspiracy involving the Holy Grail.'),
('Fifty Shades of Grey', 2011, 'Anastasia Steele, a college student, enters a relationship with the wealthy and enigmatic Christian Grey, exploring themes of dominance and submission.'),
('The Tommyknockers', 1987, 'A strange object is unearthed in a small town, leading to bizarre occurrences and mental deterioration among residents.'),
('The Talisman', 1984, 'A young boy named Jack Sawyer embarks on a quest across parallel worlds to find a mystical talisman that can save his dying mother.');

-- Book-Author associations
INSERT INTO book_authors (book_id, author_id) VALUES
(1, 1), -- 1984 - Orwell
(2, 2), -- Pride and Prejudice - Austen
(3, 3), -- Huck Finn - Twain
(4, 4), -- Harry Potter - Rowling
(5, 5), -- War and Peace - Tolstoy
(6, 1), -- Animal Farm - Orwell
(7, 2), -- Emma - Austen
(8, 6),  -- The Great Gatsby - Fitzgerald
(9, 7),  -- To Kill a Mockingbird - Harper Lee
(10, 8), -- Frankenstein - Mary Shelley
(11, 9), -- The Da Vinci Code - Dan Brown
(12, 10), -- Fifty Shades of Grey - E. L. James
(13, 11), -- The Tommyknockers - Stephen King
(14, 11), -- The Talisman - Stephen King
(14, 12); -- The Talisman - Peter Straub (co-authored)

-- Reviews

-- 1984 (book_id = 1)
INSERT INTO review (title, mark, description, book_id) VALUES
('Disturbingly Real', 5, 'A powerful depiction of authoritarianism. Orwell''s vision still resonates.', 1),
('Dark and Insightful', 4, 'A chilling and thought-provoking read about control and resistance.', 1);

-- Harry Potter and the Philosopher's Stone (book_id = 4)
INSERT INTO review (title, mark, description, book_id) VALUES
('Magical Start', 5, 'The beginning of an epic adventure. Full of wonder and imagination.', 4),
('Loved the World', 4, 'Brilliant world-building. A bit childish, but still enjoyable.', 4);

-- War and Peace (book_id = 5)
INSERT INTO review (title, mark, description, book_id) VALUES
('Epic and Philosophical', 5, 'A literary masterpiece. Demands patience but richly rewarding.', 5),
('Very Long', 3, 'Some parts were captivating, others slow and tedious.', 5);

-- Animal Farm (book_id = 6)
INSERT INTO review (title, mark, description, book_id) VALUES
('Sharp Political Satire', 5, 'Short and brilliant. A critique of power and corruption.', 6),
('Thought-Provoking', 4, 'Simple language, deep meaning. Still relevant today.', 6);

-- The Great Gatsby (book_id = 8)
INSERT INTO review (title, mark, description, book_id) VALUES
('Timeless Classic', 5, 'A haunting portrayal of the American Dream gone wrong.', 8),
('Symbolic and Sad', 4, 'Fitzgerald captures the emptiness behind glamour.', 8);

-- To Kill a Mockingbird (book_id = 9)
INSERT INTO review (title, mark, description, book_id) VALUES
('Powerful and Poignant', 5, 'Still one of the most important books on justice and morality.', 9),
('A Touching Story', 4, 'Moving and profound with memorable characters.', 9);

-- Frankenstein (book_id = 10)
INSERT INTO review (title, mark, description, book_id) VALUES
('Gothic and Deep', 5, 'An early science fiction that explores ethics and identity.', 10),
('Tragic and Thoughtful', 4, 'Much more philosophical than expected. A gripping read.', 10);

-- The Da Vinci Code (book_id = 11)
INSERT INTO review (title, mark, description, book_id) VALUES
('Overrated', 2, 'Interesting premise but poorly written and full of clichés.', 11),
('Fast but Shallow', 3, 'Entertaining at first, but lacks depth and nuance.', 11);

-- Fifty Shades of Grey (book_id = 12)
INSERT INTO review (title, mark, description, book_id) VALUES
('Not My Taste', 2, 'Repetitive and awkward prose. The characters felt flat.', 12),
('Problematic Themes', 1, 'Romanticizes unhealthy relationships. Not recommended.', 12);

-- The Tommyknockers (book_id = 13)
INSERT INTO review (title, mark, description, book_id) VALUES
('Confusing', 2, 'Starts strong but loses focus. Overly long.', 13),
('Not King''s Best', 3, 'Disjointed and slow in parts. Interesting concept though.', 13);

-- The Talisman (book_id = 14)
INSERT INTO review (title, mark, description, book_id) VALUES
('Too Long', 3, 'Sprawling and uneven pacing. Some great moments, but dragged.', 14),
('Strange Mix', 2, 'Creative world-building but hard to follow and over-complicated.', 14);


