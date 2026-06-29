# Hostal Cordillera – Sistema de Gestión

## Integrantes
- José González
- Javier Calquin

---

# API REST – Gestión de Hostal (Microservicios)

## Descripción
Sistema de gestión de un hostal implementado con **arquitectura de microservicios** usando **Spring Boot 3, Spring Cloud y MySQL**.  
Permite administrar:

- Regiones  
- Comunas  
- Hostales  
- Habitaciones  
- Huéspedes  
- Empleados  
- Reservas  
- Pagos  
- Reseñas  

---

## Tecnologías Utilizadas
- Java 21  
- Spring Boot 3.4.5  
- Spring Cloud Netflix (Eureka, Gateway)  
- Spring Data JPA / Hibernate ORM  
- Spring HATEOAS  
- Spring WebFlux (WebClient)  
- MySQL 8  
- Flyway (migración de esquema en huéspedes)  
- Maven  
- Lombok  
- Bean Validation (JSR 380)  
- SpringDoc OpenAPI / Swagger UI 2.8.5  
- DataFaker 2.4.0 (datos de prueba)  
- JUnit 5 + Mockito  
- SLF4J Logging  

---

## Arquitectura del Proyecto
El sistema está compuesto por 5 microservicios independientes:


---

Cada microservicio tiene su propia base de datos MySQL y ciclo de vida independiente.

---

## Capas internas (CSR)
- **Controller** → recibe solicitudes HTTP REST, valida entradas y delega a la capa de servicio.  
- **Service** → lógica de negocio, validaciones y logs.  
- **Repository** → acceso a datos con `JpaRepository`.  
- **Model** → entidades JPA con relaciones.  
- **DTO** → objetos de transferencia entre capas.  
- **Assemblers** → construcción de `EntityModel` con links HATEOAS (v2).  
- **Exceptions** → manejo centralizado con `@RestControllerAdvice`.  

---

## Endpoints disponibles
Todos los endpoints se exponen desde el Gateway en `http://localhost:8099` y están versionados:

- `/api/v1/...` → respuesta plana.  
- `/api/v2/...` → respuesta con HATEOAS.  

Ejemplos:
- **Huéspedes** → `/api/v1/huespedes`  
- **Hostales** → `/api/v1/hostales`  
- **Empleados** → `/api/v1/empleados`  

---

## Bases de Datos
Cada microservicio crea su base de datos automáticamente al iniciar (`createDatabaseIfNotExist=true`):

| Microservicio        | Base de datos   | Puerto |
|----------------------|-----------------|--------|
| Hostal Cordillera    | `hostaldb`      | 8082   |
| Huéspedes            | `db_huespedes`  | 8081   |
| Empleados            | `empleados_db`  | 8083   |

Usuario: `root`  
Contraseña: *(vacía por defecto – configurar según entorno)*  

---

## Ejecución del proyecto

## Requisitos previos
- Java 21  
- Maven 3.9+  
- MySQL 8 en `localhost:3306`  

## Opción 1 – Script automático (Windows)
```bat
start.bat
```
## Opción 2 – Ejecución manual
# 1. Eureka Server
```
cd eureka
./mvnw spring-boot:run
```
# 2. Microservicio Huéspedes
```
cd huespedes
./mvnw spring-boot:run
```
# 3. Microservicio Hostal Cordillera
```
cd "hostal cordillera"
./mvnw spring-boot:run
```
# 4. API Gateway
```
cd gateway
./mvnw spring-boot:run
```
# 5. Microservicio Empleados
```
cd empleados
./mvnw spring-boot:run
```

## URLs de acceso
Eureka Dashboard  
```
http://localhost:8761
```
API Gateway  
```
http://localhost:8099
```
Swagger UI centralizado  
```
http://localhost:8099/swagger-ui/index.html
```
Swagger – Huéspedes  
```
http://localhost:8081/swagger-ui/index.html
```
Swagger – Hostal Cordillera  
```
http://localhost:8082/swagger-ui/index.html
```
Swagger – Empleados  
```
http://localhost:8083/swagger-ui/index.html
```

## Comunicación entre microservicios
Empleados → Hostal Cordillera: validación remota vía WebClient (Spring WebFlux).

Huéspedes → Hostal Cordillera: referencia por comuna_id sin llamada remota.

Gateway: balanceo de carga y enrutamiento dinámico con Eureka.
