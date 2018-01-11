DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM dishes;
DELETE FROM restaurants;
ALTER SEQUENCE global_seq RESTART WITH 1;

INSERT INTO users (email, password)
VALUES ('admin@gmail.com', '$2a$10$WJfAEmY3KN7DDSPGapQj3uoCTt0NQNAzVQttKFy1ouoH5tNp6QJly');

INSERT INTO users (email, password)
VALUES ('mussalimov46@gmail.com', '$2a$10$Bo84juDRbnz01ONGVtLOsudsHA6jo5tsJ8ebwdOJ03fTSl.KLgPK2');

INSERT INTO user_roles(user_id, role) VALUES
(1, 'ADMIN'),
(2, 'USER');

/*
INSERT INTO restaurants(name)
VALUES ('Мелиот');

INSERT INTO restaurants(name)
VALUES ('LONDON BAR');

INSERT INTO restaurants(name)
VALUES ('Дракон');

INSERT INTO dishes(restaurant_id, name, price)
VALUES (3, 'Салат цезарь', 590);

INSERT INTO dishes(restaurant_id, name, price)
VALUES (3, 'Белая вещалица', 1290);

INSERT INTO dishes(restaurant_id, name, price)
VALUES (3, 'Жульен', 390);

INSERT INTO dishes(restaurant_id, name, price)
VALUES (4, 'Индейка', 1790);

INSERT INTO dishes(restaurant_id, name, price)
VALUES (4, 'БОРЩЕЦ', 290);

INSERT INTO dishes(restaurant_id, name, price)
VALUES (4, 'Дефлопе с семенами кациуса', 6990);

INSERT INTO dishes(restaurant_id, name, price)
VALUES (5, 'Утка по-пекински', 2290);

INSERT INTO dishes(restaurant_id, name, price)
VALUES (5, 'Лапша', 490);

INSERT INTO dishes(restaurant_id, name, price)
VALUES (5, 'Рис с дважды запеченной хрюшей', 1290);
*/


