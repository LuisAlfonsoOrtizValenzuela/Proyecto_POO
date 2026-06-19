package modelo;
import java.io.Serializable;
import utilidades.*;
import excepciones.SistemaVentaPasajesException;

import java.util.ArrayList;

public class Empresa implements Serializable{
    private static final long serialVersionUID = 1L;
    private Rut rut;
    private  String nombre;
    private String url;

    private ArrayList<Bus> buses;
    private ArrayList<Conductor> conductores;
    private ArrayList<Auxiliar> auxiliares;
    private ArrayList<Tripulante> tripulantes;
    private ArrayList<Venta> ventas;

    public Empresa(Rut rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
        buses = new ArrayList<>();
        auxiliares = new ArrayList<>();
        conductores = new ArrayList<>();
        tripulantes = new ArrayList<>();
        ventas = new ArrayList<>();
    }

    public Rut getRut() {
        return rut;
    }

    public  String getNombre() {
        return nombre;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addBus(Bus bus) {
        for (Bus b : buses) {
            if (b.getPatente().equalsIgnoreCase(bus.getPatente())) {
                throw new SistemaVentaPasajesException("::: Ya existe un Bus con la Patente indicada");
            }
        }

        buses.add(bus);
    }

    public Bus[] getBuses() {
        return buses.toArray(new Bus[0]);
    }

    public boolean addConductor(IdPersona id, Nombre nom, String telefono, Direccion dir) {
        for (Conductor c : conductores) {
            if (c.getIdPersona().equals(id)) {
                return false;
            }
        }

        for (Auxiliar a : auxiliares) {
            if (a.getIdPersona().equals(id)) {
                return false;
            }
        }

        Conductor conductor = new Conductor(id, nom, null, dir);

        tripulantes.add(conductor);

        return conductores.add(conductor);
    }

    public boolean addAuxiliar(IdPersona id, Nombre nom, String telefono, Direccion dir) {
        for (Conductor c : conductores) {
            if (c.getIdPersona().equals(id)) {
                return false;
            }
        }

        for (Auxiliar a : auxiliares) {
            if (a.getIdPersona().equals(id)) {
                return false;
            }
        }

        Auxiliar auxiliar = new Auxiliar(id, nom, null, dir);

        tripulantes.add(auxiliar);

        return auxiliares.add(auxiliar);
    }

    public Tripulante[] getTripulantes() {
        return tripulantes.toArray(new Tripulante[0]);
    }

    public Venta[] getVentas() {
        ArrayList<Venta> lista = new ArrayList<>();

        for (Bus bus : buses) {
            for (Viaje viaje : bus.getViajes()) {
                for (Venta venta : viaje.getVentas()) {
                    if (!lista.contains(venta)) {
                        lista.add(venta);
                    }
                }
            }
        }

        return lista.toArray(new Venta[0]);
    }
}
