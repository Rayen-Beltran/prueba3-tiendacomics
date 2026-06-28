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
    id_cliente INT,
    id_envio INT,
    id_comic INT NOT NULL,    
    id_empleado INT NOT NULL,   
    id_tienda INT NOT NULL,     
    descripcion VARCHAR(255),
    monto_total DECIMAL(10,2) NOT NULL,
    nombre_tienda VARCHAR(100),
    direccion_tienda VARCHAR(255),
    direccion_cliente VARCHAR(255),
    CONSTRAINT fk_pago_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT fk_pago_envio FOREIGN KEY (id_envio) REFERENCES envio(id_envio)
);

CREATE TABLE envios (
    id_envio INT AUTO_INCREMENT PRIMARY KEY,
    fechaSalida DATE,
    fechaEntrega DATE NOT NULL,
    tipoEnvio VARCHAR(20),
    sucursal VARCHAR(20),
    id_pago INT
);