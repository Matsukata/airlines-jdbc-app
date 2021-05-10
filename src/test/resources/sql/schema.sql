DROP TABLE IF EXISTS crews_crew_members;
DROP TABLE IF EXISTS crew_members;
DROP TABLE IF EXISTS airplanes;
DROP TABLE IF EXISTS crews;

CREATE TABLE crews
(
    id   INT PRIMARY KEY,
    name VARCHAR(20) NOT NULL
);

CREATE TABLE airplanes
(
    id               SERIAL PRIMARY KEY,
    code_name        VARCHAR(10) NOT NULL UNIQUE,
    model            VARCHAR(64) NOT NULL,
    manufacture_date DATE        NOT NULL,
    capacity         INT         NOT NULL,
    flight_range     INT         NOT NULL,
    crew_id          INT,
    CONSTRAINT airplanes_crews_FK FOREIGN KEY (crew_id) REFERENCES crews (id)
);

CREATE TABLE crew_members
(
    id          SERIAL PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    position    VARCHAR(20)  NOT NULL,
    birthday    DATE         NOT NULL,
    citizenship VARCHAR(20)  NOT NULL
);

CREATE TABLE crews_crew_members
(
    crew_id        BIGINT NOT NULL,
    crew_member_id BIGINT NOT NULL,
    CONSTRAINT PK_crews_crew_members PRIMARY KEY (crew_id, crew_member_id),
    CONSTRAINT FK_crews_crew_members FOREIGN KEY (crew_id) REFERENCES crews (id),
    CONSTRAINT FK_crew_members_crews FOREIGN KEY (crew_member_id) REFERENCES crew_members (id)
);