CREATE TABLE airplane
(
    id               SERIAL,
    code_name        VARCHAR(255) NOT NULL,
    model            VARCHAR(255) NOT NULL,
    manufacture_date DATE         NOT NULL,
    capacity         BIGINT       NOT NULL,
    flight_range     BIGINT       NOT NULL,
    CONSTRAINT airplane_PK PRIMARY KEY (id)
);

CREATE TABLE crew
(
    id          SERIAL,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    position    VARCHAR(255),
    birthday    DATE NOT NULL,
    citizenship VARCHAR(255),
    CONSTRAINT crew_PK PRIMARY KEY (id)
);

CREATE TABLE airplane_crew_group
(
    airplane_id BIGINT NOT NULL,
    crew_id     BIGINT NOT NULL,
    CONSTRAINT PK_airplane_crew_group PRIMARY KEY (airplane_id, crew_id),
    CONSTRAINT FK_airplane_crew_airplane FOREIGN KEY (airplane_id)
        REFERENCES airplane (id),
    CONSTRAINT FK_airplane_crew_crew FOREIGN KEY (crew_id)
        REFERENCES crew (id)
);
