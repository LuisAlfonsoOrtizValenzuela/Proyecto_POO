public class PagoEfectivo extends Pago {
    public PagoEfectivo(int monto) {
        super(monto);
    }

    @Override
    public String getTipoPago() {
        return "Efectivo";
    }
}
