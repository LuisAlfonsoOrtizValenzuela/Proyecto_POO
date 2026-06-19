package modelo;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Pasaje implements Serializable{
    private static final long serialVersionUID = 1L;
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

    @Override
    public String toString() {
        return "--------------------- PASAJE ELECTRÓNICO ---------------------\n" +
               " Nombre Empresa       Número de Pasaje\n" +
               String.format(" %-20s %-20s",viaje.getBus().getEmpresa().getNombre(),venta.getIdDocumento()) +
               "\n\n Nombre Pasajero                          R.U.T/Pasaporte\n" +
               String.format(" %-40s %-15s",pasajero.getNombreCompleto().toString(),pasajero.getIdPersona().toString()) +
               "\n\n Patente Bus     Asiento          Valor Pagado\n" +
               String.format(" %-15s %-16s $%-15s",viaje.getBus().getPatente(),asiento,viaje.getPrecio()) +
               "\n\n Terminal Origen      Terminal Destino      Fecha        Hora\n" +
               String.format(" %-20s %-21s %-12s %-5s",viaje.getTerminalSalida().getNombre(),viaje.getTerminalLlegada().getNombre(),
                       viaje.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                       viaje.getHora().format(DateTimeFormatter.ofPattern("HH:mm"))) +
               "\n--------------------------------------------------------------\n";
    }
}






