--liquibase formatted sql
--changeset nokk:002-create-catalog-index

CREATE INDEX idx_catalog_price
    ON catalog(price)
    WHERE available = true;