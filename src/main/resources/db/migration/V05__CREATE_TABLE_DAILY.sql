CREATE TABLE IF NOT EXISTS daily
(
    id           uuid PRIMARY KEY,
    date_summary DATE   NOT NULL,
    user_id      SERIAL NOT NULL,
    summary      TEXT   NOT NULL,
    created_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users (id)
);