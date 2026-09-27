\# PetCare Kotlin



Proyecto desarrollado para la Evaluación Parcial 1 de la asignatura Desarrollo de Aplicaciones Móviles.



\## Descripción



PetCare es una aplicación de consola desarrollada en Kotlin para gestionar boxes de atención veterinaria.



El sistema permite trabajar con distintos tipos de pacientes, controlar el estado de los boxes, calcular tarifas según las reglas de negocio, registrar entradas y salidas, manejar errores y generar un reporte de cierre de turno.



\## Funcionalidades principales



\- Gestión de pacientes Canino, Felino y Exótico.

\- Herencia y polimorfismo.

\- Estados de boxes mediante sealed class.

\- Manejo de colecciones.

\- Cálculo de tarifas, descuentos, recargos e IVA.

\- Registro de entradas y salidas.

\- Uso de corrutinas con suspend fun y delay.

\- Manejo de errores mediante try-catch.

\- Consultas sobre pacientes y boxes.

\- Reporte de cierre de turno.



\## Requisitos



\- IntelliJ IDEA.

\- Kotlin.

\- Gradle.

\- JDK compatible con el proyecto.



\## Ejecución



1\. Clonar o descargar el repositorio.

2\. Abrir la carpeta del proyecto en IntelliJ IDEA.

3\. Esperar que Gradle sincronice las dependencias.

4\. Abrir el archivo:



&#x20;  `src/main/kotlin/Main.kt`



5\. Ejecutar la función `main()`.



El programa mostrará en consola el registro de entradas y salidas, los cálculos realizados, las consultas de negocio, los errores controlados y el reporte final.



\## Estructura principal



\- `Main.kt`: ejecuta y demuestra el funcionamiento del sistema.

\- `Paciente.kt`: clase base de los pacientes.

\- `Canino.kt`: lógica correspondiente a pacientes caninos.

\- `Felino.kt`: lógica correspondiente a pacientes felinos.

\- `Exotico.kt`: lógica correspondiente a pacientes exóticos.

\- `Box.kt`: administra el estado de cada box.

\- `EstadoBox.kt`: define los posibles estados de un box.

\- `PetCareService.kt`: contiene la lógica principal del sistema.

\- `Ticket.kt`: representa los tickets de atención.

\- `TipoDueno.kt`: define los tipos de dueño.



\## Autor



Antonia

