

insert into product ( name, cost, promotional ) values
('Fish Sturgeon', 1.80, false),
('Meat', 5.01, false),
('KitKat', 2.8, true),
('Fish Perch', 1.3, false),
('Snickers', 3.5, true),
('Bounty', 2.3, true),
('Tic-tac', 1.7, true),
('NUTS', 1.4, true);


insert into discountCard (sale) values
( 3 ),
( 6 );


drop table product;
SELECT nextval('id_seq'::regclass);
select setval('id_seq',1);

