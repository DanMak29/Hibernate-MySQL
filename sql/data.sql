use schema_cataloge;
insert into categories(name)
values ('PC'),
       ('Phone');

insert into features(category_id, category_option)
values (1, 'ОЗУ'),
       (1, 'Процессор'),
       (2, 'Батарея'),
       (2, 'Операционная система')
;

insert into option_value(product_id, feature_id, value)
values (1, 1, '32 ГБ'),
       (1, 2, 'Core i5'),
       (2, 3, '3500 Mh'),
       (2, 4, 'Android');

insert into products(name, category_id, price)
values ('MSI', 1, 250000),
       ('Samsung', 2, 150000);

