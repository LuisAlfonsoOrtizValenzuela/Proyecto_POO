import java.util.Objects;

public class Persona {
    private IdPersona idPersona;
    private Nombre nombreCompleto;
    private String telefono;

    public Persona(Nombre nombreCompleto, IdPersona idPersona, String telefono){
        this.nombreCompleto=nombreCompleto;
        this.idPersona=idPersona;
        this.telefono=telefono;
    }

    public void setNombreCompleto(Nombre nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public IdPersona getIdPersona() {
        return idPersona;
    }

    public Nombre getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return idPersona + "," + nombreCompleto+ "," +telefono;
    }

    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass()) return false;
        Persona persona = (Persona) objeto;
        return Objects.equals(idPersona, persona.idPersona);
    }




}
