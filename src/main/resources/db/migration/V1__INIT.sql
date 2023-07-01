create table users
(
    id             serial primary key,
    firstName varchar(255),
    lastName varchar(255),
    username varchar(255),
    password varchar(255)
);

create table jwt_token
(
    id            serial primary key,
    user_id       bigint references users (id) not null,
    refresh_token varchar(255)                 not null
);