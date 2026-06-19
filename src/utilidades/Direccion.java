package utilidades;
import java.io.Serializable;
import java.util.Objects;

public class Direccion implements Serializable{
    private static final long serialVersionUID = 1L;
    private String calle;
    private int numero;
    private String comuna;

    public Direccion(String calle, int numero, String comuna) {
        if (calle == null || calle.trim().isEmpty()) {
            throw new IllegalArgumentException(":::: La Calle no puede estar Vacia");
        }
        if (comuna == null || comuna.trim().isEmpty()) {
            throw new IllegalArgumentException(":::: La Comuna no puede estar Vacia");
        }
        this.calle = calle.trim();
        this.numero = numero;
        this.comuna = comuna.trim();
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getComuna() {
        return comuna;
    }

    @Override
    public String toString() {
        return "Calle: " + calle +
                "\n Numero: " + numero +
                "\n Comuna: " + comuna;
    }

    @Override
    public boolean equals(Object otro) {
        if (otro == null || getClass() != otro.getClass()) return false;
        Direccion direccion = (Direccion) otro;

        return Objects.equals(comuna, direccion.comuna);
    }
}
