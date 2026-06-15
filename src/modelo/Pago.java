package modelo;
import java.io.Serializable;
public abstract class Pago implements Serializable {
    private int monto;
    private static final long serialVersionUID = 1L;
    public Pago(int monto) {
        this.monto = monto;
    }

    public int getMonto() {
        return monto;
    }
}