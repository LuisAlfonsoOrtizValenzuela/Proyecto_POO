public class Pasaje {

    private long numero;
    private int asiento;


    Pasaje(int asiento, Viaje viaje, Pasajero pasajero, Venta venta){

    }

    public Pasaje(long numero, int asiento) {
        this.numero = numero;
        this.asiento = asiento;

    }

    public long getNumero() {
        return numero;
    }

    public int getAsiento() {
        return asiento;
    }

    public Viaje getViaje(){
        return null;
    }

    public Pasajero getPasajero(){
        return null;
    }

    public Venta getVenta(){
        return null;
    }
}
