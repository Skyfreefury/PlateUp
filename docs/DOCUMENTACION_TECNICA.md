# Documentación Técnica — PlateUP

## 1. Descripción del Proyecto

**PlateUP** es una aplicación web monolítica de gestión integral para restaurantes, desarrollada como proyecto final del ciclo DAW/DAM. Simula el sistema TPV (Terminal Punto de Venta) real de un restaurante de alta cocina llamado **Eclipse PlateUp**, permitiendo gestionar mesas, pedidos, comandas de cocina y barra, carta de productos, clientes y cierre de caja diario.

La aplicación tiene dos partes diferenciadas:
- **Web pública**: página de presentación del restaurante con carta, equipo y formulario de reserva.
- **Panel de gestión interno**: accesible solo tras autenticación, con todos los módulos operativos del restaurante.

---

## 2. Tecnologías Utilizadas

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java 17 |
| Framework backend | Spring Boot 3 |
| Persistencia | Spring Data JPA + Hibernate |
| Motor de BD | MySQL 8 |
| Seguridad | Spring Security |
| Motor de plantillas | Thymeleaf |
| Framework CSS | Bootstrap 5 |
| Control de versiones | Git / GitHub |
| Build | Maven |

---

## 3. Arquitectura

La aplicación sigue el patrón **MVC (Modelo–Vista–Controlador)** con separación estricta en capas:

```
Cliente (navegador)
        │
        ▼
  WebController          ← @Controller  — recibe peticiones HTTP, delega en Service, devuelve vista
        │
        ▼
    Service              ← @Service     — lógica de negocio, validaciones, reglas
        │
        ▼
   Repository (DAO)      ← @Repository  — acceso a datos via Spring Data JPA
        │
        ▼
     Model               ← @Entity      — entidades JPA mapeadas a tablas MySQL
        │
        ▼
    MySQL DB
```

### Paquetes principales

| Paquete | Responsabilidad |
|---------|----------------|
| `Model` | Entidades JPA (Cliente, Mesa, Pedido, Comanda, Producto, LineaProducto, Sesion, TipoComanda, TipoProducto, Usuario) |
| `Repository` | Interfaces Spring Data JPA (`JpaRepository`) |
| `Service` | Lógica de negocio, validaciones y manejo de errores |
| `WebController` | Controladores Thymeleaf MVC (vistas HTML) |
| `Security` | Configuración Spring Security y usuario por defecto |

---

## 4. Modelo de Base de Datos

### Tablas y relaciones

La base de datos `restaurante_db` contiene **8 tablas** con las siguientes relaciones:

```
clientes ──────────────────────────┬──────────────────────┐
                                   │ 0..1 (reserva)       │ 0..1 (pedido)
                                   ▼                      │
mesas ─────────────────────────────┘                      │
  (cliente_id, fecha_reserva, hora_reserva)               │
                               │                          │
                           1   ▼                          ▼
sesiones ──────────── pedidos (cuenta de mesa) ◄──────────┘
                           │
                           │ 1:N
                           ▼
                        comandas (platos marchados a cocina)
                           │
                           │ 1:N
                           ▼
                    lineas_producto ──── productos ──── tipos_producto
                                                  └──── tipos_comanda
```

### Columnas destacadas de `mesas`

| Columna | Tipo | Descripción |
|---------|------|-------------|
| `estado` | VARCHAR(20) | `LIBRE` \| `OCUPADA` \| `RESERVADA` |
| `ubicacion` | VARCHAR(100) | Zona del restaurante (Interior, Terraza…) |
| `cliente_id` | BIGINT (FK) | Cliente asignado (reserva activa o mesa ocupada) |
| `fecha_reserva` | DATE | Fecha de la reserva pública |
| `hora_reserva` | VARCHAR(10) | Hora de la reserva (ej. `21:00`) |

### Relaciones destacadas

- **Mesa → Cliente**: N:1 opcional. Una mesa puede tener un cliente asignado al reservar o al abrir pedido.
- **Mesa → Pedidos**: 1:N. Una mesa puede tener varios pedidos a lo largo del tiempo.
- **Pedido → Comandas**: 1:N. Un pedido agrupa múltiples comandas.
- **Comanda → LineasProducto**: 1:N. Cada comanda contiene líneas individuales de producto.
- **Producto → TipoProducto**: N:1. Los productos se agrupan por categoría (Entrante, Principal, Postre, Bebida).

### Consultas derivadas de `MesaDAO`

| Método | Propósito |
|--------|-----------|
| `existsByClienteIdAndFechaReservaAndEstado` | Detecta reserva duplicada al crear una nueva |
| `existsByClienteIdAndFechaReservaAndEstadoAndIdNot` | Detecta reserva duplicada al editar, excluyendo la propia mesa |

---

## 5. Módulos Funcionales

### 5.1 Autenticación
- Spring Security con formulario de login propio.
- Usuario administrador creado automáticamente al arrancar si no existe (`DataInitializer`).
- Rutas públicas: `/`, `/login`, `/reservas`, recursos estáticos.
- Rutas protegidas: todo el panel `/mesas`, `/pedidos`, `/comandas`, etc.

### 5.2 Gestión de Mesas
- CRUD completo de mesas con capacidad y estado.
- Sistema de reciclaje de IDs: el número de mesa coincide con su ID, reutilizando huecos al eliminar.
- Límite configurable de 20 mesas físicas.
- **Lógica de estado en vista**: el estado visible en la tabla se calcula en Thymeleaf según las reglas siguientes:

| Condición en BD | Estado mostrado |
|-----------------|----------------|
| `estado = RESERVADA` y `fechaReserva <= hoy` | 🟡 RESERVADA (badge interactivo) |
| `estado = RESERVADA` y `fechaReserva > hoy` | 🟢 LIBRE |
| `clienteId != null` y `estado != RESERVADA` | 🔴 OCUPADA |
| Resto de casos | valor real del campo `estado` |

- **Validaciones al guardar** (`MesaService.guardarMesa`):
  - Capacidad entre 1 y 20 comensales.
  - Si estado es `RESERVADA`: fecha obligatoria y no anterior a hoy; si la fecha es hoy, la hora no puede ser anterior a la hora actual.
  - Si estado es `RESERVADA` y tiene cliente asignado: el mismo cliente no puede tener otra mesa reservada para esa misma fecha (previene duplicados por día, no globales).

### 5.3 Gestión de Pedidos (Cuentas)
- Apertura y cierre de cuentas por mesa.
- Numeración correlativa de tickets por sesión de caja.
- Cobro diferenciado: efectivo o tarjeta.
- Generación de ticket imprimible.

### 5.4 Panel de Cocina y Barra
- Vista en tiempo real de comandas pendientes por tipo (Entrantes, Principales, Postres / Bebidas).
- Temporizador de espera por comanda.
- Marcado de platos como "Listo" con un clic.

### 5.5 Carta de Productos (TPV)
- CRUD de productos agrupados por categoría.
- Sistema de reciclaje de IDs con límite de 500 productos.

### 5.6 Gestión de Clientes
- Directorio de clientes con nombre, email y teléfono.
- CRUD completo con validación de campos.
- El **email es único** a nivel de directorio (una persona = un registro); esta restricción no impide que el mismo cliente tenga reservas en fechas distintas, ya que la unicidad por fecha se valida en la capa de mesas.

### 5.7 Gestión de Caja (Sesiones)
- Apertura de turno con fondo inicial de caja.
- KPIs en tiempo real: total ingresos, efectivo, tarjeta, efectivo en caja.
- Desglose de ventas por producto.
- Cierre de sesión con impresión de resumen.

### 5.8 Sistema de Reservas (Web Pública)
- Formulario público en `index.html` (sección `#reserva`) accesible sin autenticación.
- Endpoint `POST /reservas` (`ReservaWebController`) que:
  1. Busca la mesa `LIBRE` con capacidad suficiente y menor exceso (algoritmo best-fit).
  2. Crea un `Cliente` nuevo con nombre, teléfono y email del formulario.
  3. Asigna `cliente_id`, `fecha_reserva` y `hora_reserva` a la mesa y cambia su estado a `RESERVADA`.
- Devuelve JSON `{ exito, mesa, mensaje }` al frontend, que muestra un toast al cliente.
- Si no hay mesas disponibles, responde con `exito: false` y un mensaje de contacto telefónico.
- **Prevención de reservas duplicadas por cliente y día**: al asignar un cliente a una reserva, `MesaService` comprueba que ese cliente no tenga ya otra mesa en estado `RESERVADA` para la misma `fechaReserva`. La consulta excluye la propia mesa al editar para evitar falsos positivos.

---

## 6. Seguridad

- **Autenticación**: Spring Security con BCrypt para el hash de contraseñas.
- **Autorización**: todas las rutas del panel requieren autenticación. Las vistas usan `sec:authorize` de Thymeleaf Extras Security para mostrar/ocultar elementos según el estado de sesión.
- **CSRF**: habilitado por defecto en Spring Security (formularios incluyen token CSRF).
- **Validaciones de entrada**: anotaciones `@NotBlank` en entidades JPA + `try/catch` en capa Service.

---

## 7. Instrucciones de Despliegue

### Requisitos previos
- Java 17+
- MySQL 8+
- Maven 3.8+

### Pasos

**1. Crear la base de datos**
```sql
-- Ejecutar el script:
DataBase/schema.sql
```

**2. Configurar credenciales**

Editar `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurante_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
```

**3. Compilar y ejecutar**
```bash
cd PlateUp
mvn spring-boot:run
```

**4. Acceder a la aplicación**
```
http://localhost:8080
```

El usuario administrador por defecto se crea automáticamente al primer arranque. Credenciales configurables en `DataInitializer.java`.

---

## 8. Diagrama de Clases

El diagrama de clases completo se encuentra en el archivo:

```
DiagramaClasePlateup.drawio
```

Incluye todas las entidades del modelo con sus atributos, relaciones y cardinalidades.

---

## 9. Estructura del Repositorio

```
PlateUP/
├── DataBase/
│   └── schema.sql                  ← Script de creación de BD
├── DiagramaClasePlateup.drawio     ← Diagrama de clases
├── PlateUp/
│   ├── pom.xml
│   └── src/main/java/com/Mogena/
│       ├── Model/                  ← Entidades JPA
│       ├── Repository/             ← Interfaces Spring Data
│       ├── Service/                ← Lógica de negocio
│       ├── WebController/          ← Controladores Thymeleaf
│       └── Security/               ← Configuración de seguridad
│   └── src/main/resources/
│       ├── templates/              ← Vistas Thymeleaf (.html)
│       ├── Static/Css/             ← Hojas de estilo
│       ├── Static/Js/              ← JavaScript
│       └── application.properties
└── DOCUMENTACION_TECNICA.md
```

---

## 10. Control de Versiones

Repositorio GitHub: **https://github.com/Skyfreefury/PlateUp**

Ramas:
- `main` — versión estable de producción
- `develop` — rama de desarrollo activa
- `copia-seguridad-css` — copia de seguridad del frontend

El historial de commits refleja una evolución progresiva del proyecto desde el backend inicial hasta la versión final con seguridad, cobro y gestión de caja.
