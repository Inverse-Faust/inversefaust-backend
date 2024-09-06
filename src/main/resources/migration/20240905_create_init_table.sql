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

-- UserActivity 테이블 생성
CREATE TABLE IF NOT EXISTS UserActivity (
                              user_id VARCHAR(255) NOT NULL,
                              activity_id VARCHAR(255) NOT NULL,
                              created_at DATETIME NOT NULL,
                              activity_duration INT NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES User(user_id),
                              FOREIGN KEY (activity_id) REFERENCES Activity(activity_id)
);

-- WolfScore 테이블 생성
CREATE TABLE WolfScore (
                           score_id VARCHAR(255) NOT NULL,       -- 점수 아이디
                           user_id VARCHAR(255) NOT NULL,        -- 유저 아이디
                           white_score INT NOT NULL DEFAULT 0,   -- 흰 늑대 점수
                           black_score INT NOT NULL DEFAULT 0,   -- 검은 늑대 점수
                           created_at DATETIME NOT NULL,         -- 생성일

                           PRIMARY KEY (score_id),               -- 기본 키 설정
                           FOREIGN KEY (user_id) REFERENCES User(user_id)  -- 유저 아이디 외래키 설정
);
