CREATE TABLE IF NOT EXISTS daily
(
    id              uuid PRIMARY KEY,
    date_feedback   DATE        NOT NULL,
    user_id         SERIAL      NOT NULL,
    people_feedback VARCHAR(40) NOT NULL,
    created_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);