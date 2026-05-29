CREATE TABLE IF NOT EXISTS users
(
    id            serial PRIMARY KEY,
    email         VARCHAR(255) UNIQUE NOT NULL,
    username      VARCHAR(30) UNIQUE  NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    role          VARCHAR(30)         NOT NULL,
    last_name     VARCHAR(40)         NOT NULL,
    first_name    VARCHAR(40)         NOT NULL,
    created_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
