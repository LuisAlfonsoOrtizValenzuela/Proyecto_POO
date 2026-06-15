package modelo;
import java.io.Serializable;
public class PagoTarjeta extends Pago implements Serializable {
    private long nroTarjeta;
    private static final long serialVersionUID = 1L;
    public PagoTarjeta (int monto, long nroTarjeta){
        super(monto);
        this.nroTarjeta = nroTarjeta;
    }

    public long getNroTarjeta() {
        return nroTarjeta;
    }
}

