
insert into book_store.stores(name)
values ('Book Store 1'),
       ('Book Store 2'),
       ('Book Store 3');

insert into book_store.users(first_name, surname)
VALUES ('Ivan','Popov'),
       ('Anton','Lidlov'),
       ('Ivan','Gorshnev'),
       ('Artem','Popov');

insert into book_store.orders(creation_date, completing_date, user_id, store_id, status, total_price)
VALUES ('2020-06-14T14:30', '2020-06-18T18:07', 1, 1, 'COMPLETED', 1254),
       ('2021-06-16T14:30', '2021-06-20T18:07', 1, 2 ,'CANCELLED', 20225),
       ('2022-06-17T14:30', '2022-06-21T18:07', 1, 1,'IN_PROGRESS', 148),
       ('2023-06-18T14:30', '2023-06-22T18:07', 1, 2, 'NEW', 954);

INSERT INTO book_store.books(author, date_of_publishing, date_of_writing, price_of_book, publisher_name, order_id, title,
                             store_id)
VALUES ('Leo Tolstoy', '1999-01-08', '1877-01-08', 328, 'Eksmo', 1, 'Anna Karenina', 1),
       ('Gustave Flaubert', '2002-01-08', '1856-01-08', 328, 'Eksmo', 1, 'Madame Bovary', 1),
       ('Leo Tolstoy', '2008-01-08', '1867-01-08', 328, 'Eksmo', 2, 'War and Peace', 1),
       ('F. Scott Fitzgerald', '1994-01-08', '1925-01-08', 328, 'Eksmo', 2, 'The Great Gatsby', 2),
       ('Vladimir Nabokov', '2022-01-08', '1953-01-08', 328, 'Harvest', 3, 'Lolita', 2),
       ('George Eliot', '1946-01-08', '1872-01-08', 328, 'Harvest', 3, 'Middlemarch', 3),
       ('Mark Twain', '1948-01-08', '1884-01-08', 328, 'Harvest', 4, 'The Adventures of Huckleberry Finn', 3),
       ('Anton Chekhov', '2009-01-08', '1886-01-08', 328, 'Harvest', 4, 'The Stories of Anton Chekhov', 3);




