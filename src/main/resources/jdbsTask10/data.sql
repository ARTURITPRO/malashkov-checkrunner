

insert into "product"  ( name, cost, promotional ) values
('Fish Sturgeon', 1.80, false),
('Meat', 5.01, false),
('KitKat', 2.8, true),
('Fish Perch', 1.3, false),
('Snickers', 3.5, true),
('Bounty', 2.3, true),
('Tic-tac', 1.7, true),
('NUTS', 1.4, true);

------------------------------------------------------------

insert into discountCard (discount, number) values
( 1, 1),
( 2, 2),
( 3,3),
( 4,4),
( 5, 5),
( 6, 6),
( 7, 7),
( 8, 8),
( 9, 9),
( 10, 10);

/*SELECT nextval('product_id_seq'::regclass);
select setval('product_id_seq',5);

select * from product
              ORDER BY id
              OFFSET    2
              LIMIT     5;
  */

