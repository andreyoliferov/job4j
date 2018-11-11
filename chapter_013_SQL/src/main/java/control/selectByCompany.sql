CREATE TABLE company(
  id integer NOT NULL,
  name character varying,
  CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE person(
  id integer NOT NULL,
  name character varying,
  company_id integer,
  CONSTRAINT person_pkey PRIMARY KEY (id)
);

--Получить в одном запросе:
--// - имена всех лиц, которые НЕ принадлежат компании с id = 5
--// - название компании для каждого человека
SELECT p.name, c.name FROM person as p 
  LEFT OUTER JOIN company as c on c.id = p.company_id 
  WHERE p.company_id != 5;

--Выбрать название компании с максимальным количеством лиц + количество человек в этой компании
SELECT c.name, count(p.company_id) total FROM company as c 
  LEFT OUTER JOIN person as p on c.id = p.company_id 
  GROUP BY c.name 
  ORDER BY total DESC LIMIT 1;
