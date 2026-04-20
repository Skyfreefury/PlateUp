-- ============================================================
--  DATOS DE EJEMPLO — restaurante_db
--  Archivo: init_data.sql
--
--  INSTRUCCIONES:
--    1. Ejecutar primero schema.sql para crear las tablas base.
--    2. Arrancar la app UNA VEZ con ddl-auto=update para que
--       Hibernate añada las columnas extra de las entidades.
--       (sesiones, usuarios, columnas nuevas en mesas/pedidos/etc.)
--    3. Parar la app y ejecutar este archivo.
--
--  USUARIO ADMIN: se crea automáticamente al arrancar la app.
--                 Credenciales: admin / admin123
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
TRUNCATE TABLE sesiones;

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
--    Determinan a qué estación va el ticket (cocina / barra).
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
  (1, 'María García',     '600 111 222', 'maria.garcia@gmail.com'),
  (2, 'Carlos López',     '600 333 444', 'carlos.lopez@gmail.com'),
  (3, 'Ana Martínez',     '600 555 666', 'ana.martinez@gmail.com'),
  (4, 'Pedro Sánchez',    '600 777 888', 'pedro.sanchez@gmail.com'),
  (5, 'Laura Fernández',  '600 999 000', 'laura.fernandez@gmail.com'),
  (6, 'Sergio Ruiz',      '601 111 222', 'sergio.ruiz@gmail.com'),
  (7, 'Elena Torres',     '601 333 444', 'elena.torres@gmail.com'),
  (8, 'David Moreno',     '601 555 666', 'david.moreno@gmail.com');


-- ============================================================
-- 4. SESIONES DE CAJA
--    Una cerrada (ayer) y una abierta (hoy).
-- ============================================================
INSERT INTO sesiones (id, apertura, cierre, monto_inicial, estado) VALUES
  (1, '2025-04-19 09:00:00', '2025-04-19 23:45:00', 150.00, 'CERRADA'),
  (2, '2025-04-20 09:00:00', NULL,                   150.00, 'ABIERTA');


-- ============================================================
-- 5. MESAS
--    8 mesas: mix de estados, interior y terraza.
--    Mesas OCUPADAS: ya tienen cliente asignado.
--    Mesas RESERVADAS: tienen cliente + fecha + hora de reserva.
-- ============================================================
INSERT INTO mesas (id, numero, capacidad, estado, ubicacion, cliente_id, fecha_reserva, hora_reserva) VALUES
  (1, 1, 2, 'OCUPADA',   'Interior', 1,    NULL,         NULL),
  (2, 2, 4, 'LIBRE',     'Interior', NULL, NULL,         NULL),
  (3, 3, 4, 'RESERVADA', 'Interior', 2,    '2025-04-20', '21:00'),
  (4, 4, 6, 'LIBRE',     'Interior', NULL, NULL,         NULL),
  (5, 5, 2, 'OCUPADA',   'Terraza',  3,    NULL,         NULL),
  (6, 6, 4, 'LIBRE',     'Terraza',  NULL, NULL,         NULL),
  (7, 7, 8, 'RESERVADA', 'Terraza',  4,    '2025-04-21', '20:30'),
  (8, 8, 4, 'LIBRE',     'Terraza',  NULL, NULL,         NULL);


-- ============================================================
-- 6. PRODUCTOS (La Carta)
-- ============================================================
INSERT INTO productos (id, nombre, precio, descripcion, tipo_producto_id, tipo_comanda_id) VALUES
  -- Entrantes
  (1,  'Croquetas de Jamón Ibérico', 8.50,  'Croquetas caseras con jamón ibérico D.O.',               1, 1),
  (2,  'Ensalada César',             9.00,  'Lechuga romana, pollo, parmesano y salsa césar.',         1, 1),
  (3,  'Foie a la Plancha',          14.00, 'Foie micuit con mermelada de higos y brioche tostado.',   1, 1),
  (4,  'Gazpacho Andaluz',           6.50,  'Gazpacho frío de temporada con picatostes.',              1, 1),
  -- Principales
  (5,  'Solomillo de Ternera',       24.00, 'Solomillo al punto con patatas asadas y salsa de vino.', 2, 2),
  (6,  'Merluza al Horno',           19.50, 'Merluza gallega con verduras de temporada al horno.',    2, 2),
  (7,  'Risotto de Setas',           16.00, 'Risotto cremoso con setas silvestres y trufa negra.',     2, 2),
  (8,  'Secreto Ibérico',            21.00, 'Secreto ibérico a la brasa con chimichurri.',             2, 2),
  -- Postres
  (9,  'Tiramisú Casero',            7.00,  'Tiramisú tradicional con bizcocho y mascarpone.',         3, 3),
  (10, 'Tarta de Queso',             6.50,  'Tarta de queso al horno con coulis de frutos rojos.',     3, 3),
  (11, 'Crema Catalana',             5.50,  'Crema catalana flameada al momento.',                     3, 3),
  -- Bebidas
  (12, 'Agua Mineral',               2.00,  'Botella de agua mineral 50cl.',                           4, 4),
  (13, 'Vino Tinto Rioja',           5.50,  'Copa de vino tinto Rioja Reserva.',                       4, 4),
  (14, 'Vino Blanco Albariño',       5.50,  'Copa de vino blanco Albariño Rías Baixas.',               4, 4),
  (15, 'Cerveza Artesana',           3.50,  'Caña de cerveza artesana local.',                         4, 4),
  (16, 'Refresco',                   2.50,  'Coca-Cola, Fanta Naranja o Sprite.',                      4, 4),
  (17, 'Café Solo',                  1.80,  'Café espresso.',                                          4, 4);


-- ============================================================
-- 7. PEDIDOS
--    2 abiertos (sesión de hoy) + 2 cerrados (sesión de ayer).
-- ============================================================
INSERT INTO pedidos (id, mesa_id, cliente_id, sesion_id, fecha_hora, total, pago_efectivo, pago_tarjeta, numero_ticket, estado, mesa_anterior_id) VALUES
  -- Pedidos abiertos (sesión 2 — hoy)
  (1, 1, 1, 2, '2025-04-20 13:15:00', 52.50, NULL,  NULL,  1, 'ABIERTA', NULL),
  (2, 5, 3, 2, '2025-04-20 13:30:00', 34.50, NULL,  NULL,  2, 'ABIERTA', NULL),
  -- Pedidos cerrados (sesión 1 — ayer)
  (3, NULL, 5, 1, '2025-04-19 20:00:00', 89.50, 89.50, 0.00,  1, 'CERRADA', NULL),
  (4, NULL, 6, 1, '2025-04-19 21:15:00', 67.00, 0.00,  67.00, 2, 'CERRADA', NULL);


-- ============================================================
-- 8. COMANDAS
--    Asociadas a los pedidos abiertos.
--    Estado: EN_PREPARACION | SERVIDA
-- ============================================================
INSERT INTO comandas (id, pedido_id, tipo_comanda_id, nombre_plato, cantidad, estado, comentarios, creado_en) VALUES
  -- Pedido 1 (Mesa 1 — María García): entrantes servidos, principales en cocina
  (1, 1, 1, 'Croquetas de Jamón Ibérico', 2, 'SERVIDA',        NULL,             '2025-04-20 13:17:00'),
  (2, 1, 4, 'Vino Tinto Rioja',           2, 'SERVIDA',        NULL,             '2025-04-20 13:17:00'),
  (3, 1, 2, 'Solomillo de Ternera',       1, 'EN_PREPARACION', 'Poco hecho',     '2025-04-20 13:40:00'),
  (4, 1, 2, 'Merluza al Horno',           1, 'EN_PREPARACION', NULL,             '2025-04-20 13:40:00'),
  -- Pedido 2 (Mesa 5 — Ana Martínez): entrantes servidos, bebidas servidas
  (5, 2, 1, 'Ensalada César',             2, 'SERVIDA',        NULL,             '2025-04-20 13:32:00'),
  (6, 2, 4, 'Agua Mineral',               2, 'SERVIDA',        NULL,             '2025-04-20 13:32:00'),
  (7, 2, 2, 'Risotto de Setas',           1, 'EN_PREPARACION', 'Sin trufa',      '2025-04-20 13:50:00'),
  (8, 2, 2, 'Secreto Ibérico',            1, 'EN_PREPARACION', NULL,             '2025-04-20 13:50:00'),
  -- Pedido 3 (cerrado ayer)
  (9,  3, 1, 'Foie a la Plancha',         2, 'SERVIDA', NULL, '2025-04-19 20:05:00'),
  (10, 3, 2, 'Solomillo de Ternera',      2, 'SERVIDA', NULL, '2025-04-19 20:25:00'),
  (11, 3, 3, 'Tiramisú Casero',           2, 'SERVIDA', NULL, '2025-04-19 21:30:00'),
  (12, 3, 4, 'Vino Tinto Rioja',          2, 'SERVIDA', NULL, '2025-04-19 20:05:00'),
  -- Pedido 4 (cerrado ayer)
  (13, 4, 1, 'Croquetas de Jamón Ibérico',2, 'SERVIDA', NULL, '2025-04-19 21:20:00'),
  (14, 4, 2, 'Secreto Ibérico',           2, 'SERVIDA', NULL, '2025-04-19 21:35:00'),
  (15, 4, 4, 'Cerveza Artesana',          2, 'SERVIDA', NULL, '2025-04-19 21:20:00');


-- ============================================================
-- 9. LÍNEAS DE PRODUCTO
--    precio × cantidad = subtotal
-- ============================================================
INSERT INTO lineas_producto (id, comanda_id, producto_id, cantidad, subtotal) VALUES
  -- Comandas del pedido 1
  (1,  1, 1,  2, 17.00),   -- Croquetas ×2 → 8.50×2
  (2,  2, 13, 2, 11.00),   -- Vino Rioja ×2 → 5.50×2
  (3,  3, 5,  1, 24.00),   -- Solomillo ×1
  (4,  4, 6,  1, 19.50),   -- Merluza ×1
  -- Comandas del pedido 2
  (5,  5, 2,  2, 18.00),   -- Ensalada César ×2 → 9.00×2
  (6,  6, 12, 2, 4.00),    -- Agua ×2 → 2.00×2
  (7,  7, 7,  1, 16.00),   -- Risotto ×1
  (8,  8, 8,  1, 21.00),   -- Secreto ×1
  -- Comandas del pedido 3 (cerrado)
  (9,  9,  3,  2, 28.00),  -- Foie ×2 → 14.00×2
  (10, 10, 5,  2, 48.00),  -- Solomillo ×2 → 24.00×2
  (11, 11, 9,  2, 14.00),  -- Tiramisú ×2 → 7.00×2
  (12, 12, 13, 2, 11.00),  -- Vino ×2 → 5.50×2
  -- Comandas del pedido 4 (cerrado)
  (13, 13, 1,  2, 17.00),  -- Croquetas ×2
  (14, 14, 8,  2, 42.00),  -- Secreto ×2 → 21.00×2
  (15, 15, 15, 2,  7.00);  -- Cerveza ×2 → 3.50×2


-- ============================================================
-- VERIFICACIÓN RÁPIDA
-- ============================================================
SELECT 'tipos_producto'  AS tabla, COUNT(*) AS filas FROM tipos_producto
UNION ALL
SELECT 'tipos_comanda',  COUNT(*) FROM tipos_comanda
UNION ALL
SELECT 'clientes',       COUNT(*) FROM clientes
UNION ALL
SELECT 'sesiones',       COUNT(*) FROM sesiones
UNION ALL
SELECT 'mesas',          COUNT(*) FROM mesas
UNION ALL
SELECT 'productos',      COUNT(*) FROM productos
UNION ALL
SELECT 'pedidos',        COUNT(*) FROM pedidos
UNION ALL
SELECT 'comandas',       COUNT(*) FROM comandas
UNION ALL
SELECT 'lineas_producto',COUNT(*) FROM lineas_producto;
