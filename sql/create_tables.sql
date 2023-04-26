drop schema if exists book_store cascade;
create SCHEMA IF NOT EXISTS book_store;
SET SCHEMA 'book_store';

create TABLE IF NOT EXISTS book_store.users
(
    id              BIGSERIAL    NOT NULL,
    first_name      VARCHAR(150) NOT NULL,
    surname         VARCHAR(150) NOT NULL,
    PRIMARY KEY (id)
);


create type state as Enum ('NEW', 'COMPLETED', 'CANCELLED', 'IN_PROGRESS');

CREATE TABLE IF NOT EXISTS book_store.stores
(
    id   BIGSERIAL    NOT NULL,
    name VARCHAR(150) NOT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS book_store.orders
(
    id              BIGSERIAL      NOT NULL,
    creation_date   TIMESTAMP(0)   NOT NULL,
    completing_date TIMESTAMP(0)   ,
    user_id         BIGINT         NOT NULL,
    store_id        BIGINT         NOT NULL,
    status          state,
    total_price     DECIMAL(10, 0) NOT NULL,
    PRIMARY KEY (id),

    CONSTRAINT fk_order_user1
        FOREIGN KEY (user_id)
            REFERENCES book_store.users (id) on delete cascade,

    CONSTRAINT fk_order_store1
        FOREIGN KEY (store_id)
            REFERENCES book_store.stores (id) on delete cascade
);
CREATE INDEX fk_order_user1_idx ON book_store.orders (user_id ASC);


CREATE TABLE IF NOT EXISTS book_store.books
(
    id                 BIGSERIAL      NOT NULL,
    author             VARCHAR(150)   NOT NULL,
    publisher_name     VARCHAR(150)   NOT NULL,
    date_of_publishing DATE           NOT NULL,
    date_of_writing    DATE           NOT NULL,
    price_of_book      DECIMAL(10, 0) NOT NULL,
    order_id           BIGINT         NOT NULL,
    title              VARCHAR(150)   NOT NULL,
    store_id           BIGINT         NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_book_order1
        FOREIGN KEY (order_id)
            REFERENCES book_store.orders (id) on delete cascade,
    CONSTRAINT fk_book_store1
        FOREIGN KEY (store_id)
            REFERENCES book_store.stores (id) on delete cascade
);
