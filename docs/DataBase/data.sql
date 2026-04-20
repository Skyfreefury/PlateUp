-- ============================================================
--  DATOS DE EJEMPLO — restaurante_db
--  Restaurante: La Taberna del Chef
--
--  Ejecutar DESPUÉS de init.sql
-- ============================================================

USE restaurante_db;

SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE lineas_producto;
TRUNCATE TABLE comandas;
TRUNCATE TABLE pedidos;
TRUNCATE TABLE productos;
TRUNCATE TABLE mesas;
TRUNCATE TABLE clientes;
TRUNCATE TABLE tipos_comanda;
TRUNCATE TABLE tipos_producto;

SET FOREIGN_KEY_CHECKS = 1;


-- ============================================================
-- 1. TIPOS DE PRODUCTO
-- ============================================================
INSERT INTO tipos_producto (id, nombre) VALUES
  (1, 'Entrante'),
  (2, 'Principal'),
  (3, 'Postre'),
  (4, 'Bebida');


-- ============================================================
-- 2. TIPOS DE COMANDA
-- ============================================================
INSERT INTO tipos_comanda (id, nombre) VALUES
  (1, 'Entrantes'),
  (2, 'Principales'),
  (3, 'Postres'),
  (4, 'Bebidas');


-- ============================================================
-- 3. CLIENTES
-- ============================================================
INSERT INTO clientes (id, nombre, telefono, email) VALUES
  (1,  'María García',      '600 111 222', 'maria.garcia@gmail.com'),
  (2,  'Carlos López',      '600 333 444', 'carlos.lopez@gmail.com'),
  (3,  'Ana Martínez',      '600 555 666', 'ana.martinez@gmail.com'),
  (4,  'Pedro Sánchez',     '600 777 888', 'pedro.sanchez@gmail.com'),
  (5,  'Laura Fernández',   '600 999 000', 'laura.fernandez@gmail.com'),
  (6,  'Sergio Ruiz',       '601 111 222', 'sergio.ruiz@gmail.com'),
  (7,  'Elena Torres',      '601 333 444', 'elena.torres@gmail.com'),
  (8,  'David Moreno',      '601 555 666', 'david.moreno@gmail.com'),
  (9,  'Isabel Navarro',    '601 777 888', 'isabel.navarro@gmail.com'),
  (10, 'Javier Castillo',   '601 999 000', 'javier.castillo@gmail.com'),
  (11, 'Lucía Vega',        '602 111 222', 'lucia.vega@gmail.com'),
  (12, 'Marcos Delgado',    '602 333 444', 'marcos.delgado@gmail.com');


-- ============================================================
-- 4. MESAS
--    10 mesas: LIBRE / OCUPADA / RESERVADA
--    Interior y Terraza
-- ============================================================
INSERT INTO mesas (id, numero, capacidad, estado, ubicacion, cliente_id, fecha_reserva, hora_reserva) VALUES
  (1,  1,  2,  'OCUPADA',   'Interior', 1,    NULL,         NULL),
  (2,  2,  4,  'LIBRE',     'Interior', NULL, NULL,         NULL),
  (3,  3,  4,  'RESERVADA', 'Interior', 2,    '2026-04-20', '21:00'),
  (4,  4,  6,  'LIBRE',     'Interior', NULL, NULL,         NULL),
  (5,  5,  2,  'OCUPADA',   'Interior', 3,    NULL,         NULL),
  (6,  6,  4,  'RESERVADA', 'Interior', 4,    '2026-04-20', '20:30'),
  (7,  7,  8,  'LIBRE',     'Terraza',  NULL, NULL,         NULL),
  (8,  8,  4,  'OCUPADA',   'Terraza',  5,    NULL,         NULL),
  (9,  9,  6,  'RESERVADA', 'Terraza',  6,    '2026-04-21', '13:00'),
  (10, 10, 2,  'LIBRE',     'Terraza',  NULL, NULL,         NULL);


-- ============================================================
-- 5. PRODUCTOS (La Carta)
-- ============================================================
INSERT INTO productos (id, nombre, precio, tipo_producto_id, tipo_comanda_id) VALUES
  -- Entrantes
  (1,  'Croquetas de Jamón Ibérico',   8.50,  1, 1),
  (2,  'Ensalada César',               9.00,  1, 1),
  (3,  'Foie a la Plancha',           14.00,  1, 1),
  (4,  'Gazpacho Andaluz',             6.50,  1, 1),
  (5,  'Tabla de Ibéricos',           12.00,  1, 1),
  (6,  'Pulpo a la Gallega',          13.50,  1, 1),
  -- Principales
  (7,  'Solomillo de Ternera',        24.00,  2, 2),
  (8,  'Merluza al Horno',            19.50,  2, 2),
  (9,  'Risotto de Setas',            16.00,  2, 2),
  (10, 'Secreto Ibérico a la Brasa',  21.00,  2, 2),
  (11, 'Paella Valenciana',           18.00,  2, 2),
  (12, 'Lubina a la Sal',             22.50,  2, 2),
  -- Postres
  (13, 'Tiramisú Casero',              7.00,  3, 3),
  (14, 'Tarta de Queso',               6.50,  3, 3),
  (15, 'Crema Catalana',               5.50,  3, 3),
  (16, 'Coulant de Chocolate',         6.00,  3, 3),
  -- Bebidas
  (17, 'Agua Mineral 50cl',            2.00,  4, 4),
  (18, 'Vino Tinto Rioja (copa)',       5.50,  4, 4),
  (19, 'Vino Blanco Albariño (copa)',   5.50,  4, 4),
  (20, 'Cerveza Artesana',             3.50,  4, 4),
  (21, 'Refresco',                     2.50,  4, 4),
  (22, 'Café Solo',                    1.80,  4, 4),
  (23, 'Botella Vino Tinto Rioja',    22.00,  4, 4),
  (24, 'Zumo Natural',                 3.00,  4, 4);


-- ============================================================
-- 6. PEDIDOS
--    3 abiertos (mesas ocupadas hoy) + 3 cerrados (ayer)
-- ============================================================
INSERT INTO pedidos (id, cliente_id, mesa_id, fecha_hora, total, estado) VALUES
  -- Abiertos (hoy)
  (1, 1, 1, '2026-04-20 13:15:00',  52.50, 'ABIERTO'),
  (2, 3, 5, '2026-04-20 13:30:00',  34.50, 'ABIERTO'),
  (3, 5, 8, '2026-04-20 14:00:00',  41.00, 'ABIERTO'),
  -- Cerrados (ayer)
  (4, 7, NULL, '2026-04-19 20:00:00', 89.50, 'CERRADO'),
  (5, 9, NULL, '2026-04-19 21:15:00', 67.00, 'CERRADO'),
  (6, 11,NULL, '2026-04-19 21:45:00', 55.30, 'CERRADO');


-- ============================================================
-- 7. COMANDAS
-- ============================================================
INSERT INTO comandas (id, pedido_id, fecha_hora, estado) VALUES
  -- Pedido 1 (Mesa 1 — María García)
  (1,  1, '2026-04-20 13:17:00', 'SERVIDA'),
  (2,  1, '2026-04-20 13:17:00', 'SERVIDA'),
  (3,  1, '2026-04-20 13:40:00', 'EN_PREPARACION'),
  (4,  1, '2026-04-20 13:40:00', 'EN_PREPARACION'),
  -- Pedido 2 (Mesa 5 — Ana Martínez)
  (5,  2, '2026-04-20 13:32:00', 'SERVIDA'),
  (6,  2, '2026-04-20 13:32:00', 'SERVIDA'),
  (7,  2, '2026-04-20 13:50:00', 'EN_PREPARACION'),
  -- Pedido 3 (Mesa 8 — Laura Fernández)
  (8,  3, '2026-04-20 14:02:00', 'SERVIDA'),
  (9,  3, '2026-04-20 14:20:00', 'EN_PREPARACION'),
  -- Pedido 4 (cerrado ayer)
  (10, 4, '2026-04-19 20:05:00', 'SERVIDA'),
  (11, 4, '2026-04-19 20:25:00', 'SERVIDA'),
  (12, 4, '2026-04-19 21:30:00', 'SERVIDA'),
  -- Pedido 5 (cerrado ayer)
  (13, 5, '2026-04-19 21:20:00', 'SERVIDA'),
  (14, 5, '2026-04-19 21:35:00', 'SERVIDA'),
  -- Pedido 6 (cerrado ayer)
  (15, 6, '2026-04-19 21:50:00', 'SERVIDA'),
  (16, 6, '2026-04-19 22:05:00', 'SERVIDA');


-- ============================================================
-- 8. LÍNEAS DE PRODUCTO
-- ============================================================
INSERT INTO lineas_producto (id, comanda_id, producto_id, cantidad, subtotal) VALUES
  -- Pedido 1: Croquetas, Vino, Solomillo, Merluza
  (1,  1,  1,  2, 17.00),   -- Croquetas ×2
  (2,  2,  18, 2, 11.00),   -- Vino Tinto ×2
  (3,  3,  7,  1, 24.00),   -- Solomillo ×1
  (4,  4,  8,  1, 19.50),   -- Merluza ×1
  -- Pedido 2: Ensalada, Agua, Risotto
  (5,  5,  2,  2, 18.00),   -- Ensalada César ×2
  (6,  6,  17, 2,  4.00),   -- Agua ×2
  (7,  7,  9,  2, 32.00),   -- Risotto ×2
  -- Pedido 3: Tabla Ibéricos, Refresco, Paella
  (8,  8,  5,  1, 12.00),   -- Tabla Ibéricos ×1
  (9,  8,  21, 2,  5.00),   -- Refresco ×2
  (10, 9,  11, 2, 36.00),   -- Paella ×2
  -- Pedido 4 (cerrado): Foie, Lubina, Tiramisú, Vino Botella
  (11, 10, 3,  2, 28.00),   -- Foie ×2
  (12, 11, 12, 2, 45.00),   -- Lubina ×2
  (13, 12, 13, 2, 14.00),   -- Tiramisú ×2
  (14, 12, 23, 1, 22.00),   -- Botella Vino ×1
  -- Pedido 5 (cerrado): Croquetas, Secreto, Cerveza
  (15, 13, 1,  2, 17.00),   -- Croquetas ×2
  (16, 14, 10, 2, 42.00),   -- Secreto Ibérico ×2
  (17, 14, 20, 2,  7.00),   -- Cerveza ×2
  -- Pedido 6 (cerrado): Gazpacho, Merluza, Tarta Queso, Café
  (18, 15, 4,  2, 13.00),   -- Gazpacho ×2
  (19, 16, 8,  2, 39.00),   -- Merluza ×2
  (20, 16, 14, 2, 13.00),   -- Tarta Queso ×2
  (21, 16, 22, 2,  3.60);   -- Café ×2


-- ============================================================
-- VERIFICACIÓN RÁPIDA
-- ============================================================
SELECT 'tipos_producto'   AS tabla, COUNT(*) AS filas FROM tipos_producto
UNION ALL SELECT 'tipos_comanda',   COUNT(*) FROM tipos_comanda
UNION ALL SELECT 'clientes',        COUNT(*) FROM clientes
UNION ALL SELECT 'mesas',           COUNT(*) FROM mesas
UNION ALL SELECT 'productos',       COUNT(*) FROM productos
UNION ALL SELECT 'pedidos',         COUNT(*) FROM pedidos
UNION ALL SELECT 'comandas',        COUNT(*) FROM comandas
UNION ALL SELECT 'lineas_producto', COUNT(*) FROM lineas_producto;
