package com.gmail.albermargar9;

import java.io.*;

/**
 * Gestiona la configuración de la aplicación, permitiendo la persistencia de las preferencias del usuario.
 * La configuración se guarda en un fichero binario en el directorio home del usuario.
 */
public class Configuracion {
    /** Ruta del archivo de configuración binaria del usuario. */
    private final String RUTA_ARCHIVO;

    /** Ruta por defecto del fichero de clientes. */
    private String defaultLocation;

    /** Carácter usado en el formato del menú. */
    private String menuCharacter;

    /** Indicador de si el informe debe guardarse en un fichero. */
    private String saveReport;

    /** Nombre del fichero de salida para los informes. */
    private String fileReport;

    /**
     * Constructor que inicializa la configuración, cargando primero los valores por defecto
     * y luego sobrescribiéndolos con los guardados en el fichero, si existe.
     */
    public Configuracion() {
        RUTA_ARCHIVO = System.getProperty("user.home") + File.separator + "configuration.bin";
        cargarConfiguracionPredeterminada();
        cargarDesdeFichero();
    }

    /**
     * Establece los valores de configuración por defecto.
     * Estos valores se usan si no existe un fichero de configuración previo.
     */
    private void cargarConfiguracionPredeterminada() {
        defaultLocation = "Clientes_LF.txt";
        menuCharacter = "*";
        saveReport = "false";
        fileReport = "informe.txt";
    }

    /**
     * Carga la configuración desde el fichero binario.
     * Si el fichero no existe, se mantienen los valores por defecto.
     */
    private void cargarDesdeFichero() {
        File file = new File(RUTA_ARCHIVO);
        if (!file.exists()) {
            return;
        }

        try (DataInputStream dis = new DataInputStream(new FileInputStream(file))) {
            while (dis.available() > 0) {
                String linea = dis.readUTF();
                String[] partes = linea.split(";", 2);
                if (partes.length == 2) {
                    setValor(partes[0], partes[1]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error al leer la configuración: " + e.getMessage());
        }
    }

    /**
     * Guarda la configuración actual en el fichero binario.
     * Cada opción se guarda como un par clave-valor en formato UTF.
     */
    public void guardarEnFichero() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(RUTA_ARCHIVO))) {
            dos.writeUTF("default_location;" + defaultLocation);
            dos.writeUTF("menu_character;" + menuCharacter);
            dos.writeUTF("save_report;" + saveReport);
            dos.writeUTF("file_report;" + fileReport);
            System.out.println("Configuración guardada exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar la configuración: " + e.getMessage());
        }
    }

    /**
     * Recupera el valor de una opción de configuración específica.
     *
     * @param clave La clave de la configuración solicitada (p. ej., "default_location").
     * @return El valor de la configuración como {@code String}, o {@code null} si la clave no es válida.
     */
    public String getValor(String clave) {
        switch (clave) {
            case "default_location":
                return defaultLocation;
            case "menu_character":
                return menuCharacter;
            case "save_report":
                return saveReport;
            case "file_report":
                return fileReport;
            default:
                return null;
        }
    }

    /**
     * Actualiza el valor de una opción de configuración.
     *
     * @param clave La clave de la configuración a modificar.
     * @param valor El nuevo valor a asignar.
     */
    public void setValor(String clave, String valor) {
        switch (clave) {
            case "default_location":
                defaultLocation = valor;
                break;
            case "menu_character":
                menuCharacter = valor;
                break;
            case "save_report":
                saveReport = valor;
                break;
            case "file_report":
                fileReport = valor;
                break;
        }
    }
}
