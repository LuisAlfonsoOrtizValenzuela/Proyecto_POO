import java.util.Objects;

public class Pasaporte {

    private String numero;
    private String nacionalidad;
    private Pasaporte (String numero, String nacionalidad){
        this.nacionalidad=nacionalidad;
        this.numero=numero;
    }

    public String getNumero() {
        return numero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    @Override
    public String toString() {
        return numero + " " + nacionalidad;

    }

    @Override
    public boolean equals(Object objecto) {
        if (objecto == null || getClass() != objecto.getClass()) return false;
        Pasaporte pasaporte = (Pasaporte) objecto;
        return Objects.equals(numero, pasaporte.numero) && Objects.equals(nacionalidad, pasaporte.nacionalidad);
    }

}
