public abstract class Pago {
    protected int monto;

    public Pago(int monto) {
        this.monto = monto;
    }

    public int getMonto() {
        return monto;
    }


    public abstract String getTipoPago();
}