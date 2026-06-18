# inventario-service
Microservicio encargado de administrar el stock de productos. Permite registrar inventario, consultar stock, descontar productos vendidos y enviar alertas cuando el stock llega al mínimo.
## Puerto
```
8083
```
## Tecnologías
- Java 21
- Spring Boot
- Spring Data JPA
- MySQL
- Eureka Client
- Swagger/OpenAPI
- HATEOAS
- Mockito/JUnit
- Docker
- Railway

## Base de datos
```
db_minimarket
```
## Endpoints V1
```
GET /api/v1/inventario
GET /api/v1/inventario/{productoId}
POST /api/v1/inventario
PUT /api/v1/inventario/descontar
DELETE /api/v1/inventario/{productoId}
```
## Endpoints V2 HATEOAS
```
GET /api/v2/inventario
GET /api/v2/inventario/{id}
POST /api/v2/inventario
PUT /api/v2/inventario/{productoId}
PUT /api/v2/inventario/descontar
DELETE /api/v2/inventario/{productoId}
```
## Swagger
```
http://localhost:8083/swagger-ui.html
```
## Ejemplo JSON
```json
{
  "productoId": 1,
  "stock": 100,
  "stockMinimo": 10
}
```
## Ejecutar pruebas
```bash
mvn test
```
## Ejecutar localmente
```bash
mvn spring-boot:run
```
## Configuración Railway
```properties
server.port=${PORT:8083}
```
Variables recomendadas:

```properties
SPRING_DATASOURCE_URL=jdbc:mysql://HOST:PORT/railway?serverTimezone=UTC&allowPublicKeyRetrieval=true&useSSL=false
SPRING_DATASOURCE_USERNAME=TU_USUARIO
SPRING_DATASOURCE_PASSWORD=TU_PASSWORD
EUREKA_CLIENT_ENABLED=false
```
