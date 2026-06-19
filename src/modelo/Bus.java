package modelo;

import java.util.ArrayList;
import java.io.Serializable;
public class Bus implements Serializable{
    private String patente;
    private String marca;
    private String modelo;
    private int nroAsientos;
    private Empresa empresa;
    private ArrayList<Viaje> viajes;
    private static final long serialVersionUID = 1L;

    public Bus(String patente, int nroAsientos, Empresa empresa) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException(":::: La Patente no puede estar Vacia");
        }
        if (nroAsientos <= 0) {
            throw new IllegalArgumentException(":::: El numero de Asientos debe ser Mayor a 0");
        }
        this.patente = patente.trim();
        this.nroAsientos = nroAsientos;
        this.empresa = empresa;
        this.viajes = new ArrayList<>();
    }

    public String getPatente() {
        return patente;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException(":::: La Marca no puede estar Vacia");
        }
        this.marca = marca.trim();
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException(":::: El Modelo no puede estar Vacio");
        }
        this.modelo = modelo.trim();
    }

    public int getNroAsientos() {
        return nroAsientos;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void addViaje(Viaje viaje) {
        viajes.add(viaje);
    }

    public Viaje[] getViajes() {
        return viajes.toArray(new Viaje[0]);
    }


}
