CREATE TABLE IF NOT EXISTS feedbacks
(
    id              uuid PRIMARY KEY,
    date_feedback   DATE                                NOT NULL,
    month           VARCHAR(20)                         NOT NULL,
    user_id         SERIAL                              NOT NULL,
    people_feedback VARCHAR(30)                         NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);