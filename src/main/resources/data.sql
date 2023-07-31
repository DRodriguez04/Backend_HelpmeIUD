/* Aqui se hizo prueba de consulta SQL*/
INSERT INTO usuarios(username, nombre, apellido, password, fecha_nacimiento, enabled, image)
    VALUES
        ('rodriguez,daniela20@outlook.com', 'Daniela', 'Rodriguez', '', '1999-04-20', 1, 'https://marvel-b1-cdn.bc0a.com/f00000000293000/www.rockhall.com/sites/default/files/styles/c03h_hero_desktop_1140_575/public/2019-11/Whitney%20Houston%20-%20FINAL%20image%20copy.jpg?h=def3cf70&itok=DBHKwr4u');


INSERT INTO delitos(nombre, descripcion, usuarios_id)
    VALUES('robo', 'saliendo de la casa lo atracaron', 1);

INSERT INTO roles(nombre, descripcion)
    VALUES('ADMIN', 'Administrador');

INSERT INTO roles(nombre, descripcion)
    VALUES('USER', 'usuario del sistema');

INSERT INTO roles_usuarios(usuarios_id, roles_id)
    VALUES(1, 1);

INSERT INTO roles_usuarios(usuarios_id, roles_id)
    VALUES(1, 2); 