CREATE TABLE libraries
(
    id           UUID                           NOT NULL,
    created_at   TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP(6) WITHOUT TIME ZONE,
    name         VARCHAR(35)                    NOT NULL,
    path         VARCHAR(255)                   NOT NULL,
    user_id      BIGINT                         NOT NULL,
    subdomain_id UUID                           NOT NULL,
    CONSTRAINT pk_libraries PRIMARY KEY (id)
);

ALTER TABLE libraries ADD CONSTRAINT uc_libraries_subdomain UNIQUE (subdomain_id);

ALTER TABLE libraries ADD CONSTRAINT uc_libraries_user UNIQUE (user_id);

ALTER TABLE libraries ADD CONSTRAINT uc_library_name_path_user_subdomain UNIQUE (name, path);

CREATE INDEX idx_library_name ON libraries (name);

ALTER TABLE libraries ADD CONSTRAINT FK_LIBRARIES_ON_SUBDOMAIN FOREIGN KEY (subdomain_id) REFERENCES subdomains (id);

ALTER TABLE libraries ADD CONSTRAINT FK_LIBRARIES_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);