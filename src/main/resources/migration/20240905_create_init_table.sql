-- User 테이블 생성
CREATE TABLE IF NOT EXISTS User (
                      user_id VARCHAR(255) NOT NULL PRIMARY KEY,
                      password VARCHAR(255) NOT NULL,
                      name VARCHAR(255) NOT NULL,
                      white_score INT NOT NULL DEFAULT 0,
                      black_score INT NOT NULL DEFAULT 0
);

-- Advice 테이블 생성
DROP TABLE IF EXISTS Advice;
CREATE TABLE IF NOT EXISTS Advice (
                        advice_id VARCHAR(255) NOT NULL PRIMARY KEY,
                        user_id VARCHAR(255) NOT NULL,
                        created_at DATETIME NOT NULL,
                        contents VARCHAR(1000) NOT NULL,
                        purpose varchar(255) NULL,
                        FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- Diary 테이블 생성
DROP TABLE IF EXISTS Diary;
CREATE TABLE IF NOT EXISTS Diary (
                       diary_id VARCHAR(255) NOT NULL PRIMARY KEY,
                       user_id VARCHAR(255) NOT NULL,
                       contents VARCHAR(2000) NOT NULL,
                       created_at DATETIME NOT NULL,
                       FOREIGN KEY (user_id) REFERENCES User(user_id)
);

-- Activity 테이블 생성
CREATE TABLE IF NOT EXISTS Activity (
                          activity_id VARCHAR(255) NOT NULL PRIMARY KEY,
                          activity_name VARCHAR(1000) NOT NULL,
                          purpose varchar(255) NOT NULL
);

-- UserActivity 테이블 생성;
CREATE TABLE IF NOT EXISTS UserActivity (
                              user_id VARCHAR(255) NOT NULL,
                              activity_id VARCHAR(255) NOT NULL,
                              created_at DATETIME NOT NULL,
                              activity_duration INT NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES User(user_id),
                              FOREIGN KEY (activity_id) REFERENCES Activity(activity_id)
);
