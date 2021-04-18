DROP TABLE IF EXISTS crew_id;

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
    crew_id          INT,
    CONSTRAINT airplanes_crews_FK FOREIGN KEY (crew_id) REFERENCES crews (id)
);

CREATE TABLE crew_members
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    position    VARCHAR(255),
    birthday    DATE NOT NULL,
    citizenship VARCHAR(255)
);

CREATE TABLE crews_crew_members
(
    crews_id        BIGINT NOT NULL,
    crew_members_id BIGINT NOT NULL,
    CONSTRAINT PK_crews_crew_members PRIMARY KEY (crews_id, crew_members_id),
    CONSTRAINT FK_crews_crew_members FOREIGN KEY (crews_id)
        REFERENCES crews (id),
    CONSTRAINT FK_crew_members_crews FOREIGN KEY (crew_members_id)
        REFERENCES crew_members (id)
);