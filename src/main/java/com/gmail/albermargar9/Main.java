package com.gmail.albermargar9;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Clase principal que ejecuta la aplicación de lectura e informes de clientes.
 */
public class Main {
    private static Configuracion config;
    private static List<Cliente> listaClientes = new ArrayList<>();
    private static Scanner teclado = new Scanner(System.in);
    private static String rutaFicheroLectura;

    public static void main(String[] args) {
        config = new Configuracion();

        // Si el programa recibe un parámetro, anula el default_location
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
                case "2":
                case "3":
                    System.out.println("Cargando fichero desde: " + rutaFicheroLectura);
                    listaClientes = LectorFicheros.cargarClientes(rutaFicheroLectura);
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

    private static void mostrarMenuPrincipal() {
        String c = config.getValor("menu_character");
        System.out.println("\n" + c.repeat(5) + " Menú principal " + c.repeat(5));
        System.out.println("1. Scanner");
        System.out.println("2. FileReader");
        System.out.println("3. BufferReader");
        System.out.println("4. Emitir Informes");
        System.out.println("5. Configuración");
        System.out.println("6. Salir");
        System.out.print("Elija una opción: ");
    }

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
            System.out.print("Elija una opción: ");
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

    private static void emitirInforme(List<Cliente> clientes, String titulo) {
        boolean guardar = Boolean.parseBoolean(config.getValor("save_report"));
        String reporte = titulo + "\n" + "-".repeat(titulo.length()) + "\n";

        for (Cliente cl : clientes) {
            reporte += cl.toString() + "\n";
        }

        System.out.println(reporte);

        if (guardar) {
            String rutaReporte = config.getValor("file_report");
            try (PrintWriter pw = new PrintWriter(new FileWriter(rutaReporte))) {
                pw.println(reporte);
                System.out.println("--> Informe guardado en: " + rutaReporte);
            } catch (IOException e) {
                System.err.println("Error al guardar el informe: " + e.getMessage());
            }
        }
    }

    private static void gestionarConfiguracion() {
        boolean salirMenuConfig = false;
        String[] claves = {"default_location", "menu_character", "save_report", "file_report"};

        while (!salirMenuConfig) {
            String c = config.getValor("menu_character");
            System.out.println("\n" + c.repeat(5) + " Menú Configuración " + c.repeat(5));
            System.out.println("1. default_location: " + config.getValor("default_location"));
            System.out.println("2. menu_character: " + config.getValor("menu_character"));
            System.out.println("3. save_report: " + config.getValor("save_report"));
            System.out.println("4. file_report: " + config.getValor("file_report"));
            System.out.println("5. Guardar nueva configuración y regresar al menú principal");
            System.out.println("6. Volver al Menú Principal sin guardar nueva configuración");
            System.out.print("Elija una opción: ");

            String opcion = teclado.nextLine();

            try {
                int op = Integer.parseInt(opcion);
                if (op >= 1 && op <= 4) {
                    String clave = claves[op - 1];
                    System.out.print("Escriba el nuevo valor de la variable de configuración (" + clave + "): ");
                    String nuevoValor = teclado.nextLine();
                    config.setValor(clave, nuevoValor);

                    // Si cambian la ruta por defecto y no hay args[0], actualizamos la ruta en caliente
                    if (clave.equals("default_location")) {
                        rutaFicheroLectura = nuevoValor;
                    }
                } else if (op == 5) {
                    config.guardarEnFichero();
                    salirMenuConfig = true;
                } else if (op == 6) {
                    // Si no guardamos, podríamos recargar desde fichero para revertir los cambios en memoria.
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