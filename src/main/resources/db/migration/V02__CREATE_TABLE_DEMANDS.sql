CREATE TABLE IF NOT EXISTS demands
(
    id                     uuid PRIMARY KEY,
    title                  VARCHAR(255) NOT NULL,
    description            VARCHAR(500) NOT NULL,
    user_id                SERIAL       NOT NULL,
    deadline               DATE,
    status                 VARCHAR(20)  NOT NULL,
    priority               VARCHAR(20)  NOT NULL,
    created_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    observations_to_review TEXT,
    FOREIGN KEY (user_id) REFERENCES users (id)
);
