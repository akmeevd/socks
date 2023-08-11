-- liquibase formatted sql

-- changeset akmeevd:1
create table socks(
color varchar(50),
cotton_part smallint,
quantity integer,
primary key(color, cotton_part)
);

create table batch(
id serial primary key,
number_socks integer,
socks_color varchar(50),
socks_cotton_part smallint,
is_incoming boolean
);

create table socks_batch(
batch_id serial references batch (id),
socks_color varchar(50),
socks_cotton_part smallint,
foreign key (socks_color, socks_cotton_part) references socks (color, cotton_part)
);

create table store_keeper(
id serial primary key,
first_name varchar(50),
last_name varchar(50),
phone varchar(12),
user_name varchar(50),
password text,
registration_date timestamp,
role varchar(5)
);
