package modelo;

import excepciones.SVPException;
import utilidades.IdPersona;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.io.Serializable;

public class Viaje implements Serializable {

    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private int duracion;
    private Bus bus;
    private Terminal salida;
    private Terminal llegada;
    private final ArrayList<Pasaje> pasajes = new ArrayList<>();
    private final ArrayList<Tripulante> tripulantes;
    private static final long serialVersionUID = 1L;


    public Viaje(LocalDate fecha, LocalTime hora, int precio, int dur, Bus bus, Auxiliar aux, Conductor cond, Terminal sale, Terminal llega) {
        if (fecha == null) {
            throw new IllegalArgumentException("La Fecha no puede ser Nula");
        }
        if (hora == null) {
            throw new IllegalArgumentException("La Hora no puede ser Nula");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El Precio debe ser Mayor a 0");
        }
        if (dur <= 0) {
            throw new IllegalArgumentException("La Duración debe ser Mayor a 0");
        }
        if (bus == null) {
            throw new IllegalArgumentException("El Bus no puede ser Nulo");
        }
        if (aux == null) {
            throw new IllegalArgumentException("El Auxiliar no puede ser Nulo");
        }
        if (cond == null) {
            throw new IllegalArgumentException("El Conductor no puede ser Nulo");
        }
        if (sale == null) {
            throw new IllegalArgumentException("El Terminal de Salida no puede ser Nulo");
        }
        if (llega == null) {
            throw new IllegalArgumentException("El Terminal de Llegada no puede ser Nulo");
        }
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.duracion = dur;
        this.bus = bus;

        this.salida = sale;
        this.llegada = llega;

        tripulantes = new ArrayList<>();
        tripulantes.add(aux);
        tripulantes.add(cond);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("El Precio debe ser Mayor a 0");
        }
        this.precio = precio;
    }

    public void setDuracion(int duracion) {
        if (duracion <= 0) {
            throw new IllegalArgumentException("La Duracion debe ser Mayor a 0");
        }
        this.duracion = duracion;
    }

    public LocalDateTime getFechaHoraTermino() {
        LocalDateTime salida = LocalDateTime.of(fecha, hora);

        return salida.plusMinutes(duracion);
    }

    public Bus getBus(){
        return bus;
    }

    public String[][] getAsientos() {
        int length = bus.getNroAsientos();
        String[][] lista = new String[length][2];

        for (int i = 0; i < length; i++) {
            lista[i][0] = String.valueOf((i + 1));
            lista[i][1] = "disponible";

            for (Pasaje pasaje : pasajes) {
                if (pasaje.getAsiento() == (i + 1)) {
                    lista[i][1] = "*";
                    break;
                }
            }
        }
        return lista;
    }

    public void addPasaje(Pasaje pasaje){
        pasajes.add(pasaje);
    }

    public String[][] getListaPasajeros(){

        String[][] lista = new String[pasajes.size()][5];

        for (int i = 0; i < pasajes.size(); i++) {
            Pasaje pasaje = pasajes.get(i);
            Pasajero pasajero = pasaje.getPasajero();

            lista[i][0] = String.valueOf(pasaje.getAsiento());
            lista[i][1] = pasajero.getIdPersona().toString();
            lista[i][2] = pasajero.getNombreCompleto().toString();
            lista[i][3] = pasajero.getNomContacto().toString();
            lista[i][4] = pasajero.getFonoContacto();
        }

        return lista;
    }

    public boolean existeDisponibilidad(int nroAsientos){
        return getNroAsientosDisponibles() >= nroAsientos;
    }

    public int getNroAsientosDisponibles(){
        return (bus.getNroAsientos() - pasajes.size());
    }

    public Venta[] getVentas() {
        ArrayList<Venta> lista = new ArrayList<>();

        for (Pasaje p : pasajes) {
            Venta venta = p.getVenta();

            if (!lista.contains(venta)) {
                lista.add(venta);
            }
        }

        return lista.toArray(new Venta[0]);
    }

    public void addConductor(Conductor conductor) {
        tripulantes.add(conductor);
    }

    public Tripulante[] getTripulantes() {
        return tripulantes.toArray(new Tripulante[0]);
    }

    public Terminal getTerminalLlegada() {
        return llegada;
    }

    public Terminal getTerminalSalida() {
        return salida;
    }


    // algo


    public boolean asientoDisponible(int asiento) {
        for (Pasaje p : pasajes) {
            if (p.getAsiento() == asiento) {
                return false;
            }
        }

        return true;
    }
}
