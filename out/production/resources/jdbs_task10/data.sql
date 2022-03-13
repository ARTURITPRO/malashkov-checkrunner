
insert into products (name, price, sale, quantity, in_stock) values
('Fish', 11, true,0, true),
('Cat', 111, true,0,true),
('Alcohol', 555, false,0,true),
('Bread', 785, true,0,true),
('Meat', 56.47, false,0,true),
('Coffee', 457.7, true,0,true),
('Milk', 777, false,0,true),
('Eig', 666.66, false,0,true),
('Potato', 74, true,0,true),
('Fruit', 73, false,0,true);

insert into discount_cards (discount_percentage) values
(3),
(5),
(7),
(10),
(12),
(15),
(25);

insert into users (username, password, enabled) values
('admin', '{bcrypt}$2a$12$Qdg5iV9.ttgS.pKkdmGQ6u93i.bFcvHqOnkDzlyzLj3cBYZ7WZylG', true),
('user', '{bcrypt}$2a$12$4ufTIoFSUirK8/xASrfGLOljwqZoJmcBsSUXysnzvPLovYojJAlTK', true);


insert into authorities (username, authority) values
('admin', 'ROLE_ADMIN'),
('user', 'ROLE_USER');


SELECT * FROM products;

