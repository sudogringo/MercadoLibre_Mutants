# 🧬 Mutant Detector API

[![Java](https://img.shields.io/badge/Java-17-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Tests](https://img.shields.io/badge/Tests-27%20passing-success.svg)]()
[![Coverage](https://img.shields.io/badge/Coverage-97%25-brightgreen.svg)]()

> 📚 API REST para detectar mutantes analizando secuencias de ADN.

---

## 📋 Tabla de Contenidos

1. [¿Qué es este proyecto?](#-qué-es-este-proyecto)
2. [Prerequisitos](#-prerequisitos)
3. [Instalación y Ejecución](#-instalación-y-ejecución)
4. [API Endpoints](#-api-endpoints)
5. [Testing](#-testing)
6. [Despliegue con Docker](#-despliegue-con-docker)

---

## 🎯 ¿Qué es este proyecto?

Este proyecto es una API REST que resuelve el desafío de detección de mutantes de MercadoLibre. La API expone dos endpoints:
- `POST /mutant`: para analizar una secuencia de ADN y determinar si es mutante.
- `GET /stats`: para obtener estadísticas de las verificaciones de ADN.

---

## 📦 Prerequisitos

- Java JDK 17+
- Git

---

## 🚀 Instalación y Ejecución

### 1. Clonar el Repositorio
```bash
git clone <URL_DEL_REPOSITORIO>
cd ExamenMercado
```

### 2. Compilar el Proyecto
```bash
# Windows
./gradlew.bat build

# Mac/Linux
./gradlew build
```

### 3. Ejecutar la Aplicación
```bash
# Windows
./gradlew.bat bootRun

# Mac/Linux
./gradlew bootRun
```
La aplicación estará corriendo en `http://localhost:8080`.

### 4. Verificar que Funciona
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **H2 Console**: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:mutantdb`, User: `sa`, Password: *(vacío)*)

---

## 📡 API Endpoints

### POST /mutant
Verifica si una secuencia de ADN es mutante.

**Request:**
```http
POST http://localhost:8080/mutant
Content-Type: application/json

{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]
}
```

**Responses:**
- `200 OK`: Si el ADN es mutante.
- `403 Forbidden`: Si el ADN es humano.
- `400 Bad Request`: Si el ADN es inválido.

### GET /stats
Retorna estadísticas de las verificaciones de ADN.

**Request:**
```http
GET http://localhost:8080/stats
```

**Response:**
```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

---

## 🧪 Testing

### Ejecutar Tests
```bash
./gradlew test
```

### Generar Reporte de Cobertura
```bash
./gradlew jacocoTestReport
```
El reporte se encuentra en `build/reports/jacoco/test/html/index.html`.

### Verificación de Cobertura
```bash
./gradlew jacocoTestCoverageVerification
```
Este comando fallará si la cobertura es menor al 80%.

---

## 🐳 Despliegue con Docker

### Construir la Imagen
```bash
docker build -t mutant-detector-api .
```

### Ejecutar el Contenedor
```bash
docker run -p 8080:8080 mutant-detector-api
```
La aplicación estará disponible en `http://localhost:8080`.

---

## ☁️ Despliegue en Render

Esta guía te mostrará cómo desplegar la aplicación en [Render](https://render.com/), un servicio en la nube con un plan gratuito ideal para este tipo de proyectos.

### Prerequisitos
- Una cuenta de [GitHub](https://github.com/).
- Una cuenta de [Render](https://dashboard.render.com/).

### Paso 1: Subir el Proyecto a GitHub
1. Crea un nuevo repositorio **público** en GitHub (ej. `mutant-detector-api`).
2. Sube el código de este proyecto a tu nuevo repositorio.
   ```bash
   git remote add origin https://github.com/TU_USUARIO/mutant-detector-api.git
   git branch -M main
   git push -u origin main
   ```

### Paso 2: Crear un Nuevo "Web Service" en Render
1. Ve a tu [Dashboard de Render](https://dashboard.render.com/).
2. Haz clic en **"New +"** y selecciona **"Web Service"**.
3. Conecta tu cuenta de GitHub si aún no lo has hecho.
4. Busca y selecciona el repositorio que creaste en el Paso 1.

### Paso 3: Configurar el Servicio
En la pantalla de configuración, asegúrate de que los siguientes valores estén correctos:
- **Name**: Elige un nombre único para tu servicio (ej. `mutant-api`).
- **Region**: Elige una región (ej. `US East`).
- **Runtime**: Selecciona **`Docker`**. Render detectará automáticamente tu `Dockerfile`.
- **Root Directory**: Déjalo en blanco.
- **Dockerfile Path**: Debería ser `./Dockerfile` por defecto.
- **Instance Type**: Elige el plan **`Free`**.

### Paso 4: Desplegar la Aplicación
1. Haz clic en el botón **"Create Web Service"** en la parte inferior de la página.
2. Render comenzará a construir la imagen de Docker y a desplegar tu aplicación. Puedes ver el progreso en los logs.
3. El primer despliegue puede tardar unos minutos.

### Paso 5: Acceder a la Aplicación
Una vez que el estado de tu servicio cambie a **"Live"**, podrás acceder a tu aplicación usando la URL pública que aparece en la parte superior de la página de tu servicio en Render.

- **API URL**: `https://mercadolibre-mutants.onrender.com`
- **Swagger UI**: `https://mercadolibre-mutants.onrender.com/swagger-ui.html`

## 👤 Autor

*   **Nombre:** Tiago Cunto Boberg
*   **Legajo:** 52629
