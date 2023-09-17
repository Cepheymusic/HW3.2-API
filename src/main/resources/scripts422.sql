create table car (
id serial primary key,
make varchar(20),
model varchar(20),
price numeric(10, 2)
);

create table car_owner(
id serial primary key,
name varchar not null,
age serial,
has_licence boolean default false,
car id serial references car(id)
)


