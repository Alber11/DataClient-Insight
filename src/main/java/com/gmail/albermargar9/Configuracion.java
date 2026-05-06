package com.gmail.albermargar9;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Gestiona la configuración de la aplicación mediante un archivo binario.
 */
public class Configuracion {
    private final String RUTA_ARCHIVO;
    private Map<String, String> propiedades;

    public Configuracion() {
        RUTA_ARCHIVO = System.getProperty("user.home") + File.separator + "configuration.bin";
        propiedades = new LinkedHashMap<>();
        cargarConfiguracionPredeterminada();
        cargarDesdeFichero();
    }

    /**
     * Carga los valores por defecto en caso de no existir el archivo.
     */
    private void cargarConfiguracionPredeterminada() {
        propiedades.put("default_location", "Clientes_LF.txt");
        propiedades.put("menu_character", "*");
        propiedades.put("save_report", "false");
        propiedades.put("file_report", "informe.txt");
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
                    propiedades.put(partes[0], partes[1]);
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
            for (Map.Entry<String, String> entry : propiedades.entrySet()) {
                dos.writeUTF(entry.getKey() + ";" + entry.getValue());
            }
            System.out.println("Configuración guardada exitosamente.");
        } catch (IOException e) {
            System.err.println("Error al guardar la configuración: " + e.getMessage());
        }
    }

    public String getValor(String clave) {
        return propiedades.get(clave);
    }

    public void setValor(String clave, String valor) {
        propiedades.put(clave, valor);
    }
}