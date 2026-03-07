CREATE TABLE user_roles
(
    user_id UUID NOT NULL,
    roles   VARCHAR(255)
);

CREATE TABLE users
(
    id         UUID                        NOT NULL,
    first_name VARCHAR(255)                NOT NULL,
    full_name  VARCHAR(255)                NOT NULL,
    username   VARCHAR(255)                NOT NULL,
    email      VARCHAR(255)                NOT NULL,
    password   VARCHAR(255)                NOT NULL,
    date_birth date                        NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT uc_users_email UNIQUE (email);

ALTER TABLE users ADD CONSTRAINT uc_users_username UNIQUE (username);

ALTER TABLE user_roles ADD CONSTRAINT fk_user_roles_on_user FOREIGN KEY (user_id) REFERENCES users (id);