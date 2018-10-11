--создание базы данных
create database tracker_base;

create table roles (
		id serial primary key,
		name text
);

create table users (
		id serial primary key,
		name text,
		role integer references roles(id)
);

create table rules (
		id serial primary key,
		name text
);

create table role_rule (
		id serial primary key,
		role integer references roles(id),
		rule integer references rules(id)
);

create table states (
		id serial primary key,
		name text
);

create table categories (
		id serial primary key,
		name text
);

create table orders (
		id serial primary key,
		autor integer unique references users(id),
		state integer references states(id),
		category integer references categories(id)
);

create table comments (
		id serial primary key,
		description text,
		to_order integer references orders(id),
		to_user integer references users(id)
);

create table attaches (
		id serial primary key,
		address_link text,
		to_order integer references orders(id)
);

insert into roles(name) values('administrator');
insert into rules(name) values('full_access');
insert into role_rule(role, rule) values(1, 1);
insert into users(name, role) values('Andrey', 1);
insert into states(name) values('received');
insert into categories(name) values('game');
insert into orders(autor, state, category) values(1, 1, 1);
insert into comments(description, to_user, to_order) values('Тестовый комментарий', 1, 1);
insert into attaches(address_link, to_order) values('https://ya.ru', 1);