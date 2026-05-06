package com.gmail.albermargar9;

/**
 * Representa a un cliente con sus datos básicos.
 */
public class Cliente {
    private String id;
    private String nombreEmpresa;
    private String nombreContacto;
    private String pais;
    private double facturacion;

    /**
     * Constructor de la clase Cliente.
     * @param id Identificador del cliente.
     * @param nombreEmpresa Nombre de la empresa.
     * @param nombreContacto Nombre del contacto.
     * @param pais País de origen.
     * @param facturacion Total de facturación.
     */
    public Cliente(String id, String nombreEmpresa, String nombreContacto, String pais, double facturacion) {
        this.id = id;
        this.nombreEmpresa = nombreEmpresa;
        this.nombreContacto = nombreContacto;
        this.pais = pais;
        this.facturacion = facturacion;
    }

    public String getNombreContacto() { return nombreContacto; }
    public String getPais() { return pais; }
    public double getFacturacion() { return facturacion; }

    @Override
    public String toString() {
        return String.format("Cliente [ID=%s, Empresa=%s, Contacto=%s, País=%s, Facturación=%.2f]",
                id, nombreEmpresa, nombreContacto, pais, facturacion);
    }
}