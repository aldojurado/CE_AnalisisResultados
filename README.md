# Tarea 05: Análisis de resultados

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

El programa tiene el código para realizar diversos archivos y cálculos específicos usados para relalizar las distinas gráficas y tablas de resultados. Pero estas partes del código fueron comentados para que no se ejecuten.

Entonces lo que realiza el programa son 30 ejecuciones sobre la estrategia seleccionada (algoritmo genético o recocido simulado) junto con los esqemas de reemplazo junto con los esquemas de reemplazo y enfriamiento.

La interfaz de texto es intuitiva y solo consiste en ir seleccionando respecto al número de la opción que se desea seleccionar y los parámetros que se desean usar.

Por cada ejecución se imprimirá la mejor solución encontrada y la aptitud de esta solución.

Adicionalmente todas las gráficas generadas se guardaron en la carpeta **src/output/graficas**.

# sobre los archivos generados
Generamos cientos de archivos para poder realizar las gráficas, al final dejamos las más generales y las que contenían los datos más relevantes. 
Estos fueron los de aptitud qeu contienen todo el historial de cómo fue mejorando y el promedio de aptitud (para GA) de cada iteración. 
Los archivos generados abarcan las 5 estrategias con sus parametrizaciones, por las 30 ejecuciones que se realizaron con distintas semillas.
Sobre la entropía dejamos los archivos con las frecuencias (población inicial y final) y el archivo con la entropia en cada ejecución