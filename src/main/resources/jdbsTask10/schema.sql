Drop DATABASE  product;
------------------------------------------------------------
create DATABASE product;
------------------------------------------------------------
create schema "product";
------------------------------------------------------------
drop table product;
------------------------------------------------------------
drop table discountCard;
------------------------------------------------------------
create table product
(
    id          SERIAL PRIMARY KEY,
    name        varchar(25),
    cost        double precision,
    promotional boolean
);
------------------------------------------------------------
create table discountCard
(
    id   SERIAL PRIMARY KEY,
    discount int,
    number  int
);

