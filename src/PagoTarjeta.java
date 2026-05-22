public class PagoTarjeta extends Pago {
    private long nroTaregeta;

    public PagoTarjeta (int monto, long nroTargeta){

        super(monto);
        this.nroTaregeta=nroTargeta;

    }

    public long getNroTaregeta() {
        return nroTaregeta;
    }
    @Override
    public String getTipoPago() {
        return "Tarjeta";}

}

