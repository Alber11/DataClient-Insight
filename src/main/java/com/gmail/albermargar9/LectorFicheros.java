package com.gmail.albermargar9;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Gestiona la lectura y el parseo del fichero de clientes.
 * Proporciona métodos para leer el fichero de datos utilizando diferentes técnicas de E/S
 * y convierte cada línea en un objeto {@link Cliente}.
 */
public class LectorFicheros {

    /**
     * Crea un {@link Scanner} para el fichero indicado, permitiendo una lectura cómoda línea a línea.
     *
     * @param ruta La ruta del fichero a leer.
     * @return Un {@code Scanner} para el fichero.
     * @throws FileNotFoundException Si el fichero no se encuentra en la ruta especificada.
     */
    public static Scanner lectorFicheroScanner(String ruta) throws FileNotFoundException {
        return new Scanner(new File(ruta));
    }

    /**
     * Crea un {@link FileReader} para el fichero indicado, útil para lecturas de caracteres.
     *
     * @param ruta La ruta del fichero a leer.
     * @return Un {@code FileReader} para el fichero.
     * @throws FileNotFoundException Si el fichero no se encuentra.
     */
    public static FileReader lectorFicheroFilereader(String ruta) throws FileNotFoundException {
        return new FileReader(ruta);
    }

    /**
     * Crea un {@link BufferedReader} para el fichero, optimizado para la lectura eficiente de líneas de texto.
     *
     * @param ruta La ruta del fichero a leer.
     * @return Un {@code BufferedReader} para el fichero.
     * @throws FileNotFoundException Si el fichero no se encuentra.
     */
    public static BufferedReader lectorFicheroBufferReader(String ruta) throws FileNotFoundException {
        return new BufferedReader(new FileReader(ruta));
    }

    /**
     * Parsea una línea de texto del fichero y la convierte en un objeto {@link Cliente}.
     * La línea debe estar delimitada por punto y coma (;) y contener al menos 12 campos.
     *
     * @param linea La línea de texto a parsear.
     * @return Un objeto {@code Cliente} con los datos parseados, o {@code null} si la línea es inválida.
     */
    private static Cliente parseLinea(String linea) {
        if (linea == null || linea.trim().isEmpty()) {
            return null;
        }
        String[] datos = linea.split(";");
        if (datos.length < 12) {
            return null;
        }
        try {
            String facturacionStr = datos[4].replace(',', '.');
            double facturacion = Double.parseDouble(facturacionStr);
            String cargo = datos[5];
            String calle = datos[6];
            String ciudad = datos[7];
            String pais = datos[10];
            String telefono = datos[11];
            return new Cliente(datos[0], datos[1], datos[2], cargo, calle, ciudad, pais, telefono, facturacion);
        } catch (NumberFormatException e) {
            System.err.println("Error parseando facturación en línea: " + linea);
            return null;
        }
    }

    /**
     * Carga la lista de clientes desde el fichero utilizando un {@link Scanner}.
     * Este método es sencillo y adecuado para ficheros de tamaño moderado.
     *
     * @param ruta La ruta del fichero a leer.
     * @return Una lista de {@link Cliente} cargados desde el fichero.
     */
    public static List<Cliente> cargarClientesConScanner(String ruta) {
        List<Cliente> clientes = new ArrayList<>();
        try (Scanner scanner = lectorFicheroScanner(ruta)) {
            while (scanner.hasNextLine()) {
                Cliente cliente = parseLinea(scanner.nextLine());
                if (cliente != null) {
                    clientes.add(cliente);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("No se encontró el fichero: " + ruta);
        }
        return clientes;
    }

    /**
     * Carga la lista de clientes desde el fichero utilizando un {@link FileReader}.
     * Este método lee el fichero carácter a carácter, lo cual puede ser menos eficiente para ficheros grandes.
     *
     * @param ruta La ruta del fichero a leer.
     * @return Una lista de {@link Cliente} cargados.
     */
    public static List<Cliente> cargarClientesConFileReader(String ruta) {
        List<Cliente> clientes = new ArrayList<>();
        File file = new File(ruta);
        if (!file.exists()) {
            System.err.println("No se encontró el fichero: " + ruta);
            return clientes;
        }
        try (FileReader fr = lectorFicheroFilereader(ruta)) {
            char[] buffer = new char[(int) file.length()];
            int charsRead = fr.read(buffer);
            String fileContent = new String(buffer, 0, charsRead);
            String[] lineas = fileContent.split("\\r?\\n");
            for (String linea : lineas) {
                Cliente cliente = parseLinea(linea);
                if (cliente != null) {
                    clientes.add(cliente);
                }
            }
        } catch (IOException e) {
            System.err.println("Error de lectura: " + e.getMessage());
        }
        return clientes;
    }

    /**
     * Carga la lista de clientes desde el fichero utilizando un {@link BufferedReader}.
     * Este es el método más eficiente para leer ficheros de texto grandes línea por línea.
     *
     * @param ruta La ruta del fichero a leer.
     * @return Una lista de {@link Cliente} cargados.
     */
    public static List<Cliente> cargarClientesConBufferedReader(String ruta) {
        List<Cliente> clientes = new ArrayList<>();
        try (BufferedReader br = lectorFicheroBufferReader(ruta)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Cliente cliente = parseLinea(linea);
                if (cliente != null) {
                    clientes.add(cliente);
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
