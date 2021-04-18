INSERT INTO crews (id, name)
VALUES (1,'Vityaz');
INSERT INTO crews (id, name)
VALUES (2, 'Strizhi');
INSERT INTO crews (id, name)
VALUES (3, 'Sokol');

INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('Airbus', 'А319', '2019-03-12', 168, 3800, ?);
INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('Airbus', 'А320', '2019-01-18', 132, 4200, ?);
INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('Boeing', '737', '2019-03-12', 130, 3900, ?);
INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('Boeing', '777', '2019-01-18', 168, 4000, ?);
INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('Boeing', '747', '2019-01-18', 130, 5000, ?);


INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship, crew_id)
VALUES ('Igor', 'Shpack', 'pilot in command', '1983-12-08', 'Russia', 1);
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship, crew_id)
VALUES ('Oleg', 'Ryapolov', 'pilot in command', '1986-12-04', 'Russia', 1);
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship, crew_id)
VALUES ('Igor', 'Tkachenko', 'pilot in command', '1980-10-08', 'Russia', 2);
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship, crew_id)
VALUES ('Dmitriy', 'Hachkovskiy', 'pilot in command', '1979-02-08', 'Russia', 3);

