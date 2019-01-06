
CREATE EXTENSION pgcrypto;

CREATE TABLE patients(
    id SERIAL NOT NULL PRIMARY KEY,
    uuid UUID  DEFAULT gen_random_uuid(),
    first_name VARCHAR NOT NULL,
    last_name VARCHAR NOT NULL,
    id_number VARCHAR NOT NULL UNIQUE
)

CREATE TABLE admins(

    id SERIAL NOT NULL PRIMARY KEY,
    uuid UUID  DEFAULT gen_random_uuid(),
    username VARCHAR NOT NULL UNIQUE,
    password VARCHAR NOT NULL
);

INSERT INTO patients(first_name,last_name,id_number) VALUES('Anabelle','Annabelle','9411210001123');
INSERT INTO admins(username,password) VALUES('kwasidev','$2a$10$z/MnFqb6Sq7L7KTvKz8RjOmUqpdds3J8lW1xiiz8QmX8U7JtC9RvG');
INSERT INTO admins(username,password) VALUES('admina','$2a$10$z/MnFqb6Sq7L7KTvKz8RjOmUqpdds3J8lW1xiiz8QmX8U7JtC9RvG');

CREATE VIEW patients_json AS(

    SELECT 
        first_name AS "firstName" ,last_name AS "lastName" , id_number AS "IdNumber" ,uuid
    FROM 
        patients
);

CREATE UNIQUE INDEX uuid_idx_admin ON admins(uuid);
CREATE UNIQUE INDEX uuid_idx_patient ON patients(uuid);