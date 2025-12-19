# Sistema de Clasificación de Animé

## Descripción
Aplicación de escritorio desarrollada en Java con interfaz gráfica Swing que permite gestionar un catálogo personal de animes. El sistema permite registrar, modificar y eliminar animes, así como crear listas personalizadas, buscar mediante filtros avanzados, visualizar estadísticas y generar recomendaciones automáticas.

Este proyecto fue desarrollado para la materia **Paradigma Orientado a Objetos** aplicando patrones **GRASP**, principios **SOLID** y una arquitectura en capas.

## Requerimientos
* **Java Development Kit (JDK):** Versión 17 o superior (Recomendado JDK 23 según configuración del proyecto).
* **IDE Sugerido:** IntelliJ IDEA o Eclipse.
* **Sistema Operativo:** Windows, Linux o macOS.

## Estructura del Proyecto
El proyecto está dividido en los siguientes paquetes para asegurar una baja cohesión y alto acoplamiento:
* `Model`: Entidades del dominio (Anime, Serie, Película, Catálogo, etc.).
* `Services`: Lógica de negocio (Gestión, Búsqueda, Estadísticas, Recomendación).
* `UI`: Interfaz gráfica (Swing) separada de la lógica.
* `repositorio`: Persistencia de datos.

## Instrucciones de Instalación y Ejecución

### Desde IntelliJ IDEA
1.  Abrir IntelliJ IDEA.
2.  Seleccionar **File > Open** y elegir la carpeta raíz del proyecto (`TPO-Clasificador-Anime-master`).
3.  Esperar a que el IDE indexe los archivos.
4.  Navegar a `src/UI/MainFrame.java`.
5.  Hacer clic derecho en el archivo y seleccionar **Run 'MainFrame.main()'**.

### Compilación Manual (Terminal)
1.  Navegar a la carpeta `src` del proyecto.
2.  Compilar los archivos:
    ```bash
    javac Model/*.java Services/*.java UI/*.java repositorio/*.java
    ```
3.  Ejecutar la aplicación:
    ```bash
    java UI.MainFrame
    ```

## Persistencia
El sistema genera automáticamente un archivo binario para guardar el catálogo.
* **Ruta por defecto:** `Documents/AplicacionCatalogoAnimes/catalogo.anime`
* Si el archivo no existe, el sistema pedirá crear un nuevo catálogo al iniciar.

## Autor
* **Hernan Alejandro Sisi** `hsisi@uade.edu.ar`