alter table sprints
    add column created_at TIMESTAMP(6) WITHOUT TIME ZONE NOT NULL,
    add updated_at        TIMESTAMP(6) WITHOUT TIME ZONE;