package modelo;

import utilidades.*;
import excepciones.SistemaVentaPasajesException;

import java.util.ArrayList;

public class Empresa {
    private Rut rut;
    private String nombre;
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

    public String getNombre() {
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
            if (b.getPatente().equals(bus.getPatente())) {
                throw new SistemaVentaPasajesException("::: Ya existe un Bus con la patente indicada");
            }
        }
        buses.add(bus);
    }

    public Bus[] getBuses() {
        return buses.toArray(new Bus[0]);
    }

    public boolean addConductor(IdPersona id, Nombre nom, String telefono, Direccion dir) {
        Conductor conductor = new Conductor(id, nom, null, dir);

        return conductores.add(conductor);
    }

    public boolean addAuxiliar(IdPersona id, Nombre nom, String telefono, Direccion dir) {
        Auxiliar auxiliar = new Auxiliar(id, nom, null, dir);

        return auxiliares.add(auxiliar);
    }

    public Tripulante[] getTripulantes() {
        return tripulantes.toArray(new Tripulante[0]);
    }

    public Venta[] getVentas() {
        return ventas.toArray(new Venta[0]);
    }
}
