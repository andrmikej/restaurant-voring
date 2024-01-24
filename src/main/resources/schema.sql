drop table if exists menu_item;
drop table if exists vote;
drop table if exists restaurant;
drop table if exists user_role;
drop table if exists users;

create table users (
                       id integer generated always as identity primary key,
                       name varchar not null,
                       email varchar not null,
                       password varchar not null,
                       enabled boolean default true not null,
                       registered timestamp default now() not null
);

create unique index users_unique_email_idx on users (email);

create table user_role (
                           user_id integer not null references users(id) on delete cascade,
                           role varchar not null,
                           constraint user_role_index unique (user_id, role)
);

create table restaurant (
                            id integer generated always as identity primary key,
                            name varchar not null,
                            address varchar not null
);

create table menu_item (
                           id integer generated always as identity primary key,
                           name varchar not null,
                           price integer not null,
                           date date not null default current_date(),
                           restaurant_id integer not null references restaurant(id) on delete cascade
);
create unique index menu_restaurant_date_idx on menu_item(restaurant_id, date, name);

create table vote (
                      id integer generated always as identity primary key,
                      date date default current_date() not null,
                      user_id integer not null references users(id) on delete cascade,
                      restaurant_id integer not null references restaurant(id) on delete cascade,
                      constraint user_date_index unique (user_id, date)
);

create index vote_restaurant_date_idx on vote(restaurant_id, date);