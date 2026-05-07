package com.gmail.albermargar9;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Clase principal que orquesta la aplicación de gestión de clientes.
 * Proporciona un menú interactivo para cargar datos de clientes, generar informes y ajustar la configuración.
 */
public class Main {
    /**
     * Almacena la configuración de la aplicación, como rutas de ficheros y preferencias de formato.
     */
    private static Configuracion config;

    /**
     * Lista que contiene los objetos {@link Cliente} cargados desde el fichero de datos.
     */
    private static List<Cliente> listaClientes = new ArrayList<>();

    /**
     * Instancia de {@link Scanner} para leer la entrada del usuario desde la consola.
     */
    private static Scanner teclado = new Scanner(System.in);

    /**
     * Ruta del fichero de datos de clientes que se está utilizando actualmente.
     */
    private static String rutaFicheroLectura;

    /**
     * Punto de entrada principal de la aplicación.
     * Inicializa la configuración y gestiona el bucle del menú principal.
     *
     * @param args Argumentos de línea de comandos. Si se proporciona, {@code args[0]} se usa como la ruta del fichero de datos.
     */
    public static void main(String[] args) {
        config = new Configuracion();

        if (args.length > 0) {
            rutaFicheroLectura = args[0];
        } else {
            rutaFicheroLectura = config.getValor("default_location");
        }

        boolean salir = false;
        while (!salir) {
            mostrarMenuPrincipal();
            String opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    System.out.println("Cargando fichero con Scanner desde: " + rutaFicheroLectura);
                    listaClientes = LectorFicheros.cargarClientesConScanner(rutaFicheroLectura);
                    if (!listaClientes.isEmpty()) {
                        System.out.println("¡Clientes cargados exitosamente (" + listaClientes.size() + ")!");
                    }
                    break;
                case "2":
                    System.out.println("Cargando fichero con FileReader desde: " + rutaFicheroLectura);
                    listaClientes = LectorFicheros.cargarClientesConFileReader(rutaFicheroLectura);
                    if (!listaClientes.isEmpty()) {
                        System.out.println("¡Clientes cargados exitosamente (" + listaClientes.size() + ")!");
                    }
                    break;
                case "3":
                    System.out.println("Cargando fichero con BufferedReader desde: " + rutaFicheroLectura);
                    listaClientes = LectorFicheros.cargarClientesConBufferedReader(rutaFicheroLectura);
                    if (!listaClientes.isEmpty()) {
                        System.out.println("¡Clientes cargados exitosamente (" + listaClientes.size() + ")!");
                    }
                    break;
                case "4":
                    gestionarInformes();
                    break;
                case "5":
                    gestionarConfiguracion();
                    break;
                case "6":
                    salir = true;
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Muestra el menú principal de la aplicación en la consola.
     * El carácter utilizado para decorar el menú se obtiene de la configuración.
     */
    private static void mostrarMenuPrincipal() {
        String c = config.getValor("menu_character");
        System.out.println("\n" + c.repeat(5) + " Menú principal " + c.repeat(5));
        System.out.println("1. Scanner");
        System.out.println("2. FileReader");
        System.out.println("3. BufferReader");
        System.out.println("4. Emitir Informes");
        System.out.println("5. Configuración");
        System.out.println("6. Salir");
        System.out.print("Elija una opción y presione Enter: ");
    }

    /**
     * Gestiona la creación de informes. Permite al usuario elegir el criterio de ordenación
     * y filtra los clientes para incluir solo los de España y Alemania.
     */
    private static void gestionarInformes() {
        if (listaClientes.isEmpty()) {
            System.out.println("No se puede generar ningún informe porque no se dispone de información de clientes.");
            return;
        }

        boolean volver = false;
        while (!volver) {
            String c = config.getValor("menu_character");
            System.out.println("\n" + c.repeat(5) + " Emitir informes " + c.repeat(5));
            System.out.println("1. Ordenado por Facturación descendente.");
            System.out.println("2. Ordenado por Nombre de Contacto ascendente.");
            System.out.println("3. Menú anterior");
            System.out.print("Elija una opción y presione Enter: ");
            String opcion = teclado.nextLine();

            List<Cliente> filtrados = listaClientes.stream()
                    .filter(cl -> cl.getPais().equalsIgnoreCase("España") || cl.getPais().equalsIgnoreCase("Alemania"))
                    .collect(Collectors.toList());

            switch (opcion) {
                case "1":
                    filtrados.sort(Comparator.comparing(Cliente::getFacturacion).reversed());
                    emitirInforme(filtrados, "Informe por Facturación Descendente (España/Alemania)");
                    break;
                case "2":
                    filtrados.sort(Comparator.comparing(Cliente::getNombreContacto));
                    emitirInforme(filtrados, "Informe por Contacto Ascendente (España/Alemania)");
                    break;
                case "3":
                    volver = true;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    /**
     * Genera un informe con la lista de clientes proporcionada y lo guarda en un fichero o lo muestra en pantalla,
     * según la configuración de {@code save_report}.
     *
     * @param clientes La lista de clientes a incluir en el informe.
     * @param titulo   El título del informe.
     */
    private static void emitirInforme(List<Cliente> clientes, String titulo) {
        boolean guardar = Boolean.parseBoolean(config.getValor("save_report"));
        String reporte = titulo + "\n" + "-".repeat(titulo.length()) + "\n";

        for (Cliente cl : clientes) {
            reporte += cl.toString() + "\n";
        }

        if (guardar) {
            File file = getUniqueFilename(config.getValor("file_report"));
            try (PrintWriter pw = new PrintWriter(new FileWriter(file))) {
                pw.println(reporte);
                System.out.println("--> Informe guardado en: " + file.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Error al guardar el informe: " + e.getMessage());
            }
        } else {
            System.out.println(reporte);
        }
    }

    /**
     * Genera un nombre de fichero único para evitar sobrescrituras.
     * Si el nombre base ya existe, añade un sufijo numérico (p. ej., "informe(1).txt").
     *
     * @param baseFilename El nombre de fichero base deseado.
     * @return Un objeto {@link File} con una ruta de fichero única.
     */
    private static File getUniqueFilename(String baseFilename) {
        File file = new File(baseFilename);
        if (!file.exists()) {
            return file;
        }

        String name = baseFilename;
        String extension = "";
        int dotIndex = baseFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            name = baseFilename.substring(0, dotIndex);
            extension = baseFilename.substring(dotIndex);
        }

        int count = 1;
        while (file.exists()) {
            String newName = name + "(" + count + ")" + extension;
            file = new File(newName);
            count++;
        }
        return file;
    }

    /**
     * Muestra y gestiona el menú de configuración, permitiendo al usuario modificar
     * las opciones de la aplicación y guardarlas.
     */
    private static void gestionarConfiguracion() {
        boolean salirMenuConfig = false;
        String[] claves = {"default_location", "menu_character", "save_report", "file_report"};

        while (!salirMenuConfig) {
            String c = config.getValor("menu_character");
            System.out.println("\n" + c.repeat(5) + " Menú Configuración " + c.repeat(5));
            System.out.println("1. default_location (ruta): " + config.getValor("default_location"));
            System.out.println("2. menu_character: " + config.getValor("menu_character"));
            System.out.println("3. save_report: " + config.getValor("save_report"));
            System.out.println("4. file_report (ruta): " + config.getValor("file_report"));
            System.out.println("5. Guardar nueva configuración y regresar al menú principal");
            System.out.println("6. Volver al Menú Principal sin guardar nueva configuración");
            System.out.print("Elija una opción y presione Enter: ");

            String opcion = teclado.nextLine();

            try {
                int op = Integer.parseInt(opcion);
                if (op >= 1 && op <= 4) {
                    String clave = claves[op - 1];
                    System.out.print("Escriba el nuevo valor de la variable de configuración (" + clave + "): ");
                    String nuevoValor = teclado.nextLine();
                    config.setValor(clave, nuevoValor);

                    if (clave.equals("default_location")) {
                        rutaFicheroLectura = nuevoValor;
                    }
                } else if (op == 5) {
                    config.guardarEnFichero();
                    salirMenuConfig = true;
                } else if (op == 6) {
                    config = new Configuracion();
                    rutaFicheroLectura = config.getValor("default_location");
                    salirMenuConfig = true;
                } else {
                    System.out.println("Opción no válida.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Por favor, introduzca un número válido.");
            }
        }
    }
}
