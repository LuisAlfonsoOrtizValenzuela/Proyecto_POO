import java.util.*;

public class Pasaje {

    private long numero;
    private int asiento;

    private Viaje viaje;
    private Pasajero pasajero;
    private Venta venta;

    public Pasaje(int asiento, Viaje viaje, Pasajero pasajero, Venta venta) {

        this.asiento = asiento;

        this.viaje = viaje;
        this.pasajero = pasajero;
        this.venta = venta;

        Random r = new Random();
        this.numero = Math.abs(r.nextLong());

        viaje.addPasaje(this);

    }

    public long getNumero() {
        return numero;
    }

    public int getAsiento() {
        return asiento;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public Pasajero getPasajero() {
        return pasajero;
    }

    public Venta getVenta() {
        return venta;
    }
}