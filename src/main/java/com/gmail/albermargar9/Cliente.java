package com.gmail.albermargar9;

/**
 * Representa a un cliente con sus datos básicos, incluyendo información de contacto y facturación.
 * Esta clase almacena todos los detalles relevantes de un cliente extraídos del fichero de datos.
 */
public class Cliente {
    /** Identificador único del cliente. */
    private String id;

    /** Nombre de la empresa del cliente. */
    private String nombreEmpresa;

    /** Nombre del contacto asociado al cliente. */
    private String nombreContacto;

    /** Cargo de la persona de contacto en la empresa. */
    private String cargo;

    /** Calle de la dirección del cliente. */
    private String calle;

    /** Ciudad de origen del cliente. */
    private String ciudad;

    /** País de origen del cliente. */
    private String pais;

    /** Número de teléfono del cliente. */
    private String telefono;

    /** Total de facturación del cliente. */
    private double facturacion;

    /**
     * Constructor para inicializar un objeto Cliente con todos sus datos.
     *
     * @param id             El identificador único del cliente.
     * @param nombreEmpresa  El nombre de la empresa del cliente.
     * @param nombreContacto El nombre de la persona de contacto.
     * @param cargo          El cargo de la persona de contacto.
     * @param calle          La calle de la dirección del cliente.
     * @param ciudad         La ciudad donde se encuentra el cliente.
     * @param pais           El país del cliente.
     * @param telefono       El número de teléfono del cliente.
     * @param facturacion    El total de facturación del cliente.
     */
    public Cliente(String id, String nombreEmpresa, String nombreContacto, String cargo, String calle, String ciudad, String pais, String telefono, double facturacion) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreContacto = nombreContacto;
        this.cargo = cargo;
        this.calle = calle;
        this.ciudad = ciudad;
        this.pais = pais;
        this.telefono = telefono;
        this.facturacion = facturacion;
    }

    /**
     * Obtiene el nombre del contacto del cliente.
     *
     * @return El nombre del contacto.
     */
    public String getNombreContacto() {
        return nombreContacto;
    }

    /**
     * Obtiene el país de origen del cliente.
     *
     * @return El país del cliente.
     */
    public String getPais() {
        return pais;
    }

    /**
     * Obtiene la facturación total del cliente.
     *
     * @return La facturación en unidades monetarias.
     */
    public double getFacturacion() {
        return facturacion;
    }

    /**
     * Obtiene el cargo de la persona de contacto.
     *
     * @return El cargo del contacto.
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Obtiene el número de teléfono del cliente.
     *
     * @return El número de teléfono.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Devuelve una representación en texto del cliente, incluyendo todos sus datos.
     * Este formato es utilizado para la generación de informes.
     *
     * @return Una cadena con los datos completos del cliente.
     */
    @Override
    public String toString() {
        return String.format(
                "Cliente [ID=%s, Empresa=%s, Contacto=%s, Cargo=%s, Calle=%s, Ciudad=%s, País=%s, Teléfono=%s, Facturación=%.2f]",
                id, nombreEmpresa, nombreContacto, cargo, calle, ciudad, pais, telefono, facturacion);
    }
}
