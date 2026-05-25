🏥 VetNova - Microservicio de Mascotas (Pacientes)

Este microservicio forma parte del ecosistema de la clínica veterinaria VetNova. Está desarrollado con Spring Boot y asume la responsabilidad crítica de gestionar el registro de pacientes, la asignación parametrizada de especies y razas, el cálculo dinámico de constantes biológicas/edad, y el control estricto de historiales clínicos.

🛠️ Tecnologías Utilizadas
* Java 21 (OpenJDK / Eclipse Temurin)
* Spring Boot 4.0.6
* Spring Data JPA (Persistencia y queries avanzadas)
* Validation (Jakarta) (Validación de tipos de datos en endpoints)
* Lombok (Inyección de dependencias y patrones de diseño)
* MySQL 9.7 (Base de datos relacional corriendo de forma aislada en Docker)
* Postman (Pruebas de endpoints distribuidos)

🗄️ Arquitectura y Base de Datos
El componente opera de manera totalmente desacoplada usando la base de datos `vetnova_mascotas`. La comunicación con el dueño se resuelve mediante un "Enlace por ID Lógico" (`idCliente`), protegiendo la independencia de los servidores:
* mascotas: Ficha central del animal (Nombre, raza, sexo, estado vital) enlazada lógicamente a su dueño.
* especies: Catálogo maestro parametrizado (N pertenece a 1) que tipifica las opciones válidas del sistema (Canino, Felino, etc.).
* historiales_mascota: Relación de Composición (1 a 1) cuyo ciclo de vida depende de la mascota. Autogenera el Número de Historia Clínica único y correlativo.
* transferencias_propietario: Registro histórico de auditoría que almacena las fechas y traspasos familiares o de adopción (Cambios de dueño).

🚀 Historias de Usuario Implementadas y Verificadas

🔹 HU-MA01: Registro Completo de Mascotas (Pacientes)
* Descripción: Da de alta un paciente enlazándolo obligatoriamente al código de su cliente/dueño, autogenerando en cascada un Número de Historia Clínica correlativo único (Formato: VHC-XXXXX).

🔹 HU-MA02: Clasificación de Especie y Raza Parametrizada
* Descripción: Restringe la creación/edición de mascotas a especies preexistentes en el catálogo maestro para prevenir errores humanos en futuras dosificaciones de medicamentos.

🔹 HU-MA03: Transferencia de Propietario (Cambio de Dueño)
* Descripción: Modifica el campo lógico `idCliente` de la mascota guardando un rastro inalterable con los datos del propietario anterior, el nuevo y la fecha exacta del traspaso.

🔹 HU-MA04: Visualización de Ficha Clínica General y Constantes Biológicas
* Descripción: Retorna un resumen clínico completo calculando dinámicamente en tiempo de ejecución la edad exacta del animal (Años, Meses y Días) comparando su fecha de nacimiento con la fecha actual.

🔹 HU-MA05: Búsqueda Rápida de Pacientes (Mascotas)
* Descripción: Implementa un buscador global con Query JPA optimizada que filtra coincidencias parciales por Nombre, Microchip o Número de Historia Clínica.

🔹 HU-MA06: Control y Actualización del Estado Vital de la Mascota
* Descripción: Permite actualizar el estado biológico (ACTIVO, FALLECIDO, EXTRAVIADO, DONADO), gatillando alertas logs que suspenden agendas automáticas futuras en casos sensibles.

📝 Colección de Pruebas en Postman (JSONs de Ejemplo)

Registrar Mascota (Enlazada al Cliente ID 1):
POST http://localhost:8085/api/v1/mascotas

{
  "nombre": "Rocco",
  "especieNombre": "Canino",
  "raza": "Pastor Alemán",
  "sexo": "Macho",
  "fechaNacimiento": "2023-03-15",
  "idCliente": 1,
  "numeroMicrochip": "985112003456789",
  "ultimoPeso": 28.5,
  "estaEsterilizado": false,
  "alergiasCriticas": "Sensibilidad alimentaria a las proteínas de pollo"
}

Obtener Ficha Clínica con Edad Dinámica:
GET http://localhost:8085/api/v1/mascotas/1

Búsqueda Rápida Global:
GET http://localhost:8085/api/v1/mascotas/buscar?termino=Rocco

Transferencia de Propietario (Traspaso):
PATCH http://localhost:8085/api/v1/mascotas/1/transferir
{ "idNuevoCliente": 2 }