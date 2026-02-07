# Gold Cinema

Gold Cinema es una aplicación web orientada a la gestión y recomendación de películas, desarrollada como proyecto de portafolio Full Stack utilizando Java y Spring Boot.  
El objetivo del proyecto es demostrar la integración de backend, frontend y consumo de APIs externas, junto con lógica de negocio realista enfocada en la experiencia del usuario.

---

## Descripción general

La aplicación permite a los usuarios registrarse, iniciar sesión y gestionar películas marcadas como favoritas o vistas.  
A partir de esta información, el sistema genera recomendaciones personalizadas utilizando datos obtenidos desde la API externa OMDb.

El proyecto está diseñado para simular el funcionamiento de una plataforma real de recomendaciones, priorizando dinamismo, personalización y buenas prácticas de desarrollo.

---

## Funcionalidades principales

- Registro e inicio de sesión de usuarios
- Autenticación y autorización con Spring Security
- Gestión de películas favoritas y vistas
- Sistema de recomendaciones dinámicas
- Recomendaciones personalizadas por usuario
- Recomendaciones aleatorias para usuarios nuevos
- Consumo de la API OMDb para obtener información de películas
- Visualización de posters, géneros, actores y año
- Interfaz web responsive

---

## Sistema de recomendaciones

El sistema de recomendaciones se comporta de forma distinta según el estado del usuario:

### Usuarios nuevos
- Reciben recomendaciones obtenidas directamente desde la API OMDb
- Las películas se seleccionan de forma aleatoria
- Las recomendaciones cambian al recargar la página
- Cada usuario obtiene sugerencias diferentes

### Usuarios con historial
- Se analizan los géneros de las películas favoritas
- Se excluyen películas ya vistas o marcadas como favoritas
- Se priorizan coincidencias de género
- Se consideran valoraciones cuando están disponibles

Este enfoque evita recomendaciones repetitivas y mejora la experiencia del usuario.

---

# Tecnologías utilizadas

# Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- Maven

# Frontend
- HTML5
- CSS3
- JavaScript
- JSP

# Base de datos
- MySQL

# API externa
- OMDb API (Open Movie Database)

---

El proyecto se encuentra en desarrollo activo y continúa evolucionando como parte de mi formación profesional.

Mejoras futuras previstas:

Optimización del algoritmo de recomendaciones

Implementación de microservicios

Caché de respuestas de la API OMDb

Pruebas unitarias e integración

Dockerización del proyecto

Autora

María Fernanda Díaz
Trainee Full Stack Java

Este proyecto forma parte de mi portafolio personal y refleja mi enfoque en el aprendizaje continuo, la lógica de negocio y el desarrollo de aplicaciones reales.

