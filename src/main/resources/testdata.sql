DROP TABLE if EXISTS doctor CASCADE;
DROP TABLE if EXISTS patient CASCADE;
DROP SEQUENCE IF EXISTS hibernate_sequence;

CREATE TABLE IF NOT EXISTS doctor(
id bigint not null,
first_name varchar(32),
last_name varchar(32),
patronymic varchar(32),
birth_date varchar(32),
phone_number varchar(32),
position varchar(32),
primary key (id)
);

CREATE TABLE IF NOT EXISTS patient(
id int not null,
first_name varchar(32),
last_name varchar(32),
patronymic varchar(32),
birth_date varchar(32),
phone_number varchar(32),
doctor_id bigint,
primary key (id),
foreign key (doctor_id) REFERENCES doctor
);