begin;

drop schema if exists "people" cascade;
drop type if exists "gender" cascade;

create schema "people";

CREATE TYPE gender AS ENUM ('М', 'Ж');

CREATE TABLE people.people
( 
	id_person			serial,
	last_name		character varying(50) not null,
	first_name		character varying(50) not null,
	patronymic		character varying(50) not null,
	date_of_birth		date,
	gender			gender,
	PRIMARY KEY (id_person) 
);
------------------------------------------------------------------------------------------

INSERT INTO people.people (last_name, first_name, patronymic, date_of_birth, gender) 
VALUES ('Достоевский', 'Фёдор', 'Михайлович', '1821-11-11','М');
INSERT INTO people.people (last_name, first_name, patronymic, date_of_birth, gender) 
VALUES ('Пушкин', 'Александр', 'Сергеевич', '1799-06-06','М');
INSERT INTO people.people (last_name, first_name, patronymic, date_of_birth, gender)  
VALUES ('Летов', 'Игорь', 'Фёдорович', '1964-09-10','М');
INSERT INTO people.people (last_name, first_name, patronymic, date_of_birth, gender) 
VALUES ('Дягилева', 'Янка', 'Станиславовна', '1966-09-04','Ж');
INSERT INTO people.people (last_name, first_name, patronymic, date_of_birth, gender) 
VALUES ('Терешкова', 'Валентина', 'Владимировна', '1937-03-06','Ж');
INSERT INTO people.people (last_name, first_name, patronymic, date_of_birth, gender) 
VALUES ('Горенко', 'Анна', 'Андреевна', '1889-06-23','Ж');

end;