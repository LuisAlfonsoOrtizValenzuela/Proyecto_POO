package modelo;

import utilidades.Direccion;
import utilidades.IdPersona;
import utilidades.Nombre;
import java.io.Serializable;
public abstract class Tripulante extends Persona implements Serializable {
    private Direccion direccion;
    private static final long serialVersionUID = 1L;
    public Tripulante(IdPersona idPersona, Nombre nom, String telefono, Direccion direccion) {
        super(nom, idPersona, telefono);
        this.direccion = direccion;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public abstract void addViaje(Viaje viaje);

    public abstract int getNroViajes();
}
