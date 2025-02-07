-- Crear la base de datos
CREATE DATABASE Authors;

-- Usar la base de datos
USE Authors;

-- Crear la tabla 'author'
CREATE TABLE author (
    id INT AUTO_INCREMENT PRIMARY KEY,          -- ID único para cada autor
    first_name VARCHAR(100) NOT NULL,           -- Nombre del autor
    last_name VARCHAR(100) NOT NULL,            -- Apellido del autor
    birth_date DATE,                            -- Fecha de nacimiento del autor
    phone VARCHAR(15),                          -- Teléfono del autor
    email VARCHAR(100),                         -- Email del autor
    UNIQUE (first_name, last_name)              -- Para evitar autores con el mismo nombre y apellido
);

-- Crear la tabla 'literarygenre'
CREATE TABLE literarygenre (
    id INT AUTO_INCREMENT PRIMARY KEY,          -- ID único para cada género literario
    name VARCHAR(100) NOT NULL,                 -- Nombre del género literario
    description TEXT                            -- Descripción del género literario
);

-- Relacionar las tablas 'author' y 'literarygenre' mediante una tabla intermedia
-- Para representar la relación muchos a muchos entre autores y géneros literarios.
CREATE TABLE author_literarygenre (
    author_id INT,                              -- ID del autor
    genre_id INT,                               -- ID del género literario
    PRIMARY KEY (author_id, genre_id),          -- Combinación única de autor y género
    FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE,  -- Relación con 'author'
    FOREIGN KEY (genre_id) REFERENCES literarygenre(id) ON DELETE CASCADE  -- Relación con 'literarygenre'
);
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY '1234';
