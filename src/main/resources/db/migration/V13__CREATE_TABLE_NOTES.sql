CREATE TABLE notes
(
    id           UUID         NOT NULL,
    title        VARCHAR(255) NOT NULL,
    version      INTEGER      NOT NULL,
    deleted_at   TIMESTAMP(6) WITHOUT TIME ZONE,
    is_archived  BOOLEAN      NOT NULL,
    is_pinned    BOOLEAN      NOT NULL,
    user_id      BIGINT       NOT NULL,
    subdomain_id UUID         NOT NULL,
    content      TEXT,
    created_at   TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP(6) WITHOUT TIME ZONE,
    CONSTRAINT pk_notes PRIMARY KEY (id)
);

ALTER TABLE notes
    ADD CONSTRAINT uk_notes_user_subdomain_title UNIQUE (user_id, subdomain_id, title);

CREATE INDEX idx_notes_user_subdomain_pinned_title ON notes (user_id, subdomain_id, is_pinned, title);

ALTER TABLE notes
    ADD CONSTRAINT FK_NOTES_ON_SUBDOMAIN FOREIGN KEY (subdomain_id) REFERENCES subdomains (id);

ALTER TABLE notes
    ADD CONSTRAINT FK_NOTES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);