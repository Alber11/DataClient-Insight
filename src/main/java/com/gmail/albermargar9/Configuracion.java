package com.gmail.albermargar9;

import java.io.*;

/**
 * Gestiona la configuración de la aplicación mediante un archivo binario.
 */
public class Configuracion {
    private final String RUTA_ARCHIVO;
    private String defaultLocation;
    private String menuCharacter;
    private String saveReport;
    private String fileReport;

    public Configuracion() {
        RUTA_ARCHIVO = System.getProperty("user.home") + File.separator + "configuration.bin";
        cargarConfiguracionPredeterminada();
        cargarDesdeFichero();
    }

    /**
     * Carga los valores por defecto en caso de no existir el archivo.
     */
    private void cargarConfiguracionPredeterminada() {
        defaultLocation = "Clientes_LF.txt";
        menuCharacter = "*";
        saveReport = "false";
        fileReport = "informe.txt";
    }

    /**
     * Lee la configuración del archivo binario.
     */
    private void cargarDesdeFichero() {
        File file = new File(RUTA_ARCHIVO);
        if (!file.exists()) return;

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
     * Guarda la configuración actual en el archivo binario.
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
