package modelo;

import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import java.io.Serializable;

import java.util.ArrayList;

public class Auxiliar extends Tripulante implements Serializable {
    private static final long serialVersionUID = 1L;
    private ArrayList<Viaje> viajes;

    public Auxiliar(IdPersona idPersona, Nombre nom, String telefono, Direccion direccion) {
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
