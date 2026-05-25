VetNova - Microservicio de Usuarios y Roles



Este microservicio forma parte del ecosistema de la clínica veterinaria VetNova. Está desarrollado con Spring Boot y se encarga de toda la gestión de autenticación, control de accesos basados en roles (RBAC), seguridad y el ciclo de vida de las cuentas de los usuarios internos (Administradores, Veterinarios y Recepcionistas).

Tecnologías Utilizadas



Java 21 (Amazon Corretto / OpenJDK)

Spring Boot 4.0.6

Spring Data JPA (Persistencia de datos)

Validation (Jakarta) (Protección y reglas de DTOs)

Lombok (Productividad de código)

MySQL 9.7 (Base de datos relacional corriendo en Docker)

Postman (Suite de pruebas para endpoints)

Arquitectura y Base de Datos



El microservicio utiliza una relación de Muchos a Muchos (Many-To-Many) mapeada mediante una tabla intermedia para flexibilizar que un usuario tenga múltiples funciones simultáneas dentro de la clínica.

Las tablas automáticas generadas por Hibernate son:

usuarios: Datos principales de la cuenta, sucursal y estado actual.

roles: Perfiles del sistema (ADMIN, VETERINARIO, etc.).

usuarios_roles: Tabla intermedia de asignación de permisos dinámicos con fecha de registro.

permisos y roles_permisos: Matriz para restricciones atómicas a nivel de endpoints.

Historias de Usuario Implementadas y Verificadas



HU-UR01: Creación de Usuarios y Cuentas



Descripción: Permite el registro de nuevos empleados asociándolos a una sucursal y asignándoles sus roles base.

Comportamiento: Guarda contraseñas procesadas de manera simulada y deja la cuenta con estado inicial PENDIENTE_ACTIVACION.

HU-UR02: Asignación y Modificación de Roles



Descripción: Permite cambiar o añadir nuevos roles a los usuarios existentes desde la consola de administración en tiempo real.

HU-UR03: Autenticación de Usuarios (Login)



Descripción: Valida credenciales contra la base de datos MySQL. Al tener éxito, genera y devuelve un token firmado JWT (JSON Web Token) con una expiración configurada para el turno laboral de 8 horas (28,800 segundos).

HU-UR05: Baja y Desactivación de Usuarios



Descripción: Aplica un Borrado Lógico (estado = 'INACTIVE') para deshabilitar los accesos de un usuario sin alterar la integridad y trazabilidad de los datos históricos de la clínica.

