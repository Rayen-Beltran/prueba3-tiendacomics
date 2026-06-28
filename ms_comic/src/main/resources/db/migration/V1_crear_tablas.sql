-- Tablas principales
CREATE TABLE comics (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(100) NOT NULL,
    isbn VARCHAR(13) NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    fecha_publicacion DATE,
    stock INT NOT NULL
);

CREATE TABLE categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL
);

CREATE TABLE editoriales (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE autores (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE tiendas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255) NOT NULL
);

-- Tablas de relación Many-to-Many
CREATE TABLE comic_editorial (
    id_comic INT NOT NULL,
    id_editorial INT NOT NULL,
    PRIMARY KEY (id_comic, id_editorial)
);

CREATE TABLE comic_categoria (
    id_comic INT NOT NULL,
    id_categoria INT NOT NULL,
    PRIMARY KEY (id_comic, id_categoria)
);

CREATE TABLE autor_comic (
    id_comic INT NOT NULL,
    id_autor INT NOT NULL,
    PRIMARY KEY (id_comic, id_autor)
);

CREATE TABLE comic_tienda (
    comic_id INT NOT NULL,
    tienda_id INT NOT NULL,
    PRIMARY KEY (comic_id, tienda_id)
);