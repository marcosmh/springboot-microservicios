INSERT INTO usuarios (user_name, password, enabled, nombre, apellido, email) VALUES ('markcode','$2a$10$SczeowgV1.2KSFQKcvMhHeOH.ND2iZwKDpriTFAu67fFVW4avDynq',1,'Marcos','Magana','markcode@code.com');
INSERT INTO usuarios (user_name, password, enabled, nombre, apellido, email) VALUES ('admin','12346',1,'Administrador','Sistema','sistema@code.com');


INSERT INTO roles (nombre) VALUES ('ROLE_USER');
INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');

INSERT INTO usuarios_roles (usuario_id,role_id) VALUES (1,1);
INSERT INTO usuarios_roles (usuario_id,role_id) VALUES (2,2);
INSERT INTO usuarios_roles (usuario_id,role_id) VALUES (2,1);