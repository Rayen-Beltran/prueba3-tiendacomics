CREATE TABLE clientes (
    id_cliente INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido VARCHAR(100) NOT NULL,
    edad INT NOT NULL,
    rut INT NOT NULL,
    dv VARCHAR(1) NOT NULL,
    correo VARCHAR(40) NOT NULL,
    telefono INT,
    direccion VARCHAR(100) NOT NULL
);

CREATE TABLE pago (
    id_pago INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    montoTotal INT NOT NULL
);

CREATE TABLE envios (
    id_envio INT AUTO_INCREMENT PRIMARY KEY,
    fechaSalida DATE,
    fechaEntrega DATE NOT NULL,
    tipoEnvio VARCHAR(20),
    sucursal VARCHAR(20),
    id_pago INT,
    FOREIGN KEY (id_pago) REFERENCES pago(id_pago)
);