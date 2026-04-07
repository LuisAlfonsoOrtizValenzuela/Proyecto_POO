import java.util.Objects;

public class Nombre {
    private Tratamiento tratamiento;
    private String nombres;
    private String apellido_paterno;
    private String apellido_materno;

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    @Override
    public String toString() {
        return "- Tratamiento: " + tratamiento +
                " - Nombre: " + nombres +
                " - Apellido paterno: " + apellido_paterno +
                " - Apellido materno: " + apellido_materno;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Nombre nombre = (Nombre) o;
        return Objects.equals(nombres, nombre.nombres) && Objects.equals(apellido_paterno, nombre.apellido_paterno) && Objects.equals(apellido_materno, nombre.apellido_materno);
    }
}
