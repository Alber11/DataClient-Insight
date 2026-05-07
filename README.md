# DataClient-Insight

DataClient-Insight es una aplicación de consola desarrollada en Java diseñada para la gestión, lectura y generación de informes a partir de datos de clientes almacenados en archivos de texto. El sistema permite explorar diferentes métodos de lectura de archivos en Java y ofrece opciones de configuración persistente.

## 🚀 Características

- **Múltiples métodos de lectura:** Implementación de carga de datos mediante `Scanner`, `FileReader` y `BufferedReader`.
- **Generación de Informes:** Filtrado de clientes (de un archivo txt clientes con informacion) con opciones de ordenación por facturación o nombre de contacto.
- **Configuración Persistente:** Uso de archivos binarios (`.bin`) para guardar las preferencias del usuario, como la ruta por defecto de los datos o el estilo del menú.
- **Gestión de Salida:** Capacidad para mostrar informes por consola o guardarlos en archivos de texto con nombres únicos para evitar sobrescrituras.

## 📂 Estructura del Proyecto

El proyecto sigue una estructura estándar de Maven:

* `src/main/java/com/gmail/albermargar9/`
    * `Main.java`: Punto de entrada de la aplicación y gestión del flujo de menús.
    * `Cliente.java`: Modelo de datos que representa a un cliente.
    * `LectorFicheros.java`: Clase de utilidad para procesar el archivo `Clientes_LF.txt`.
    * `Configuracion.java`: Gestión de la persistencia de configuración en formato binario.
* `Clientes_LF.txt`: Archivo de datos de ejemplo con formato CSV (delimitado por `;`).

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 21.
- **Gestor de Proyectos:** Maven.
- **Persistencia de Configuración:** `DataInputStream` y `DataOutputStream` para manejo de archivos binarios.
- **Procesamiento de Datos:** Java Streams y Comparators para el filtrado y ordenación de listas.

## 💻 Uso

### Requisitos previos
- JDK 21 instalado.
- Maven (opcional, para compilación).

### Ejecución
Al iniciar la aplicación, se presenta un menú principal con las siguientes opciones:

1.  **Scanner / FileReader / BufferReader:** Permite cargar los datos del archivo de clientes utilizando diferentes estrategias de I/O de Java.
2.  **Emitir Informes:** Una vez cargados los datos, permite filtrar clientes de España/Alemania y ordenarlos.
3.  **Configuración:** Permite cambiar la ruta del archivo de datos, el carácter decorativo del menú y activar/desactivar el guardado de informes en disco.

## 📝 Formato de Datos
El archivo de entrada debe tener campos separados por punto y coma (`;`). Los campos procesados son:
- ID del cliente
- Nombre de la empresa
- Nombre del contacto
- Facturación (en la 5ª posición, con soporte para coma decimal)
- Ciudad (7ª posición)
- País (11ª posición)

---
Desarrollado por [alber11](https://github.com/alber11).
