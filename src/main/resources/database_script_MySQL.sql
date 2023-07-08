CREATE DATABASE IF NOT EXISTS product_manager;

use product_manager;

CREATE TABLE roles
(
    user_id VARCHAR(255) NOT NULL,
    `role`  VARCHAR(30)  NOT NULL
);

CREATE TABLE users
(
    id       VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE roles
    ADD CONSTRAINT fk_roles_on_user_entity FOREIGN KEY (user_id) REFERENCES users (id);

CREATE TABLE substances
(
    id        VARCHAR(25)  NOT NULL,
    name      VARCHAR(50)  NOT NULL,
    color     INT          NOT NULL,
    descript  VARCHAR(255) NULL,
    image_url VARCHAR(255) NULL,
    CONSTRAINT pk_substances PRIMARY KEY (id)
);

CREATE TABLE compositions
(
    product_id   BIGINT      NOT NULL,
    content      DECIMAL     NOT NULL,
    substance_id VARCHAR(25) NOT NULL,
    CONSTRAINT pk_compositions PRIMARY KEY (product_id, substance_id)
);

CREATE TABLE products
(
    id        BIGINT AUTO_INCREMENT NOT NULL,
    name      VARCHAR(25)           NOT NULL,
    full_name VARCHAR(50)           NOT NULL,
    image_url VARCHAR(255)          NULL,
    is_common BIT(1)                NOT NULL,
    user_id   VARCHAR(255)          NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_USER FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE compositions
    ADD CONSTRAINT fk_compositions_on_product_entity FOREIGN KEY (product_id) REFERENCES products (id);

ALTER TABLE compositions
    ADD CONSTRAINT fk_compositions_on_substance_entity FOREIGN KEY (substance_id) REFERENCES substances (id);

