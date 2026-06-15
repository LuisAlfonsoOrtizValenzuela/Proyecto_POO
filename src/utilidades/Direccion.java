package utilidades;
import java.io.Serializable;
import java.util.Objects;

public class Direccion implements Serializable{
    private static final long serialVersionUID = 1L;
    private String calle;
    private int numero;
    private String comuna;

    public Direccion(String calle, int numero, String comuna) {
        this.calle = calle;
        this.numero = numero;
        this.comuna = comuna;
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
