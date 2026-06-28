CREATE TABLE categoria (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    nombre_categoria VARCHAR(100) NOT NULL
);

CREATE TABLE editorial (
    id_editorial INT AUTO_INCREMENT PRIMARY KEY,
    nombre_editorial VARCHAR(100) NOT NULL
);

CREATE TABLE autor (
    id_autor INT AUTO_INCREMENT PRIMARY KEY,
    nombre_autor VARCHAR(100) NOT NULL
);

CREATE TABLE comic (
    id_comic INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descripcion TEXT,
    isbn VARCHAR(50),
    fecha_publicacion DATE,
    genero VARCHAR(50),
    precio DECIMAL(10,2) NOT NULL,
    id_editorial INT,
    CONSTRAINT fk_comic_editorial FOREIGN KEY (id_editorial) REFERENCES editorial(id_editorial)
);


CREATE TABLE categoria_comic (
    id_categoria INT,
    id_comic INT,
    PRIMARY KEY (id_categoria, id_comic),
    CONSTRAINT fk_cat_comic_categoria FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria) ON DELETE CASCADE,
    CONSTRAINT fk_cat_comic_comic FOREIGN KEY (id_comic) REFERENCES comic(id_comic) ON DELETE CASCADE
);

CREATE TABLE autor_comic (
    id_autor INT,
    id_comic INT,
    PRIMARY KEY (id_autor, id_comic),
    CONSTRAINT fk_aut_comic_autor FOREIGN KEY (id_autor) REFERENCES autor(id_autor) ON DELETE CASCADE,
    CONSTRAINT fk_aut_comic_comic FOREIGN KEY (id_comic) REFERENCES comic(id_comic) ON DELETE CASCADE
);

