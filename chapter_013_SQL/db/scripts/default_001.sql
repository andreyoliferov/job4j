CREATE TABLE orders (
  id uuid primary key,
  name varchar(300),
  description text,
  date_created timestamp
);