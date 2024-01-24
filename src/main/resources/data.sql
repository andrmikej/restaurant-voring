insert into users (name, email, password)
values ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest'),
       ('User1', 'user1@gmail.com', '{noop}password1'),
       ('User2', 'user2@gmail.com', '{noop}password2'),
       ('User3', 'user3@gmail.com', '{noop}password3'),
       ('User4', 'user4@gmail.com', '{noop}password4'),
       ('User5', 'user5@gmail.com', '{noop}password5');

insert into user_role (role, user_id)
values ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2),
       ('USER', 4),
       ('USER', 5),
       ('USER', 6),
       ('USER', 7),
       ('USER', 8);

insert into restaurant (name, address)
values ( 'Park Kitchen', '30 Tarasa Shevchenka Blvd Hilton Kyiv, Kyiv 01030 Ukraine' ),
       ('Tolstiy & Tonkiy Restaurant', 'Proreznaya St., 15A, Kyiv 02121 Ukraine'),
       ('Fish House Restaurant & Oyster Bar', 'vul. Ioanna Pavla II 5, Kyiv 01042 Ukraine'),
       ('Whisky Corner', 'Sofiivska St., 16/16, Kyiv 01001 Ukraine'),
       ('Khutorets na Dnipri', 'Naberezhno-Kreshhatitskaya St, 10_, Kyiv 04070 Ukraine');

insert into menu_item (name, price, restaurant_id)
values ('Greek salad', 280, 1 ),
       ('Shrimps', 350, 1 ),
       ('Carpaccio', 850, 1 ),
       ('Oysters', 99, 2 ),
       ('Chicken salad', 299, 2 ),
       ('Grilled vegetables', 130, 2 ),
       ('Fried Pickles', 250, 3 ),
       ('Scallop', 420, 3 ),
       ('Catfish', 350, 3 ),
       ('Cullen Skink Soup', 270, 4 ),
       ('Beef "Wellington"', 730, 4 ),
       ('Pike roe', 625, 4 ),
       ('Baked turkey with apples', 680, 5 ),
       ('Vegetable salad', 180, 5 ),
       ('Dumplings', 165, 5 );

insert into vote (user_id, restaurant_id)
values (1,  2),
       (4,  3),
       (5,  2),
       (6,  1),
       (7,  2),
       (8,  5);