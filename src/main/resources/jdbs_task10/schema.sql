CREATE
DATABASE product;
DROP
DATABASE product;
create table product
(
    id          BIGSERIAL PRIMARY KEY,
    name        varchar(25),
    cost        double precision,
    promotional boolean
);

create table discountCard
(
    id   BIGSERIAL PRIMARY KEY,
    sale int
);


