

insert into product ( id_product, name, cost, promotional ) values
(1,'Fish Sturgeon', 1.80, false),
(2,'Meat', 5.01, false),
(3,'KitKat', 2.8, true),
(4,'Fish Perch', 1.3, false),
(5,'Snickers', 3.5, true),
(6,'Bounty', 2.3, true),
(7,'Tic-tac', 1.7, true),
(8,'NUTS', 1.4, true);


insert into discountCard (sale) values
( 3 ),
( 6 );


drop table product;
SELECT nextval('product_id_seq'::regclass);
select setval('product_id_seq',1);

