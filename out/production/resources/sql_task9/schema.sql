CREATE DATABASE artezian_well;
--DROP DATABASE artezian_well;
DROP TABLE IF EXISTS firm CASCADE;
DROP TABLE IF EXISTS product CASCADE;
DROP TABLE IF EXISTS company CASCADE;
DROP TABLE IF EXISTS purchases CASCADE;

CREATE TABLE firm
(
    id                  BIGSERIAL PRIMARY KEY,
    pump_brand          VARCHAR(50) NOT NULL UNIQUE,
    country             VARCHAR(50) NOT NULL
);

CREATE TABLE product
(
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(50)  NOT NULL UNIQUE,
    price               INT NOT NULL,
    engine_power        INT NOT NULL,
    amperage            INT NOT NULL,
    efficiency_pump     INT NOT NULL,
    producer_id         BIGINT REFERENCES firm (id)
);


CREATE TABLE company
(
    id                  BIGSERIAL PRIMARY KEY,
    name                VARCHAR(32) NOT NULL,
    city                VARCHAR(32) NOT NULL,
    adress              VARCHAR(32) NOT NULL,
    tel_code            INT         NOT NULL,
    tel_number          INT         NOT NULL
);



CREATE TABLE purchases
(
    id         BIGSERIAL PRIMARY KEY,
    company_id BIGINT REFERENCES company (id),
    product_id BIGINT REFERENCES product (id),
    count      INT NOT NULL
);



