package com.gmail.albermargar9;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase orientada al manejo y lectura del fichero de clientes mediante distintos métodos.
 */
public class LectorFicheros {

    public static Scanner lectorFicheroScanner(String ruta) throws FileNotFoundException {
        return new Scanner(new File(ruta));
    }

    public static FileReader lectorFicheroFilereader(String ruta) throws FileNotFoundException {
        return new FileReader(ruta);
    }

    public static BufferedReader lectorFicheroBufferReader(String ruta) throws FileNotFoundException {
        return new BufferedReader(new FileReader(ruta));
    }

    /**
     * Lee el archivo utilizando BufferedReader y parsea los clientes.
     * @param ruta Ruta del archivo a leer.
     * @return Lista de clientes generada.
     */
    public static List<Cliente> cargarClientes(String ruta) {
        List<Cliente> clientes = new ArrayList<>();
        // Se usa BufferedReader como método principal para delegar la carga por ser el más eficiente leyendo líneas
        try (BufferedReader br = lectorFicheroBufferReader(ruta)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                // Asumimos un formato separado por comas (Ajustar según tu Clientes_LF.txt)
                String[] datos = linea.split(";");
                if (datos.length >= 5) {
                    try {
                        double facturacion = Double.parseDouble(datos[4]);
                        clientes.add(new Cliente(datos[0], datos[1], datos[2], datos[3], facturacion));
                    } catch (NumberFormatException e) {
                        System.err.println("Error parseando facturación en línea: " + linea);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("No se encontró el fichero: " + ruta);
        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
        }
        return clientes;
    }
}
