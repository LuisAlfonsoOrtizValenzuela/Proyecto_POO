package modelo;

import utilidades.IdPersona;
import utilidades.Nombre;
import java.io.Serializable;
import java.util.ArrayList;

public class Cliente extends Persona implements Serializable{
    private String email;
    private ArrayList<Venta> ventas;
    private static final long serialVersionUID = 1L;
    public Cliente(Nombre nombreCompleto, IdPersona idPersona, String telefono, String email) {
        super(nombreCompleto, idPersona, telefono);
        this.email = email;
        this.ventas = new ArrayList<>();
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addVenta(Venta venta) {
        ventas.add(venta);
    }
}
