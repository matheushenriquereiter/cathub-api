CREATE TABLE IF NOT EXISTS users
(
    id       INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS posts
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255) NOT NULL,
    user_id     INT          NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
)