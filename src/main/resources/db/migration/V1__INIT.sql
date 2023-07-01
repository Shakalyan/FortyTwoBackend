create table users
(
    id             serial primary key,
    first_name varchar(255),
    last_name varchar(255),
    username varchar(255),
    vk_token varchar(255),
    vk_token_expiration timestamp without time zone
);

create table jwt_token
(
    id            serial primary key,
    user_id       bigint references users (id) not null,
    refresh_token varchar(255)                 not null
);