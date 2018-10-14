create database car_storage;

create table body (
	id serial primary key,
	type varchar(200)
);

create table engine (
	id serial primary key,
	power integer
);

create table transmission (
	id serial primary key,
	automatic boolean
);

create table car (
	id serial primary key,
	id_body integer references body(id) unique,
	id_engine integer references engine(id) unique,
	id_transmission integer references transmission(id) unique
);

insert into body (type) values ('седан');
insert into body (type) values ('седан');
insert into body (type) values ('хэтчбек');
insert into body (type) values ('универсал');
insert into body (type) values ('универсал');
insert into body (type) values ('универсал');

insert into engine (power) values (100);
insert into engine (power) values (100);
insert into engine (power) values (80);
insert into engine (power) values (180);
insert into engine (power) values (180);
insert into engine (power) values (180);
insert into engine (power) values (250);
insert into engine (power) values (400);

insert into transmission (automatic) values (true);
insert into transmission (automatic) values (false);
insert into transmission (automatic) values (false);
insert into transmission (automatic) values (false);

insert into car (id_body, id_engine, id_transmission) values (7, 11, 3);
insert into car (id_body, id_engine, id_transmission) values (8, 12, 1);
insert into car (id_body, id_engine, id_transmission) values (9, 13, 4);

--1. Вывести список всех машин и все привязанные к ним детали.
select c.id, b.type, e.power, t.automatic from car as c
		left outer join body as b on c.id_body = b.id
		left outer join engine as e on c.id_engine = e.id
		left outer join transmission as t on c.id_transmission = t.id;

--2. Вывести отдельно детали, которые не используются в машине, кузова, двигатели, коробки передач.
select b.id, b.type, e.id, e.power, t.id, t.automatic from car as c
		right outer join transmission as t on c.id_transmission = t.id
		full outer join body as b on c.id_body = b.id
		full outer join engine as e on c.id_engine = e.id
			where c.id_body is null or
			c.id_engine is null or
			c.id_transmission is null;