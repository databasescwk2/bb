DROP TABLE IF EXISTS Post;
DROP TABLE IF EXISTS Topic;
DROP TABLE IF EXISTS Forum;
DROP TABLE IF EXISTS Person;

CREATE TABLE Person (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(100) NOT NULL,
  username VARCHAR(10) NOT NULL,
  stuId VARCHAR(10) NULL
);

CREATE TABLE Forum (
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE Topic (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	forumId INTEGER NOT NULL,
	title VARCHAR(100) NOT NULL,
	postCount INTEGER NOT NULL,
	FOREIGN KEY (forumId) REFERENCES Forum(id)
);

CREATE TABLE Post (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	topicId INTEGER NOT NULL,
    postNumber INTEGER NOT NULL,
    authorId INTEGER NOT NULL,
	text VARCHAR(1000) NOT NULL,
    postedAt DATETIME NOT NULL,
	likes INTEGER NULL,
	FOREIGN KEY (topicId) REFERENCES Topic(id),
    FOREIGN KEY (authorId) REFERENCES Person(id)
);

CREATE TABLE TopicLikes(
    topicId INTEGER UNIQUE NOT NULL,
	authorId INTEGER UNIQUE NOT NULL,
	FOREIGN KEY (topicId) REFERENCED Topic(id),
	FOREIGN KEY (authorId) REFERENCES Person(id)
);

CREATE TABLE PostLikes();