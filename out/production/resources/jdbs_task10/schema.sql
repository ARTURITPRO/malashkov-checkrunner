CREATE ROLE bestuser WITH
    LOGIN
    SUPERUSER
    CREATEDB
    CREATEROLE
    INHERIT
    NOREPLICATION
    CONNECTION LIMIT -1
    PASSWORD '0147896325aA';

create table products(
    id serial not null,
    name varchar(25),
    price numeric(10, 2),
    sale boolean,
    quantity int,
    in_stock boolean,
    primary key (id)
);


create table discount_cards(
    id serial not null,
    discount_percentage int,
    primary key (id)
);


create table users(
    username varchar(15),
    password varchar(100),
    enabled boolean,
    primary key (username)
);

create table authorities(
    username varchar(15),
    authority varchar(25),
    foreign key (username) references users(username)
);

