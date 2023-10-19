CREATE TABLE status (
                        id_status SERIAL PRIMARY KEY,
                        status VARCHAR(255) NOT NULL,
                        type_status VARCHAR(255) NOT NULL
); /*para roda fora do compose apague o create table*/

INSERT INTO status (id_status, status, type_status) VALUES (1, 'New', 'type_user');
INSERT INTO status (id_status, status, type_status) VALUES (2, 'Active', 'type_user');
