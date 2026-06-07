CREATE TABLE contracteds_resources
(
    user_id   BIGINT      NOT NULL,
    resources VARCHAR(30) NOT NULL
);

ALTER TABLE contracteds_resources
    ADD CONSTRAINT fk_contracteds_resources_on_user FOREIGN KEY (user_id) REFERENCES users (id);