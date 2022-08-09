use schema_cataloge;

create table categories
(
    id   bigint unsigned auto_increment,
    name varchar(30) not null,
    primary key (id)
);

create table features
(
    id              bigint unsigned auto_increment,
    category_id     bigint unsigned,
    category_option varchar(30) not null,
    primary key (id),
    foreign key (category_id) references categories (id)

);

create table option_value
(
    id         bigint unsigned auto_increment,
    product_id bigint unsigned,
    feature_id bigint unsigned,
    value      varchar(30) not null,
    primary key (id),
    foreign key (product_id) references products (id),
    foreign key (feature_id) references features (id)

);

create table products
(
    id          bigint unsigned auto_increment,
    name        varchar(30) not null,
    category_id bigint unsigned,
    price       bigint unsigned,
    primary key (id),
    foreign key (category_id) references categories (id)

);

