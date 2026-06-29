CREATE TABLE empleados (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(25) NOT NULL,
   apellido VARCHAR(25) NOT NULL,
   edad INT NOT NULL,
   rut INT NOT NULL,
   dv VARCHAR(1) NOT NULL,
   correo VARCHAR(40) NOT NULL,
   telefono INT(9) NOT NULL
);
INSERT INTO empleados (nombre, apellido, edad, rut, dv, correo, telefono) VALUES ('Jordan','Torres',21, 12332456,'5','JordanTorres@gmail.com', 789654789);

CREATE TABLE duenos (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(20) NOT NULL,
   apellido VARCHAR(20) NOT NULL
);
INSERT INTO duenos (nombre, apellido) VALUES ('Benjamin', 'Miranda');

CREATE TABLE tiendas (
   id_tienda INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(50) NOT NULL,
   direccion VARCHAR(65) NOT NULL,
   id_duenos INT,
   id_empleados INT,
   id_stock_comics INT,
   CONSTRAINT fk_tiendas_duenos FOREIGN KEY (id_duenos) REFERENCES duenos(id),
   CONSTRAINT fk_tiendas_empleados FOREIGN KEY (id_empleados) REFERENCES empleados(id)
);
INSERT INTO tiendas (nombre, direccion, id_duenos, id_empleados) VALUES ('LOS CRACKS', 'la Farfana 1150',1,1);

CREATE TABLE stock_comics (
    id_stock INT AUTO_INCREMENT PRIMARY KEY,
    id_tienda INT,
    id_comic INT NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    CONSTRAINT fk_stock_tienda FOREIGN KEY (id_tienda) REFERENCES tiendas(id_tienda)
);
