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
        StringBuilder sb = new StringBuilder();


        String nombreEmpresa = "EMPRESA";

        sb.append("PASAJE ELECTRÓNICO");
        sb.append("\n");
        sb.append("Nombre Empresa").append("\n");
        sb.append("Número de pasaje");
        sb.append("\n");
        sb.append(nombreEmpresa);
        sb.append("\n");
        sb.append(numero);
        sb.append("\n");
        sb.append("Nombre Pasajero").append("\n");
        sb.append("RUT/Pasaporte");
        sb.append("\n");
        sb.append(pasajero.getNombreCompleto().toString().toUpperCase());
        sb.append("\n");
        sb.append(pasajero.getIdPersona().toString());
        sb.append("\n");
        sb.append("Patente bus").append("\n");
        sb.append("Asiento").append("\n");
        sb.append("Valor Pagado");
        sb.append("\n");
        sb.append(viaje.getBus().getPatente());
        sb.append("\n");
        sb.append(asiento);
        sb.append("\n");
        sb.append(venta.getMontoPagado());
        sb.append("\n");
        sb.append("Terminal origen").append("\n");
        sb.append("Terminal destino").append("\n");
        sb.append("Fecha").append("\n");
        sb.append("Hora");
        sb.append("\n");
        sb.append(viaje.getTerminalSalida().getDireccion().getComuna().toUpperCase());
        sb.append("\n");
        sb.append(viaje.getTerminalLlegada().getDireccion().getComuna().toUpperCase());
        sb.append("\n");
        sb.append(viaje.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        sb.append("\n");
        sb.append(viaje.getHora().format(DateTimeFormatter.ofPattern("HH:mm")));

        return sb.toString();
    }
}






