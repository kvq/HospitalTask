CREATE TABLE IF NOT EXISTS doctor(
id bigint not null,
first_name varchar(32),
last_name varchar(32),
patronymic varchar(32),
birth_date date,
phone_number varchar(32),
position varchar(32),
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
bigint id,
primary key (username)
);