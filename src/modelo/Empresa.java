package modelo;

import utilidades.*;

import java.util.ArrayList;

public class Empresa {
    private Rut rut;
    private String nombre;
    private String url;

    private ArrayList<Bus> buses;
    private ArrayList<Conductor> conductores;
    private ArrayList<Auxiliar> auxiliares;
    private ArrayList<Venta> ventas;

    public Empresa(Rut rut, String nombre) {
        this.rut = rut;
        this.nombre = nombre;
        buses = new ArrayList<>();
        auxiliares = new ArrayList<>();
        conductores = new ArrayList<>();
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
        buses.add(bus);
    }

    public Bus[] getBuses() {
        Bus[] busLista = new Bus[buses.size()];

        for (int i = 0; i < buses.size(); i++) {
            busLista[i] = buses.get(i);
        }
        return busLista;
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
        Tripulante[] tripulantesLista = new Tripulante[auxiliares.size() + conductores.size()];

        // Falta terminar

        return tripulantesLista;
    }

    public Venta[] getVentas() {
        Venta[] ventasLista = new Venta[0]; // arreglar

        return ventasLista; // arreglar
    }
}
