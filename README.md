sql

CREATE TABLE autores (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(15) NOT NULL
); INSERT INTO autores (nombre) VALUES ('Jose');


CREATE TABLE categorias (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(20) NOT NULL
); INSERT INTO categorias (nombre) VALUES ('Accion');


CREATE TABLE clientes (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(100) NOT NULL,
   apellido VARCHAR(100) NOT NULL,
   edad INT(3) NOT NULL,
   rut INT(8) NOT NULL,
   dv VARCHAR(1) NOT NULL,
   correo VARCHAR(40) NOT NULL,
   telefono INT(9),
   direccion VARCHAR(100) NOT NULL
); INSERT INTO clientes (nombre, apellido, edad, rut, dv, correo, telefono, direccion) VALUES ('Carlos','Papu',20, 22345785,'K','carlos123@gmail.com',987654312, 'La farfana city');


CREATE TABLE comics (
   id INT AUTO_INCREMENT PRIMARY KEY,
   titulo VARCHAR(100) NOT NULL,
   ISBN VARCHAR(13) NOT NULL,
   genero VARCHAR(100) NOT NULL,
   precio DOUBLE NOT NULL,
   fechaPublicacion DATE NOT NULL,
   stock INT NOT NULL,
   CONSTRAINT fk_comic_tienda
   /*relaciones*/
); INSERT INTO comics (titulo) VALUES ('Jose');


CREATE TABLE dueños (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(15) NOT NULL
); INSERT INTO dueños (nombre) VALUES ('Benjamin');


CREATE TABLE editoriales (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(15) NOT NULL
); INSERT INTO editoriales (nombre) VALUES ('Anagrama');


CREATE TABLE empleados (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre VARCHAR(100) NOT NULL,
   apellido VARCHAR(100) NOT NULL,
   edad INT NOT NULL,
   rut INT(8) NOT NULL,
   dv VARCHAR(1) NOT NULL,
   correo VARCHAR(40) NOT NULL,
   telefono INT(9) NOT NULL
); INSERT INTO empleados (nombre, apellido, edad, rut, dv, correo, telefono) VALUES ('Jordan','Torres',21, 12332456,'5','JordanTorres@gmail.com', 789654789);


CREATE TABLE envios (
   id INT AUTO_INCREMENT PRIMARY KEY,
   fechaSalida Date,
   fechaEntrega Date NOT NULL,
   /*fk*/,
   tipoEnvio VARCHAR(20),
   sucursal VARCHAR(20) 
); INSERT INTO envios (fechaSalida, fechaEntrega, tipoEnvio, sucursal) VALUES ('11-10-2025','20-12-2026','Blue Express', 'La Farfana 1150');


CREATE TABLE pago (
   id_pago INT AUTO_INCREMENT PRIMARY KEY,
   /*FK*/
   descripcion VARCHAR(100) NOT NULL,
   montoTotal INT(1000) NOT NULL,
   /*FK*/
); INSERT INTO pago (descripcion, montoTotal) VALUES ('El pago por este comics es de 1000, este pago se realizo desde la tarjeta tanto tanto');


CREATE TABLE tienda (
   id INT AUTO_INCREMENT PRIMARY KEY,
   nombre_tienda VARCHAR(100) NOT NULL,
   direccion_tienda VARCHAR(200) NOT NULL,
   /*fk*/
); INSERT INTO tienda (nombre_tienda, direccion_tienda) VALUES ('LOS CRACKS', 'la Farfana 1150');