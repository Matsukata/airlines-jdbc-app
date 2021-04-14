CREATE TABLE crews
(
    id   INT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE airplanes
(
    id               SERIAL PRIMARY KEY,
    code_name        VARCHAR(255) NOT NULL,
    model            VARCHAR(255) NOT NULL,
    manufacture_date DATE         NOT NULL,
    capacity         INT          NOT NULL,
    flight_range     INT          NOT NULL,
    crew_id          INT          NOT NULL,
    CONSTRAINT airplanes_crews_FK FOREIGN KEY (crew_id) REFERENCES crews (id)
);

CREATE TABLE crew_members
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    position    VARCHAR(255),
    birthday    DATE NOT NULL,
    citizenship VARCHAR(255),
    crew_id     INT  NOT NULL,
    CONSTRAINT crew_members_crews_FK FOREIGN KEY (crew_id) REFERENCES crews (id)
);



