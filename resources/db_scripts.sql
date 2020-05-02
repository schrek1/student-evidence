CREATE SCHEMA student_evidence;

CREATE TABLE student_evidence.student
(
    dbId      INT AUTO_INCREMENT PRIMARY KEY,
    studentId INT                                        NOT NULL,
    firstname VARCHAR(60)                                NOT NULL,
    lastname  VARCHAR(60)                                NOT NULL,
    born      DATE                                       NOT NULL,
    type      ENUM ('TECHNICAL', 'HUMANITY', 'DISTANCE') NOT NULL
);

CREATE INDEX index_student_id ON student_evidence.student (studentId);
CREATE INDEX index_name ON student_evidence.student (firstname, lastname);


CREATE TABLE student_evidence.evaluation
(
    dbId       INT AUTO_INCREMENT PRIMARY KEY,
    studentId  INT       NOT NULL,
    evaluation TINYINT   NOT NULL,
    date       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (studentId) REFERENCES student_evidence.student (studentId)
);
