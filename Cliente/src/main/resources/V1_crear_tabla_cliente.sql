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
); INSERT INTO clientes (nombre, apellido, edad, rut, dv, correo, telefono, direccion) 
    VALUES ('Carlos','Papu',20, 22345785,'K','carlos123@gmail.com',987654312, 'La farfana city');