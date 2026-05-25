🏥 VetNova - Microservicio de Clientes y Propietarios

Este microservicio forma parte del ecosistema de la clínica veterinaria VetNova. Está desarrollado con Spring Boot y es el encargado absoluto de centralizar la ficha de los propietarios de mascotas (clientes), sus datos de contacto de emergencia, su comportamiento comercial (puntos de fidelidad) y las auditorías de bloqueos comerciales.

🛠️ Tecnologías Utilizadas
* Java 21 (OpenJDK / Eclipse Temurin)
* Spring Boot 4.0.6
* Spring Data JPA (Persistencia de datos)
* Validation (Jakarta) (Reglas de negocio y protección en DTOs)
* Lombok (Productividad y reducción de código repetitivo)
* MySQL 9.7 (Base de datos relacional corriendo de forma aislada en Docker)
* Postman (Validación y pruebas integrales de endpoints)

🗄️ Arquitectura y Base de Datos
El microservicio implementa el patrón "Base de datos por Microservicio" usando el esquema aislado `vetnova_clientes`. Estructura relaciones desacopladas del resto del ecosistema:
* clientes: Almacena los datos principales del dueño (RUT único, Nombre, Dirección, etc.), su estatus comercial y saldo de puntos.
* contactos_emergencia: Relación de Composición (1 a N) insertada en cascada fuerte, asegurando que un cliente posea contactos de verificación cruzada para casos críticos.
* bloqueos_comerciales: Tabla histórica de auditoría donde se registran las razones y fechas de clientes dados de baja por morosidad o malas prácticas.

🚀 Historias de Usuario Implementadas y Verificadas

🔹 HU-CL01: Registro Integral de Clientes con Contacto de Emergencia
* Descripción: Permite dar de alta a un propietario en el sistema capturando sus datos personales y obligando a registrar al menos un contacto de respaldo en un solo flujo transaccional.
* Comportamiento: Valida el formato del RUT y guarda el registro con estado inicial 'ACTIVO' y 0 puntos acumulados.

🔹 HU-CL02: Sistema de Puntos de Fidelidad (Fidelización)
* Descripción: Incrementa de forma automática o manual los puntos del cliente basados en sus consumos o campañas de marketing dentro de la clínica.

🔹 HU-CL03: Bloqueo Comercial por Morosidad o Malas Prácticas
* Descripción: Aplica una baja comercial (estado = 'BLOQUEADO'), registrando en la tabla de auditoría el motivo y el operario que lo realiza. Suspende inmediatamente la capacidad del cliente de agendar citas en los otros servicios.

📝 Colección de Pruebas en Postman (JSONs de Ejemplo)

Crear Cliente con Contacto de Emergencia:
POST http://localhost:8084/api/v1/clientes

{
  "rut": "12.345.678-9",
  "nombre": "Gabriel Martinez",
  "telefono": "+56912345678",
  "email": "gabriel.martinez@duocuc.cl",
  "direccion": "Avenida Concha y Toro 1340, Puente Alto",
  "contactosEmergencia": [
    {
      "nombre": "María José Fuentes",
      "telefono": "+56998765432",
      "parentesco": "Esposa"
    }
  ]
}

Bloquear Cliente (Auditoría Comercial):
PATCH http://localhost:8084/api/v1/clientes/1/bloquear?motivo=Morosidad%20excesiva%20en%20hospitalizacion