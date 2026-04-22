# Eclipse PlateUp — Sistema TPV para Restaurantes

> Proyecto final de ciclo DAW · Cristian Mogena · 2026

---

## ¿Qué es esto?

**Eclipse PlateUp** es una aplicación web de gestión integral para restaurantes. Simula un TPV (Terminal Punto de Venta) real: permite gestionar mesas, tomar pedidos, mandar comandas a cocina y barra, cobrar cuentas y cerrar caja al final del turno.

Tiene dos partes diferenciadas:
- **Web pública** — página de presentación del restaurante con carta y formulario de reservas.
- **Panel interno** — accesible solo con login, con todos los módulos operativos.

---

## Stack tecnológico

| Capa | Tecnología |
|------|-----------|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3 |
| Persistencia | Spring Data JPA + Hibernate |
| Base de datos | MySQL 8 |
| Seguridad | Spring Security |
| Plantillas | Thymeleaf |
| Frontend | Bootstrap 5 + CSS propio |
| Build | Maven |
| Control de versiones | Git / GitHub |

---

## Módulos del sistema

### Mesas
- Vista de todas las mesas con estado en tiempo real (Libre / Ocupada / Reservada).
- CRUD completo con validación de capacidad.
- El estado **Reservada** solo se activa el día de la reserva; antes aparece como Libre.
- Validación: un cliente no puede tener dos mesas reservadas el mismo día.

### Reservas (web pública)
- Formulario público sin login.
- Algoritmo **best-fit**: asigna la mesa libre con menor exceso de capacidad.
- Respuesta inmediata vía JSON con toast de confirmación.

### Pedidos (Cuentas)
- Apertura y cierre de cuentas por mesa.
- Numeración correlativa de tickets por sesión de caja.
- Cobro diferenciado: efectivo / tarjeta / mixto.
- Ticket imprimible generado automáticamente.

### Cocina y Barra
- Vista en tiempo real de comandas pendientes separadas por tipo.
- Temporizador de espera por comanda.
- Marcado de platos como "Listo" con un clic.

### Carta de Productos
- CRUD de platos y bebidas agrupados por categoría.
- Sistema de reciclaje de IDs (hasta 500 productos).

### Clientes
- Directorio con nombre, email y teléfono.
- Email único por cliente; múltiples reservas en fechas distintas permitidas.

### Caja (Sesiones)
- Apertura de turno con fondo inicial.
- KPIs en tiempo real: ingresos totales, efectivo, tarjeta, efectivo en caja.
- Desglose de ventas por producto.
- Cierre de sesión con resumen imprimible.

---

## Cómo ejecutarlo localmente

### Requisitos
- Java 17+
- MySQL 8+
- Maven 3.8+

### Pasos

**1. Clonar el repositorio**
```bash
git clone https://github.com/Skyfreefury/PlateUp.git
cd PlateUp
```

**2. Crear la base de datos**
```sql
-- Ejecutar en MySQL:
source docs/DataBase/init.sql
```

**3. Configurar credenciales**

Editar `PlateUp/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/restaurante_db
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_CONTRASEÑA
```

**4. Compilar y arrancar**
```bash
cd PlateUp
mvn spring-boot:run
```

**5. Abrir en el navegador**
```
http://localhost:8080
```

El usuario administrador se crea automáticamente al primer arranque.

---

## Estructura del proyecto

```
PlateUP/
├── docs/
│   ├── DiagramaClasesJava_PlateUP.drawio     ← Diagrama UML de clases
│   ├── DiagramaEntidadRelacion_PlateUP.drawio ← Diagrama ER de la BD
│   ├── DOCUMENTACION_TECNICA.md              ← Documentación técnica completa
│   └── DataBase/
│       ├── init.sql                           ← Script de creación de BD
│       └── data.sql                           ← Datos de ejemplo
└── PlateUp/
    └── src/main/java/com/Mogena/
        ├── Model/          ← Entidades JPA
        ├── Repository/     ← Interfaces Spring Data
        ├── Service/        ← Lógica de negocio y validaciones
        ├── WebController/  ← Controladores Thymeleaf MVC
        └── Security/       ← Configuración Spring Security
```

---

## Arquitectura

El proyecto sigue el patrón **MVC** en 4 capas:

```
Navegador → Controller → Service → Repository → MySQL
```

- **Controller**: recibe la petición HTTP y devuelve la vista.
- **Service**: contiene toda la lógica de negocio y validaciones.
- **Repository**: acceso a datos via Spring Data JPA (consultas derivadas por nombre de método).
- **Model**: entidades JPA mapeadas a tablas MySQL.

---

## Decisiones de diseño destacadas

- **Reciclaje de IDs en Mesas**: el número visible de mesa coincide con su ID en BD. Al eliminar una mesa, el hueco queda disponible para la siguiente. Límite: 20 mesas.
- **Estado de reserva inteligente**: una mesa `RESERVADA` muestra estado `LIBRE` hasta el día de la reserva, activando el badge interactivo solo ese día.
- **Validación anti-duplicado por día**: un cliente solo puede tener una mesa reservada por fecha, implementado con consultas derivadas de Spring Data sin SQL manual.
- **Separación web pública / panel interno**: rutas públicas sin autenticación (`/`, `/reservas`); todo el panel protegido con Spring Security.

---

## Diagramas

| Diagrama | Archivo |
|----------|---------|
| Clases Java (UML) | `docs/DiagramaClasesJava_PlateUP.drawio` |
| Entidad-Relación (BD) | `docs/DiagramaEntidadRelacion_PlateUP.drawio` |

---

## Repositorio

**https://github.com/Skyfreefury/PlateUp**

| Rama | Uso |
|------|-----|
| `main` | Versión estable |
| `develop` | Desarrollo activo |

---

## Guía de exposición

> Usa esta sección como hilo conductor de tu presentación.

**1. Introducción (1-2 min)**
> "He desarrollado un sistema TPV web para restaurantes llamado Eclipse PlateUp. Tiene una parte pública para los clientes del restaurante y un panel interno para el personal."

**2. Demo de la web pública (1-2 min)**
- Mostrar la página de inicio.
- Hacer una reserva desde el formulario público y mostrar el toast de confirmación.
- Explicar el algoritmo best-fit de selección de mesa.

**3. Login y panel de mesas (2-3 min)**
- Entrar con el usuario admin.
- Mostrar la tabla de mesas con los distintos estados (Libre / Ocupada / Reservada).
- Explicar la lógica de estado inteligente: reservada activa solo el día de la reserva.
- Crear una mesa nueva y mostrar el reciclaje de IDs.

**4. Flujo completo de pedido (3-4 min)**
- Abrir una cuenta en una mesa.
- Añadir comandas y mostrar cómo aparecen en la vista de Cocina.
- Marcar un plato como listo desde Cocina.
- Cobrar la cuenta (efectivo + tarjeta) y mostrar el ticket.

**5. Módulos secundarios (1-2 min)**
- Carta de productos: mostrar las categorías y el CRUD.
- Clientes: mostrar el directorio y la validación de email único.
- Caja: mostrar los KPIs del turno actual.

**6. Arquitectura y tecnología (2 min)**
- Mostrar el diagrama de clases Java (`DiagramaClasesJava_PlateUP.drawio`).
- Explicar las 4 capas: Controller → Service → Repository → Model.
- Mencionar Spring Security, Thymeleaf y Spring Data JPA.

**7. Decisiones técnicas destacadas (1-2 min)**
- Reciclaje de IDs en mesas.
- Validación anti-duplicado de reservas por día con consultas derivadas de JPA.
- CSS responsive con patrón tabla → tarjeta en móvil.

**8. Cierre (30 seg)**
> "El proyecto está en GitHub con documentación técnica completa, diagrama ER y diagrama de clases UML."

---

*Eclipse PlateUp © 2026*
