CREATE DATABASE MyProduct;

create table myProducts(
    id   BIGSERIAL PRIMARY KEY,
    id_product int,
    name varchar(25),
    cost numeric(10, 2),
    promotional boolean
);


create table myDiscountCards(
    id   BIGSERIAL PRIMARY KEY,
    name varchar(25),
    number int,
    sale int
);




