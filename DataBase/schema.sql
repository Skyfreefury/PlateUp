-- Creación de la base de datos
CREATE DATABASE IF NOT EXISTS restaurante_db;
USE restaurante_db;

-- 1. Tabla Mesas
CREATE TABLE mesas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero INT NOT NULL UNIQUE,
    capacidad INT NOT NULL,
    estado VARCHAR(20) DEFAULT 'LIBRE' -- Estados: LIBRE, OCUPADA, RESERVADA
);

-- 2. Tabla Productos (Carta)
CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    descripcion VARCHAR(255),
    precio DECIMAL(10, 2) NOT NULL,
    categoria VARCHAR(50) NOT NULL -- Ej: Entrante, Principal, Bebida, Postre
);

-- 3. Tabla Comandas (Relación 1:N con Mesas)
CREATE TABLE comandas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mesa_id BIGINT NOT NULL,
    fecha_hora DATETIME DEFAULT CURRENT_TIMESTAMP,
    estado VARCHAR(20) DEFAULT 'EN_PREPARACION', -- Estados: EN_PREPARACION, SERVIDA, PAGADA
    total DECIMAL(10, 2) DEFAULT 0.00,
    CONSTRAINT fk_comanda_mesa FOREIGN KEY (mesa_id) REFERENCES mesas(id) ON DELETE CASCADE
);

-- 4. Tabla Lineas_Comanda (Tabla intermedia para relación N:M entre Comandas y Productos)
CREATE TABLE lineas_comanda (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    comanda_id BIGINT NOT NULL,
    producto_id BIGINT NOT NULL,
    cantidad INT NOT NULL DEFAULT 1,
    subtotal DECIMAL(10, 2) NOT NULL,
    CONSTRAINT fk_linea_comanda FOREIGN KEY (comanda_id) REFERENCES comandas(id) ON DELETE CASCADE,
    CONSTRAINT fk_linea_producto FOREIGN KEY (producto_id) REFERENCES productos(id) ON DELETE CASCADE
);