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
            throw new IllegalArgumentException("ERROR: La calle no puede estar vacia");
        }
        if (numero <= 0) {
            throw new IllegalArgumentException("ERROR: El numero debe ser mayor a 0");
        }
        if (comuna == null || comuna.trim().isEmpty()) {
            throw new IllegalArgumentException("ERROR: La comuna no puede estar vacia");
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
