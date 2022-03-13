

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




