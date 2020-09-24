DROP TABLE IF EXISTS userJDBCS cascade;
DROP TABLE IF EXISTS roles cascade;
DROP TABLE IF EXISTS orders cascade;
DROP TABLE IF EXISTS brands cascade;
DROP TABLE IF EXISTS categories cascade;
DROP TABLE IF EXISTS products cascade;
DROP TABLE IF EXISTS ordered_product cascade;
DROP table IF EXISTS users_role cascade;
DROP table IF EXISTS ordered_products cascade;

CREATE TABLE users (
    id              BIGSERIAL NOT NULL PRIMARY KEY,
    user_name       VARCHAR(50),
    password        VARCHAR(50),
    first_name      VARCHAR(50),
    last_name       VARCHAR(50),
    phone           VARCHAR(20),
    email           VARCHAR(50),
    gender          VARCHAR(10),
    birthday        DATE,
    address         VARCHAR(100)
);

CREATE TABLE roles (
    id                   BIGSERIAL NOT NULL PRIMARY KEY,
    name                 VARCHAR(30) not null unique,
    allowed_resource     VARCHAR(200),
    allowed_read         boolean not null default false,
    allowed_create       boolean not null default false,
    allowed_update       boolean not null default false,
    allowed_delete       boolean not null default false
);

CREATE TABLE users_role (
    user_id    bigint NOT NULL,
    role_id    bigint NOT NULL,
    FOREIGN KEY (user_id) references userJDBCS(id),
    FOREIGN KEY (role_id) references roles(id)
);

CREATE TABLE orders (
    id                      BIGSERIAL NOT NULL PRIMARY KEY,
    user_id                 BIGSERIAL NOT NULL,
    order_date              DATE ,
    order_amount            DECIMAL(20,2),
    order_status            VARCHAR(30),
    order_shipping_date     DATE ,
    order_tracking_number   VARCHAR(50),
    FOREIGN KEY (user_id) references userJDBCS(id)
);

CREATE TABLE categories (
    id            BIGSERIAL NOT NULL PRIMARY KEY,
    category_name VARCHAR(50),
    description   VARCHAR(100)
);

CREATE TABLE brands (
    id          BIGSERIAL NOT NULL PRIMARY KEY,
    name        VARCHAR(50)
);

CREATE TABLE products (
    id            BIGSERIAL NOT NULL PRIMARY KEY,
    name          VARCHAR(80),
    category_id   BIGSERIAL NOT NULL,
    brand_id      BIGSERIAL NOT NULL,
    price         DECIMAL(20,2),
    FOREIGN KEY (category_id) references categories(id),
    FOREIGN KEY (brand_id) references brands(id)
);

CREATE TABLE ordered_products (
    order_id    BIGSERIAL NOT NULL,
    product_id  BIGSERIAL NOT NULL,
    quantity    INTEGER,
    FOREIGN KEY (order_id) references orders(id),
    FOREIGN KEY (product_id) references products(id)
);


