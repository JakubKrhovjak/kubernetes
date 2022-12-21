CREATE TABLE IF NOT EXISTS user
(
    id       serial primary key,
    username varchar(255) not null,
    password varchar(255) not null
);