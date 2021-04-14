INSERT INTO crews (id, name)
VALUES (1,'Vityaz');
INSERT INTO crews (id, name)
VALUES (2, 'Strizhi');

INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('SU', '30-SM', '2019-03-12', 2, 3000, 1);
INSERT INTO airplanes (code_name, model, manufacture_date, capacity, flight_range, crew_id)
VALUES ('MIG', '29', '2019-01-18', 2, 3000, 2);


INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship, crew_id)
VALUES ('Igor', 'Shpack', 'pilot in command', '1983-12-08', 'Russia', 1);
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship, crew_id)
VALUES ('Oleg', 'Ryapolov', 'pilot in command', '1986-12-04', 'Russia', 1);
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship, crew_id)
VALUES ('Igor', 'Tkachenko', 'pilot in command', '1980-10-08', 'Russia', 2);
INSERT INTO crew_members (first_name, last_name, position, birthday, citizenship, crew_id)
VALUES ('Dmitriy', 'Hachkovskiy', 'pilot in command', '1979-02-08', 'Russia', 2);

