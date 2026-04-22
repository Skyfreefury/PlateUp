-- ============================================================
--  DATOS REALISTAS — restaurante_db
--  Restaurante: La Taberna del Chef
--  Escenario: Martes 22 abril 2026 — Servicio de mediodía en curso
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
-- 3. CLIENTES  (18 clientes habituales y nuevos)
-- ============================================================
INSERT INTO clientes (id, nombre, telefono, email) VALUES
  (1,  'María García',       '600 111 222', 'maria.garcia@gmail.com'),
  (2,  'Carlos López',       '600 333 444', 'carlos.lopez@gmail.com'),
  (3,  'Ana Martínez',       '600 555 666', 'ana.martinez@gmail.com'),
  (4,  'Pedro Sánchez',      '600 777 888', 'pedro.sanchez@gmail.com'),
  (5,  'Laura Fernández',    '600 999 000', 'laura.fernandez@gmail.com'),
  (6,  'Sergio Ruiz',        '601 111 222', 'sergio.ruiz@gmail.com'),
  (7,  'Elena Torres',       '601 333 444', 'elena.torres@gmail.com'),
  (8,  'David Moreno',       '601 555 666', 'david.moreno@gmail.com'),
  (9,  'Isabel Navarro',     '601 777 888', 'isabel.navarro@gmail.com'),
  (10, 'Javier Castillo',    '601 999 000', 'javier.castillo@gmail.com'),
  (11, 'Lucía Vega',         '602 111 222', 'lucia.vega@gmail.com'),
  (12, 'Marcos Delgado',     '602 333 444', 'marcos.delgado@gmail.com'),
  (13, 'Sofía Herrera',      '602 555 666', 'sofia.herrera@gmail.com'),
  (14, 'Alberto Jiménez',    '602 777 888', 'alberto.jimenez@gmail.com'),
  (15, 'Carmen Blanco',      '602 999 000', 'carmen.blanco@gmail.com'),
  (16, 'Roberto Iglesias',   '603 111 222', 'roberto.iglesias@gmail.com'),
  (17, 'Patricia Molina',    '603 333 444', 'patricia.molina@gmail.com'),
  (18, 'Fernando Castro',    '603 555 666', 'fernando.castro@gmail.com');


-- ============================================================
-- 4. MESAS  (12 mesas — 6 ocupadas, 2 reservadas, 4 libres)
--    Interior y Terraza — servicio mediodía en curso
-- ============================================================
INSERT INTO mesas (id, numero, capacidad, estado, ubicacion, cliente_id, fecha_reserva, hora_reserva) VALUES
  (1,  1,  2, 'OCUPADA',   'Interior', 1,    NULL,         NULL),
  (2,  2,  4, 'OCUPADA',   'Interior', 2,    NULL,         NULL),
  (3,  3,  4, 'RESERVADA', 'Interior', 3,    '2026-04-22', '21:00'),
  (4,  4,  6, 'OCUPADA',   'Interior', 4,    NULL,         NULL),
  (5,  5,  2, 'LIBRE',     'Interior', NULL, NULL,         NULL),
  (6,  6,  4, 'OCUPADA',   'Interior', 5,    NULL,         NULL),
  (7,  7,  8, 'RESERVADA', 'Terraza',  6,    '2026-04-22', '21:30'),
  (8,  8,  4, 'OCUPADA',   'Terraza',  7,    NULL,         NULL),
  (9,  9,  6, 'LIBRE',     'Terraza',  NULL, NULL,         NULL),
  (10, 10, 2, 'LIBRE',     'Terraza',  NULL, NULL,         NULL),
  (11, 11, 4, 'OCUPADA',   'Interior', 8,    NULL,         NULL),
  (12, 12, 4, 'LIBRE',     'Terraza',  NULL, NULL,         NULL);


-- ============================================================
-- 5. PRODUCTOS — La Carta completa
-- ============================================================
INSERT INTO productos (id, nombre, precio, tipo_producto_id, tipo_comanda_id) VALUES
  -- Entrantes (tipo_producto=1, tipo_comanda=1)
  (1,  'Croquetas de Jamón Ibérico',                  8.50,  1, 1),
  (2,  'Ensalada César con Pollo',                   10.50,  1, 1),
  (3,  'Foie a la Plancha con Mermelada de Higos',   15.00,  1, 1),
  (4,  'Gazpacho Andaluz',                            6.50,  1, 1),
  (5,  'Tabla de Ibéricos Selectos',                 14.00,  1, 1),
  (6,  'Pulpo a la Gallega',                         16.50,  1, 1),
  (7,  'Patatas Bravas',                              6.00,  1, 1),
  (8,  'Burrata con Tomate y Albahaca',              11.00,  1, 1),
  -- Principales (tipo_producto=2, tipo_comanda=2)
  (9,  'Solomillo de Ternera con Patatas al Romero', 26.00,  2, 2),
  (10, 'Merluza al Horno con Verduras',              20.00,  2, 2),
  (11, 'Risotto de Setas y Trufa',                   18.00,  2, 2),
  (12, 'Secreto Ibérico a la Brasa',                 22.00,  2, 2),
  (13, 'Paella Valenciana (por persona)',             19.00,  2, 2),
  (14, 'Entrecot de Angus con Salsa de Pimienta',    28.00,  2, 2),
  (15, 'Bacalao al Pil Pil',                         23.00,  2, 2),
  (16, 'Pollo de Corral al Horno',                   17.00,  2, 2),
  -- Postres (tipo_producto=3, tipo_comanda=3)
  (17, 'Tiramisú Casero',                             7.00,  3, 3),
  (18, 'Tarta de Queso con Frutos Rojos',             7.50,  3, 3),
  (19, 'Crema Catalana',                              5.50,  3, 3),
  (20, 'Coulant de Chocolate con Helado de Vainilla', 7.00,  3, 3),
  (21, 'Helado Artesano (3 bolas)',                   5.00,  3, 3),
  -- Bebidas (tipo_producto=4, tipo_comanda=4)
  (22, 'Agua Mineral 50cl',                           2.00,  4, 4),
  (23, 'Agua Mineral 1L',                             3.50,  4, 4),
  (24, 'Vino Tinto Rioja (copa)',                     5.50,  4, 4),
  (25, 'Vino Blanco Albariño (copa)',                 5.50,  4, 4),
  (26, 'Cerveza Artesana 33cl',                       3.50,  4, 4),
  (27, 'Refresco',                                    2.50,  4, 4),
  (28, 'Café Solo',                                   1.80,  4, 4),
  (29, 'Cortado',                                     1.80,  4, 4),
  (30, 'Botella Vino Tinto Rioja Reserva',           28.00,  4, 4),
  (31, 'Botella Vino Blanco Albariño',               22.00,  4, 4),
  (32, 'Zumo Natural de Naranja',                     3.50,  4, 4);


-- ============================================================
-- 6. PEDIDOS
--    6 ABIERTOS (servicio mediodía en curso)
--    8 CERRADOS (días anteriores — datos históricos)
-- ============================================================
INSERT INTO pedidos (id, cliente_id, mesa_id, fecha_hora, total, estado) VALUES
  -- ── Mediodía 22 abr 2026 — EN CURSO ──────────────────────
  (1,  1, 1,  '2026-04-22 13:10:00',  75.50, 'ABIERTO'),   -- Mesa 1: María (2 pax)
  (2,  2, 2,  '2026-04-22 13:00:00', 195.00, 'ABIERTO'),   -- Mesa 2: Carlos (4 pax)
  (3,  4, 4,  '2026-04-22 13:30:00', 121.50, 'ABIERTO'),   -- Mesa 4: Pedro  (4 pax, familia)
  (4,  5, 6,  '2026-04-22 13:45:00',  84.00, 'ABIERTO'),   -- Mesa 6: Laura  (2 pax)
  (5,  7, 8,  '2026-04-22 14:00:00',  37.50, 'ABIERTO'),   -- Mesa 8: Elena  (3 pax)
  (6,  8, 11, '2026-04-22 14:15:00',  21.50, 'ABIERTO'),   -- Mesa 11: David (2 pax, recién sentados)
  -- ── Noche 21 abr 2026 ────────────────────────────────────
  (7,  9,  NULL, '2026-04-21 21:00:00',  88.80, 'CERRADO'),
  (8,  10, NULL, '2026-04-21 21:30:00', 178.00, 'CERRADO'),
  (9,  11, NULL, '2026-04-21 22:00:00',  50.60, 'CERRADO'),
  -- ── Mediodía 21 abr 2026 ─────────────────────────────────
  (10, 12, NULL, '2026-04-21 13:00:00',  35.30, 'CERRADO'),
  (11, 13, NULL, '2026-04-21 13:30:00',  76.00, 'CERRADO'),
  -- ── Noche 20 abr 2026 ────────────────────────────────────
  (12, 14, NULL, '2026-04-20 20:30:00', 204.40, 'CERRADO'),
  (13, 15, NULL, '2026-04-20 21:00:00',  82.60, 'CERRADO'),
  (14, 16, NULL, '2026-04-20 21:30:00', 131.40, 'CERRADO');


-- ============================================================
-- 7. COMANDAS
-- ============================================================
INSERT INTO comandas (id, pedido_id, fecha_hora, estado) VALUES
  -- ── Pedido 1 — Mesa 1, María ─────────────────────────────
  (1,  1, '2026-04-22 13:12:00', 'SERVIDA'),         -- bebidas
  (2,  1, '2026-04-22 13:14:00', 'SERVIDA'),         -- entrantes
  (3,  1, '2026-04-22 13:35:00', 'EN_PREPARACION'),  -- principales
  -- ── Pedido 2 — Mesa 2, Carlos ────────────────────────────
  (4,  2, '2026-04-22 13:02:00', 'SERVIDA'),         -- bebidas
  (5,  2, '2026-04-22 13:05:00', 'SERVIDA'),         -- entrantes
  (6,  2, '2026-04-22 13:25:00', 'SERVIDA'),         -- principales
  (7,  2, '2026-04-22 14:10:00', 'EN_PREPARACION'),  -- postres
  -- ── Pedido 3 — Mesa 4, Pedro ─────────────────────────────
  (8,  3, '2026-04-22 13:32:00', 'SERVIDA'),         -- bebidas
  (9,  3, '2026-04-22 13:35:00', 'SERVIDA'),         -- entrantes
  (10, 3, '2026-04-22 14:00:00', 'EN_PREPARACION'),  -- principales
  -- ── Pedido 4 — Mesa 6, Laura ─────────────────────────────
  (11, 4, '2026-04-22 13:47:00', 'SERVIDA'),         -- bebidas + entrante
  (12, 4, '2026-04-22 14:05:00', 'EN_PREPARACION'),  -- principales
  -- ── Pedido 5 — Mesa 8, Elena ─────────────────────────────
  (13, 5, '2026-04-22 14:02:00', 'SERVIDA'),         -- bebidas
  (14, 5, '2026-04-22 14:05:00', 'EN_PREPARACION'),  -- entrantes
  -- ── Pedido 6 — Mesa 11, David ────────────────────────────
  (15, 6, '2026-04-22 14:17:00', 'EN_PREPARACION'),  -- bebidas + entrante
  -- ── Pedido 7 — Noche 21 abr ──────────────────────────────
  (16, 7, '2026-04-21 21:05:00', 'SERVIDA'),
  (17, 7, '2026-04-21 21:35:00', 'SERVIDA'),
  -- ── Pedido 8 — Noche 21 abr ──────────────────────────────
  (18, 8, '2026-04-21 21:32:00', 'SERVIDA'),
  (19, 8, '2026-04-21 22:10:00', 'SERVIDA'),
  -- ── Pedido 9 — Noche 21 abr ──────────────────────────────
  (20, 9, '2026-04-21 22:02:00', 'SERVIDA'),
  (21, 9, '2026-04-21 22:30:00', 'SERVIDA'),
  -- ── Pedido 10 — Mediodía 21 abr ──────────────────────────
  (22, 10, '2026-04-21 13:05:00', 'SERVIDA'),
  -- ── Pedido 11 — Mediodía 21 abr ──────────────────────────
  (23, 11, '2026-04-21 13:32:00', 'SERVIDA'),
  (24, 11, '2026-04-21 14:00:00', 'SERVIDA'),
  -- ── Pedido 12 — Noche 20 abr ─────────────────────────────
  (25, 12, '2026-04-20 20:35:00', 'SERVIDA'),
  (26, 12, '2026-04-20 21:10:00', 'SERVIDA'),
  -- ── Pedido 13 — Noche 20 abr ─────────────────────────────
  (27, 13, '2026-04-20 21:02:00', 'SERVIDA'),
  (28, 13, '2026-04-20 21:40:00', 'SERVIDA'),
  -- ── Pedido 14 — Noche 20 abr ─────────────────────────────
  (29, 14, '2026-04-20 21:33:00', 'SERVIDA'),
  (30, 14, '2026-04-20 22:05:00', 'SERVIDA');


-- ============================================================
-- 8. LÍNEAS DE PRODUCTO
--    Subtotales = precio × cantidad
-- ============================================================
INSERT INTO lineas_producto (id, comanda_id, producto_id, cantidad, subtotal) VALUES

  -- ── C1: bebidas pedido 1 ──────────────────────────────────
  (1,  1, 23, 1,  3.50),   -- Agua 1L ×1
  (2,  1, 24, 2, 11.00),   -- Vino Tinto Rioja copa ×2

  -- ── C2: entrantes pedido 1 ───────────────────────────────
  (3,  2,  1, 1,  8.50),   -- Croquetas Jamón ×1
  (4,  2,  4, 1,  6.50),   -- Gazpacho ×1

  -- ── C3: principales pedido 1 ─────────────────────────────
  (5,  3,  9, 1, 26.00),   -- Solomillo ×1
  (6,  3, 10, 1, 20.00),   -- Merluza al Horno ×1

  -- ── C4: bebidas pedido 2 ──────────────────────────────────
  (7,  4, 30, 1, 28.00),   -- Botella Vino Tinto Reserva ×1
  (8,  4, 23, 2,  7.00),   -- Agua 1L ×2

  -- ── C5: entrantes pedido 2 ───────────────────────────────
  (9,  5,  5, 1, 14.00),   -- Tabla de Ibéricos ×1
  (10, 5,  6, 1, 16.50),   -- Pulpo a la Gallega ×1
  (11, 5,  8, 1, 11.00),   -- Burrata con Tomate ×1

  -- ── C6: principales pedido 2 ─────────────────────────────
  (12, 6,  9, 2, 52.00),   -- Solomillo ×2
  (13, 6, 12, 1, 22.00),   -- Secreto Ibérico ×1
  (14, 6, 11, 1, 18.00),   -- Risotto de Setas ×1

  -- ── C7: postres pedido 2 ─────────────────────────────────
  (15, 7, 17, 2, 14.00),   -- Tiramisú ×2
  (16, 7, 19, 1,  5.50),   -- Crema Catalana ×1
  (17, 7, 20, 1,  7.00),   -- Coulant de Chocolate ×1

  -- ── C8: bebidas pedido 3 ──────────────────────────────────
  (18, 8, 27, 2,  5.00),   -- Refresco ×2
  (19, 8, 26, 2,  7.00),   -- Cerveza Artesana ×2
  (20, 8, 22, 2,  4.00),   -- Agua 50cl ×2

  -- ── C9: entrantes pedido 3 ───────────────────────────────
  (21, 9,  1, 2, 17.00),   -- Croquetas Jamón ×2
  (22, 9,  7, 1,  6.00),   -- Patatas Bravas ×1
  (23, 9,  2, 1, 10.50),   -- Ensalada César ×1

  -- ── C10: principales pedido 3 ────────────────────────────
  (24, 10, 16, 2, 34.00),  -- Pollo de Corral ×2
  (25, 10, 13, 2, 38.00),  -- Paella Valenciana ×2

  -- ── C11: bebidas + entrante pedido 4 ─────────────────────
  (26, 11, 31, 1, 22.00),  -- Botella Vino Blanco Albariño ×1
  (27, 11,  8, 1, 11.00),  -- Burrata con Tomate ×1

  -- ── C12: principales pedido 4 ────────────────────────────
  (28, 12, 15, 1, 23.00),  -- Bacalao al Pil Pil ×1
  (29, 12, 14, 1, 28.00),  -- Entrecot de Angus ×1

  -- ── C13: bebidas pedido 5 ──────────────────────────────────
  (30, 13, 26, 2,  7.00),  -- Cerveza Artesana ×2
  (31, 13, 22, 1,  2.00),  -- Agua 50cl ×1

  -- ── C14: entrantes pedido 5 ───────────────────────────────
  (32, 14,  6, 1, 16.50),  -- Pulpo a la Gallega ×1
  (33, 14,  7, 2, 12.00),  -- Patatas Bravas ×2

  -- ── C15: bebidas + entrante pedido 6 ─────────────────────
  (34, 15, 25, 2, 11.00),  -- Vino Blanco Albariño copa ×2
  (35, 15,  2, 1, 10.50),  -- Ensalada César ×1

  -- ── C16: pedido 7 (Isabel, noche 21 abr) ─────────────────
  (36, 16,  3, 1, 15.00),  -- Foie a la Plancha ×1
  (37, 16,  2, 1, 10.50),  -- Ensalada César ×1
  (38, 16, 30, 1, 28.00),  -- Botella Vino Tinto ×1

  -- ── C17: pedido 7 continuación ───────────────────────────
  (39, 17,  9, 1, 26.00),  -- Solomillo ×1
  (40, 17, 18, 1,  7.50),  -- Tarta de Queso ×1
  (41, 17, 28, 1,  1.80),  -- Café Solo ×1

  -- ── C18: pedido 8 (Javier, noche 21 abr) ─────────────────
  (42, 18,  6, 2, 33.00),  -- Pulpo a la Gallega ×2
  (43, 18,  5, 1, 14.00),  -- Tabla de Ibéricos ×1
  (44, 18, 30, 2, 56.00),  -- Botella Vino Tinto ×2

  -- ── C19: pedido 8 continuación ───────────────────────────
  (45, 19, 12, 2, 44.00),  -- Secreto Ibérico ×2
  (46, 19, 10, 1, 20.00),  -- Merluza ×1
  (47, 19, 19, 2, 11.00),  -- Crema Catalana ×2

  -- ── C20: pedido 9 (Lucía, noche 21 abr) ──────────────────
  (48, 20, 26, 2,  7.00),  -- Cerveza ×2
  (49, 20,  1, 1,  8.50),  -- Croquetas ×1
  (50, 20,  4, 1,  6.50),  -- Gazpacho ×1

  -- ── C21: pedido 9 continuación ───────────────────────────
  (51, 21, 11, 1, 18.00),  -- Risotto de Setas ×1
  (52, 21, 17, 1,  7.00),  -- Tiramisú ×1
  (53, 21, 28, 2,  3.60),  -- Café Solo ×2

  -- ── C22: pedido 10 (Marcos, mediodía 21 abr) ─────────────
  (54, 22, 23, 1,  3.50),  -- Agua 1L ×1
  (55, 22,  7, 1,  6.00),  -- Patatas Bravas ×1
  (56, 22, 16, 1, 17.00),  -- Pollo de Corral ×1
  (57, 22, 20, 1,  7.00),  -- Coulant ×1
  (58, 22, 28, 1,  1.80),  -- Café Solo ×1

  -- ── C23: pedido 11 (Sofía, mediodía 21 abr) ──────────────
  (59, 23, 31, 1, 22.00),  -- Botella Albariño ×1
  (60, 23,  8, 1, 11.00),  -- Burrata ×1
  (61, 23,  3, 1, 15.00),  -- Foie a la Plancha ×1

  -- ── C24: pedido 11 continuación ──────────────────────────
  (62, 24, 15, 1, 23.00),  -- Bacalao al Pil Pil ×1
  (63, 24, 21, 1,  5.00),  -- Helado Artesano ×1

  -- ── C25: pedido 12 (Alberto, noche 20 abr — grupo grande) ─
  (64, 25, 30, 2, 56.00),  -- Botella Vino Tinto ×2
  (65, 25,  5, 1, 14.00),  -- Tabla de Ibéricos ×1
  (66, 25,  6, 2, 33.00),  -- Pulpo a la Gallega ×2

  -- ── C26: pedido 12 continuación ──────────────────────────
  (67, 26, 14, 2, 56.00),  -- Entrecot de Angus ×2
  (68, 26,  9, 1, 26.00),  -- Solomillo ×1
  (69, 26, 17, 2, 14.00),  -- Tiramisú ×2
  (70, 26, 28, 3,  5.40),  -- Café Solo ×3

  -- ── C27: pedido 13 (Carmen, noche 20 abr) ────────────────
  (71, 27, 27, 2,  5.00),  -- Refresco ×2
  (72, 27,  2, 2, 21.00),  -- Ensalada César ×2

  -- ── C28: pedido 13 continuación ──────────────────────────
  (73, 28, 13, 2, 38.00),  -- Paella Valenciana ×2
  (74, 28, 18, 2, 15.00),  -- Tarta de Queso ×2
  (75, 28, 29, 2,  3.60),  -- Cortado ×2

  -- ── C29: pedido 14 (Roberto, noche 20 abr) ───────────────
  (76, 29, 31, 1, 22.00),  -- Botella Albariño ×1
  (77, 29, 23, 2,  7.00),  -- Agua 1L ×2
  (78, 29,  1, 2, 17.00),  -- Croquetas ×2
  (79, 29,  8, 1, 11.00),  -- Burrata ×1

  -- ── C30: pedido 14 continuación ──────────────────────────
  (80, 30, 10, 2, 40.00),  -- Merluza al Horno ×2
  (81, 30, 11, 1, 18.00),  -- Risotto de Setas ×1
  (82, 30, 19, 2, 11.00),  -- Crema Catalana ×2
  (83, 30, 28, 3,  5.40);  -- Café Solo ×3


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
