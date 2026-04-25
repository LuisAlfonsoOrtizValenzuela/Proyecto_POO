import java.util.Objects;

public class Nombre {
    private Tratamiento tratamiento;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;

    public Tratamiento getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(Tratamiento tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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
        return tratamiento + " " + nombre + " " + apellido_paterno + " " + apellido_materno;
    }

    @Override
    public boolean equals(Object otro) {
        if (this==otro) return true;
        if (otro==null || getClass()!=otro.getClass()) return false;

        Nombre nom = (Nombre) otro;
        return Objects.equals(nombre, nom.nombre) && Objects.equals(apellido_paterno, nom.apellido_paterno) && Objects.equals(apellido_materno, nom.apellido_materno);
    }
}
