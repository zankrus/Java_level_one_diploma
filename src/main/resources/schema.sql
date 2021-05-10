CREATE TABLE projects
(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT,
    name       TEXT    NOT NULL
);

CREATE TABLE suites
(
    id    INTEGER PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(255) NOT NULL ,
    project_id INTEGER      NOT NULL REFERENCES projects
);


CREATE TABLE cases
(
    id         INTEGER PRIMARY KEY AUTO_INCREMENT,
    suite_id    INTEGER NOT NULL REFERENCES suites,
    description       TEXT    NOT NULL,
    attached_files      TEXT
);


