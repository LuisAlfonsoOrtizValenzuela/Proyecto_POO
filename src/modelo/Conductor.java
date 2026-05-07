package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;

import java.util.ArrayList;

public class Conductor extends Tripulante {

    private ArrayList<Viaje> viajes;

    public Conductor(IdPersona idPersona, Nombre nom, String telefono, Direccion direccion) {
        super(idPersona, nom, telefono, direccion);
        viajes = new ArrayList<>();
    }

    @Override
    public void addViaje(Viaje viaje) {
        viajes.add(viaje);
    }

    @Override
    public int getNroViajes() {
        return viajes.size();
    }
}
