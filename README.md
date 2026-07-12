cd ms_cliente
cd ms_comic
cd tiendas(1)
.\mvnw spring-boot:run


 http://localhost:8080/swagger-ui/index.html


 Rayen Beltran: ms_cliente

 Maximiiano Jimenez: ms_comic

 Sergio Torres: ms_tiendas

 Eureka Server (puerto 8761)
Servidor de registro y descubrimiento de servicios. Todos los microservicios se registran aquí al iniciar.

ms_cliente (puerto 8081)
Microservicio que gestiona clientes, envíos y pagos. Expone endpoints REST bajo /api/v1/clientes, /api/v1/envios y /api/v1/pago.
http://localhost:8081/doc/swagger-ui/swagger-ui/index.html#/

ms_comic (puerto 8082)
Microservicio que gestiona comics, autores, editoriales y categorías. Expone endpoints REST bajo /api/v1/comics, /api/v1/autores, /api/v1/editoriales y /api/v1/categorias.
http://localhost:8082/doc/swagger-ui/swagger-ui/index.html#/

tiendas (puerto 8083)
Microservicio que gestiona tiendas, dueños y empleados. Expone endpoints REST bajo /api/v1/tiendas, /api/v1/duenos y /api/v1/empleados.
http://localhost:8081/doc/swagger-ui/swagger-ui/index.html#/

API Gateway (puerto 8080)
Punto de entrada único al sistema. Enruta las peticiones hacia el microservicio correspondiente según la URL y balancea la carga usando Eureka.

Se agrego los docker en cada microservicio y el compose.yml en general.
