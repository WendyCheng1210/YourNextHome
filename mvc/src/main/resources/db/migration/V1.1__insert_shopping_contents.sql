insert into users (user_name,password,first_name,last_name,phone,email,gender,birthday,address) values
('wendycheng','123456789','Wendy','Cheng','540-887-9921','wendy@gmail.com','Female','1995-12-30','132 Harding Street, Herndon, VA'),
('lilywang','223456789','Lily','Wang','540-889-0909','lwang@@yeah.net','Female','1980-01-02','997 Claytor Square, Blacksburg, VA'),
('alexflint','323456789','Alex','Flint','750-991-2836','alexf@gmail.com','Male','1992-03-05','987 Little Cir, Reston, VA');
commit;

insert into roles (name, allowed_resource, allowed_read, allowed_create, allowed_update, allowed_delete) values
('Administrator', '/', 'Y', 'Y', 'Y', 'Y') ,
('Consumer','/Categories','Y', 'N', 'N', 'N')
;
commit;

insert into orders (order_date, order_amount,order_status,order_shipping_date,order_tracking_number) values
('2020-05-20', 100.00,'Shipped','2020-05-22', '0004089956379287770'),
('2020-07-21', 90.00,'Shipped','2020-07-22', '0005089956379288880'),
('2020-08-01', 1200.00,'Shipped','2020-08-02', '0006089956379289990');
commit;

insert into categories (category_name, description) values
('Bedding', 'Turn your bedroom into your sweet dream place!'),
('Dining','Enjoy your special time!'),
('Furniture','All about comfort!');
commit;

insert into brands (name) values
('Kate Spade'),
('Olivia & Oliver'),
('Serta');
commit;

insert into products (name, price) values
('Kate Spade Microfiber Solid Sheet Set', 100.00),
('Olivia & Oliver™ 5 Pc Harper Splatter Organic Shape Gold Dinnerware Collection',90),
('Serta® Copenhagen Left-Facing Reclining Sectional Sofa with Storage',1200);
commit;


insert into users_role values
(1,1),
(2,2),
(3,2)
;
commit ;

insert into ordered_products (order_id, product_id, quantity) values
(1,1,1),
(2,2,1),
(3,3,1)
;
commit ;
