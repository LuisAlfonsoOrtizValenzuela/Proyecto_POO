package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import java.io.Serializable;
public class Pasajero extends Persona implements Serializable{
    private Nombre nomContacto;
    private String fonoContacto;
    private static final long serialVersionUID = 1L;
    public Pasajero(Nombre nombreCompleto, IdPersona idPersona, String telefono, Nombre nomContacto, String fonoContacto) {
        super(nombreCompleto, idPersona, telefono);
        this.nomContacto = nomContacto;
        this.fonoContacto = fonoContacto;
    }

    public Nombre getNomContacto() {
        return nomContacto;
    }
    public void setNomContacto(Nombre nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getFonoContacto() {
        return fonoContacto;
    }
    public void setFonoContacto(String fonoContacto) {
        this.fonoContacto = fonoContacto;
    }
}
