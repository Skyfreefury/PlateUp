-- ============================================================
--  BASE DE DATOS: restaurante_db
--  Descripción:   Sistema de gestión de restaurante
--  Tablas:        8 (clientes, mesas, tipos_producto,
--                    tipos_comanda, productos, pedidos,
--                    comandas, lineas_producto)
-- ============================================================

DROP DATABASE IF EXISTS restaurante_db;
CREATE DATABASE restaurante_db;
USE restaurante_db;


-- ============================================================
-- 1. CLIENTES
--    Información de los clientes. Asociación opcional a pedidos.
-- ============================================================
CREATE TABLE clientes (
    id       BIGINT       AUTO_INCREMENT PRIMARY KEY,
    nombre   VARCHAR(100) NOT NULL,
    telefono VARCHAR(20),
    email    VARCHAR(100) UNIQUE
);


-- ============================================================
-- 2. MESAS
--    Mesas físicas del restaurante.
--    Estado: LIBRE | OCUPADA | RESERVADA
--    FK: cliente_id → ON DELETE SET NULL  (reserva o cliente activo)
-- ============================================================
CREATE TABLE mesas (
    id            BIGINT      AUTO_INCREMENT PRIMARY KEY,
    numero        INT         NOT NULL UNIQUE,
    capacidad     INT         NOT NULL,
    estado        VARCHAR(20) DEFAULT 'LIBRE',
    ubicacion     VARCHAR(100),
    cliente_id    BIGINT,
    fecha_reserva DATE,
    hora_reserva  VARCHAR(10),

    CONSTRAINT fk_mesa_cliente FOREIGN KEY (cliente_id)
        REFERENCES clientes(id) ON DELETE SET NULL
);


-- ============================================================
-- 3. TIPOS DE PRODUCTO
--    Categorías de carta (ej: Bebida, Plato Principal, Postre).
-- ============================================================
CREATE TABLE tipos_producto (
    id     BIGINT      AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);


-- ============================================================
-- 4. TIPOS DE COMANDA
--    Momentos del servicio (ej: Entrante, Plato Fuerte).
-- ============================================================
CREATE TABLE tipos_comanda (
    id     BIGINT      AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL UNIQUE
);


-- ============================================================
-- 5. PRODUCTOS  (La Carta)
--    Cada producto pertenece a un tipo_producto y opcionalmente
--    a un tipo_comanda.
--    FK: tipo_producto_id → ON DELETE CASCADE
--        tipo_comanda_id  → ON DELETE SET NULL
-- ============================================================
CREATE TABLE productos (
    id               BIGINT         AUTO_INCREMENT PRIMARY KEY,
    nombre           VARCHAR(100)   NOT NULL,
    precio           DECIMAL(10, 2) NOT NULL,
    tipo_producto_id BIGINT         NOT NULL,
    tipo_comanda_id  BIGINT,

    CONSTRAINT fk_producto_tipo    FOREIGN KEY (tipo_producto_id)
        REFERENCES tipos_producto(id) ON DELETE CASCADE,

    CONSTRAINT fk_producto_comanda FOREIGN KEY (tipo_comanda_id)
        REFERENCES tipos_comanda(id)  ON DELETE SET NULL
);


-- ============================================================
-- 6. PEDIDOS
--    Cuenta global de una visita. Agrupa todas las comandas
--    de una mesa / cliente.
--    Estado: ABIERTO | PAGADO | CERRADO
--    FK: cliente_id → ON DELETE SET NULL
--        mesa_id    → ON DELETE SET NULL
-- ============================================================
CREATE TABLE pedidos (
    id         BIGINT         AUTO_INCREMENT PRIMARY KEY,
    cliente_id BIGINT,
    mesa_id    BIGINT,
    fecha_hora DATETIME       DEFAULT CURRENT_TIMESTAMP,
    total      DECIMAL(10, 2) DEFAULT 0.00,
    estado     VARCHAR(20)    DEFAULT 'ABIERTO',

    CONSTRAINT fk_pedido_cliente FOREIGN KEY (cliente_id)
        REFERENCES clientes(id) ON DELETE SET NULL,

    CONSTRAINT fk_pedido_mesa   FOREIGN KEY (mesa_id)
        REFERENCES mesas(id)    ON DELETE SET NULL
);


-- ============================================================
-- 7. COMANDAS
--    Ticket enviado a cocina, asociado a un pedido.
--    Puede haber varias comandas por pedido.
--    Estado: EN_PREPARACION | SERVIDA
--    FK: pedido_id → ON DELETE CASCADE
-- ============================================================
CREATE TABLE comandas (
    id         BIGINT      AUTO_INCREMENT PRIMARY KEY,
    pedido_id  BIGINT      NOT NULL,
    fecha_hora DATETIME    DEFAULT CURRENT_TIMESTAMP,
    estado     VARCHAR(20) DEFAULT 'EN_PREPARACION',

    CONSTRAINT fk_comanda_pedido FOREIGN KEY (pedido_id)
        REFERENCES pedidos(id) ON DELETE CASCADE
);


-- ============================================================
-- 8. LINEAS DE PRODUCTO
--    Relación N:M entre comanda y producto.
--    Guarda precio en el momento del pedido (subtotal desacoplado).
--    FK: comanda_id  → ON DELETE CASCADE
--        producto_id → ON DELETE CASCADE
-- ============================================================
CREATE TABLE lineas_producto (
    id          BIGINT         AUTO_INCREMENT PRIMARY KEY,
    comanda_id  BIGINT         NOT NULL,
    producto_id BIGINT         NOT NULL,
    cantidad    INT            NOT NULL DEFAULT 1,
    subtotal    DECIMAL(10, 2) NOT NULL,

    CONSTRAINT fk_linea_comanda  FOREIGN KEY (comanda_id)
        REFERENCES comandas(id)  ON DELETE CASCADE,

    CONSTRAINT fk_linea_producto FOREIGN KEY (producto_id)
        REFERENCES productos(id) ON DELETE CASCADE
);

-- ============================================================
-- 9. Cambio de usuario
--    Cambio de usuario root al propio
-- ============================================================
CREATE USER 'plateup'@'localhost' IDENTIFIED BY 'admin123';
GRANT ALL PRIVILEGES ON restaurante_db.* TO 'plateup'@'localhost';
FLUSH PRIVILEGES;