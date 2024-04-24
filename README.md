# Tarea 04: Algoritmo Genético

## Integrantes del equipo:

- **Ángeles Sánchez Aldo Javier 320286144**
- **Jurado Guadalupe Aldo Emilio 320025255**

## Requisitos

Este proyecto usa maven. <br>
**Instalación desde terminal:**

### En fedora:

```bash
sudo dnf install maven
```

### En ubuntu:

```bash
sudo apt install maven
```

Adicionalmente ya se debe tener java con su **jdk**.
De no tenerlo, en fedora se instala de la siguiente forma:

```bash
sudo dnf install java-devel

```

## Compilar y Ejecutar

Para compilar de forma limpia:

```bash
mvn clean install
```

Para ejecutar el programa (después de compilar):

```bash
java -jar target/ejecuta.jar
```

## Limpieza

Adicionalmente para limpiar los archivos generados por la compilación:

```bash
mvn clean
```

## Comentarios

# Sobre la interfaz 

El programa cuenta con una pequeña interfaz de texto para ir seleccionando
las distintas opciones para la ejecución de los algoritmos.
Los parámetros se pasan después de la ejecución.

Para realizar la ejecución del algoritmo genético de optimización continua se debe seguir los siguientes pasos:<br>
En los menús primero se debe selecciónar:<br>
 Problema de optimización contínua índice (2)<br> 
    luego se selecciona la función de evaluación (1, ... , 8)<br>
    y por último se escribe como parámetros el tamaño de la población, la semilla, la probabilidad de cruza y la dimensión de la función.<br>

Se imprimirán la mejor solución encontrada y se guardarán en el archivo src/outoput/solucionesContinuas/reporte.txt la mejor aptitud y la aptitud promedio en cada generación.

Adicionalmente en el directorio src/output/solucionesContinuas se encuentran las gráficas de aptitud y la tabla con la mejor solución en las 30 ejecuciones por cada función.
