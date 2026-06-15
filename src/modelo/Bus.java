package modelo;

import java.util.ArrayList;
import java.io.Serializable;
public class Bus implements Serializable{
    private String patente;
    private String marca;
    private String modelo;
    private int nroAsientos;
    private ArrayList<Viaje> viajes;
    private static final long serialVersionUID = 1L;

    public Bus(String patente, int nroAsientos) {
        if (patente == null || patente.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: La patente no puede estar vacia");
        }
        if (nroAsientos <= 0) {
            throw new IllegalArgumentException("ERROR: El numero de asientos debe ser mayor a 0");
        }
        this.patente = patente.trim();
        this.nroAsientos = nroAsientos;
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
            throw new IllegalArgumentException("ERROR: La marca no puede estar vacia");
        }
        this.marca = marca.trim();
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: El modelo no puede estar vacio");
        }
        this.modelo = modelo.trim();
    }

    public int getNroAsientos() {
        return nroAsientos;
    }

    public void addViaje(Viaje viaje) {
        viajes.add(viaje);
    }

    public Viaje[] getViajes() {
        return viajes.toArray(new Viaje[0]);
    }


}
