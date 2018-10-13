--1. Написать запрос получение всех продуктов с типом "СЫР"
select * from product 
		where type_id in (
			select id from type where name = 'СЫР'
		);
		
select p.id, p.name from product as p 
		inner join type as t
		on p.type_id = t.id
		where t.name = 'СЫР';

--2. Написать запрос получения всех продуктов, у кого в имени есть слово "мороженное"
select * from product
		where type_id in (
			select if from type where name like = '%мороженое%'
		);
	
--3. Написать запрос, который выводит все продукты, срок годности которых заканчивается в следующем месяце.	
select * from product
		where expired_date between 
			date_trunc('month', current_timestamp + interval '1 month')
		and 
			date_trunc('month', current_timestamp + interval '2 month') - interval '1 day';
		);

--4. Написать запрос, который выводит самый дорогой продукт.		
select * from product
		where price = (select max(price) from product) 
		limit 1;

--5. Написать запрос, который выводит количество всех продуктов определенного типа.		
select type_id, count(*) from product group type_id;

--6. Написать запрос получение всех продуктов с типом "СЫР" и "МОЛОКО"
select * from product 
		where type_id in (
			select id from type where name = 'СЫР',
			select id from type where name = 'МОЛОКО'
		);
	
--7. Написать запрос, который выводит тип продуктов, которых осталось меньше 10 штук. 	
with temp as(
			select type_id, count(*) as total_count from product group by type_id
		)
select * from type
		where id in (
			select type_id from temp where total_count  < 10
		);

--8. Вывести все продукты и их тип.	
select p.id, p.expired_date, p.price, t.name from product as p
		inner join type as t on p.type_id = t.id;