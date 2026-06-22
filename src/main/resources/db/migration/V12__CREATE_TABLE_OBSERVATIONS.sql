CREATE TABLE observations
(
    id               UUID          NOT NULL,
    demand_id        UUID          NOT NULL,
    text_observation VARCHAR(5000) NOT NULL,
    created_at       TIMESTAMP WITHOUT TIME ZONE,
    updated_at       TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_observations PRIMARY KEY (id)
);

ALTER TABLE observations
    ADD CONSTRAINT FK_OBSERVATIONS_ON_DEMAND FOREIGN KEY (demand_id) REFERENCES demands (id);