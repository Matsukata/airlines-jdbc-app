INSERT INTO crews (id, name)
VALUES (1, 'Vityaz');
INSERT INTO crews (id, name)
VALUES (2, 'Strizhi');
INSERT INTO crews (id, name)
VALUES (3, 'Sokol');

INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('Airbus', 'А319', '2019-03-12', 168, 3800, 1);
INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('Airbus', 'А320', '2019-01-18', 132, 4200, 2);
INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('Boeing', '737', '2019-03-12', 130, 3900, 1);
INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('Boeing', '777', '2019-01-18', 168, 4000, 3);
INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('Boeing', '747', '2019-01-18', 130, 5000, 2);


INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship)
VALUES ('Igor', 'Shpack', 'PILOT_IN_COMMAND', '1983-12-08', 'BRAZILIAN');
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship)
VALUES ('Igor', 'Tkachenko', 'PILOT_IN_COMMAND', '1980-10-08', 'ITALIAN');
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship)
VALUES ('Dmitriy', 'Hachkovskiy', 'PILOT_IN_COMMAND', '1979-02-08', 'NORWEGIAN');
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship)
VALUES ('Oleg', 'Ryapolov', 'AIRCRAFT_PILOT', '1986-12-04', 'KOREAN');
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship)
VALUES ('Egor', 'Shleeman', 'FLIGHT_ENGINEER', '1987-05-08', 'BRAZILIAN');
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship)
VALUES ('Eugene', 'Danilov', 'SECOND_OFFICER', '1991-10-08', 'ITALIAN');
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship)
VALUES ('Marat', 'Abakulov', 'FIRST_OFFICER', '1994-03-08', 'NORWEGIAN');
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship)
VALUES ('Mi', 'Chen', 'FLIGHT_ATTENDANT', '1995-10-08', 'CHINESE');
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship)
VALUES ('Mary', 'Jones', 'FLIGHT_ATTENDANT', '1994-12-03', 'BRAZILIAN');
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship)
VALUES ('Jane', 'Lewis', 'FLIGHT_ATTENDANT', '1992-10-01', 'NORWEGIAN');

INSERT INTO crews_crew_members (crew_id, crew_member_id)
VALUES (1, 2);
INSERT INTO crews_crew_members (crew_id, crew_member_id)
VALUES (2, 9);
INSERT INTO crews_crew_members (crew_id, crew_member_id)
VALUES (3, 8);
INSERT INTO crews_crew_members (crew_id, crew_member_id)
VALUES (1, 1);
INSERT INTO crews_crew_members (crew_id, crew_member_id)
VALUES (1, 5);