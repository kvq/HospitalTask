CREATE TABLE IF NOT EXISTS tariff(
name varchar(32),
price DECIMAL(8,2),
primary key (name)
);

CREATE TABLE IF NOT EXISTS doctor(
id bigint not null,
first_name varchar(32),
last_name varchar(32),
patronymic varchar(32),
birth_date date,
phone_number varchar(32),
speciality varchar(32),
tariff_name varchar(32),
foreign key (tariff_name) references tariff(name),
primary key (id)
);

CREATE TABLE IF NOT EXISTS patient(
id bigint not null,
first_name varchar(32),
last_name varchar(32),
patronymic varchar(32),
birth_date date,
phone_number varchar(32),
doctor_id bigint,
primary key (id)
);

CREATE TABLE IF NOT EXISTS security_user(
username varchar(32),
password varchar(64),
account_type varchar(32),
role varchar(32),
id bigint,
primary key (username)
);