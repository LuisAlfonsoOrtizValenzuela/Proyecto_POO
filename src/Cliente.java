import java.util.ArrayList;

public class Cliente extends Persona {
    private String email;
    private ArrayList<Venta> ventas;

    public Cliente(Nombre nombreCompleto, IdPersona idPersona, String telefono, Nombre nombre, String email) {
        super(nombreCompleto, idPersona);
        this.email = email;
        this.ventas = new ArrayList<>();
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
