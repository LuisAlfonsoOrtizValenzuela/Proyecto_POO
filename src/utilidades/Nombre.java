package utilidades;
import java.io.Serializable;
import java.util.Objects;

public class Nombre implements Serializable {
    private static final long serialVersionUID = 1L;
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
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(":::: El Nombre no puede estar Vacio");
        }
        this.nombre = nombre.trim();
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        if (apellido_paterno == null || apellido_paterno.trim().isEmpty()) {
            throw new IllegalArgumentException(":::: El Apellido paterno no puede estar Vacio");
        }
        this.apellido_paterno = apellido_paterno.trim();
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        if (apellido_materno == null || apellido_materno.trim().isEmpty()) {
            throw new IllegalArgumentException(":::: El Apellido materno no puede estar Vacio");
        }
        this.apellido_materno = apellido_materno.trim();
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
