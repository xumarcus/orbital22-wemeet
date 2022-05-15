CREATE TABLE users
(
    username VARCHAR(50)  NOT NULL,
    password VARCHAR(500) NOT NULL,
    enabled  BOOLEAN      NOT NULL DEFAULT TRUE,
    PRIMARY KEY (username)
);

CREATE TABLE authorities
(
    authority VARCHAR(50) NOT NULL,
    PRIMARY KEY (authority)
);

CREATE TABLE authorizations
(
    username  VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    FOREIGN KEY (username) REFERENCES users (username),
    FOREIGN KEY (authority) REFERENCES authorities (authority)
);