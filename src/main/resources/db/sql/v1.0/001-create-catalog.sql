--liquibase formatted sql
--changeset nokk:001-create-catalog

CREATE TABLE catalog
(
    id          UUID                    PRIMARY KEY,
    name        VARCHAR(255)    UNIQUE  NOT NULL,
    price       DECIMAL(17,2)           NOT NULL,
    available   BOOLEAN                 NOT NULL,
    created_at  TIMESTAMPTZ             NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMPTZ             NOT NULL DEFAULT CURRENT_TIMESTAMP
);