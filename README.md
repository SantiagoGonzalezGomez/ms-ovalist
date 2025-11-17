# OVA Microservice

Microservicio Spring Boot para gestión de Objetos Virtuales de
Aprendizaje (OVAs). Proporciona operaciones CRUD completas con
paginación, filtros y validaciones.

## 🚀 Características

-   **CRUD Completo** - Create, Read, Update, Delete de OVAs
-   **Paginación** - Listas paginadas con tamaño personalizable
-   **Filtros Avanzados** - Búsqueda por categoría, texto y más
-   **Validaciones** - Validación automática de datos de entrada
-   **Manejo de Errores** - Sistema centralizado de excepciones
-   **API RESTful** - Endpoints REST estándar
-   **Sin Base de Datos** - Almacenamiento en memoria (ideal para
    desarrollo)

## 🛠️ Tecnologías

-   **Java 17**
-   **Spring Boot 3.4.11**
-   **Spring Web**
-   **Spring Validation**
-   **Lombok**
-   **Maven**

## 📋 Requisitos

-   Java 17 o superior
-   Maven 3.6+
-   Spring Boot 3.4.11

## 🔧 Instalación y Ejecución

### 1. Clonar el repositorio

``` bash
git clone https://github.com/SantiagoGonzalezGomez/ms-ovalist.git
cd ova-service
```

### 2. Compilar el proyecto

``` bash
mvn clean compile
```

### 3. Ejecutar la aplicación

``` bash
mvn spring-boot:run
```

La aplicación estará disponible en: http://localhost:8080

## 📚 Endpoints de la API

### Health Check

    GET /api/ovas/health

### Obtener todos los OVAs (con paginación)

    GET /api/ovas?pagina=0&tamaño=10&categoria=Programación&search=java

### Obtener OVA por ID

    GET /api/ovas/{id}

### Crear nuevo OVA

    POST /api/ovas

### Actualizar OVA

    PUT /api/ovas/{id}

### Eliminar OVA (soft delete)

    DELETE /api/ovas/{id}

### Buscar OVAs por categoría

    GET /api/ovas/categoria/{categoria}

### Buscar OVAs por título

    GET /api/ovas/buscar?titulo=java

## 📝 Modelo de Datos

### OVA Request (POST/PUT)

``` json
{
    "titulo": "Introducción a Java",
    "descripcion": "Curso básico de programación en Java",
    "autor": "Carlos Rodríguez",
    "url": "https://ejemplo.com/java-basico",
    "categoria": "Programación",
    "nivel": "Básico",
    "duracion": 120,
    "calificacion": 4.5
}
```

### OVA Response

``` json
{
    "id": 1,
    "titulo": "Introducción a Java",
    "descripcion": "Curso básico de programación en Java",
    "autor": "Carlos Rodríguez",
    "url": "https://ejemplo.com/java-basico",
    "categoria": "Programación",
    "nivel": "Básico",
    "duracion": 120,
    "calificacion": 4.5,
    "fechaCreacion": "2024-01-15T10:30:00.123456",
    "activo": true
}
```

## 🧪 Pruebas con Postman

### Health Check

    GET http://localhost:8080/api/ovas/health

### Crear OVA

    POST http://localhost:8080/api/ovas
    Content-Type: application/json

    {
        "titulo": "Spring Boot Avanzado",
        "descripcion": "Aprende Spring Boot con proyectos reales",
        "autor": "Santiago Gonzalez",
        "url": "https://miaula.com/spring-boot",
        "categoria": "Programación",
        "nivel": "Intermedio",
        "duracion": 90,
        "calificacion": 4.8
    }

### Listar OVAs con paginación

    GET http://localhost:8080/api/ovas?pagina=0&tamaño=5&categoria=Programación

## 🏗️ Estructura del Proyecto

    src/main/java/com/ova/microservice/
    ├── controller/           # Controladores REST
    ├── service/             # Lógica de negocio
    ├── model/               # Modelos de datos
    ├── dto/                 # Data Transfer Objects
    ├── exception/           # Manejo de excepciones
    └── OvaMicroserviceApplication.java

## 🐛 Manejo de Errores

### 400 - Validación fallida

``` json
{
    "titulo": "El título debe tener entre 3 y 100 caracteres",
    "url": "La URL debe ser válida"
}
```

### 404 - Recurso no encontrado

``` json
{
    "error": "OVA no encontrado con id: 999"
}
```


## ✨ Autor

Santiago Gonzalez Gomez - GitHub