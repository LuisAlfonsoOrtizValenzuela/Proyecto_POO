package modelo;

import utilidades.IdPersona;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class Viaje {
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private int duracion;
    private Bus bus;
    private Terminal salida;
    private Terminal llegada;
    private final ArrayList<Pasaje> pasajes = new ArrayList<>();
    private final ArrayList<Tripulante> tripulantes;

    public Viaje(LocalDate fecha, LocalTime hora, int precio, int dur, Bus bus, Auxiliar aux, Conductor cond, Terminal sale, Terminal llega) {
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
        this.precio = precio;
    }

    public void setDuracion(int duracion) {
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
            lista[i][1] = "Disponible";

            for (Pasaje pasaje : pasajes) {
                if (pasaje.getAsiento() == (i + 1)) {
                    lista[i][1] = "* ";
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
        int size=pasajes.size();
        String[][] lista=new String[size][4];
        for(int i=0;i<size;i++){
            IdPersona id=pasajes.get(i).getVenta().getCliente().getIdPersona();
            lista[i][0]=id.toString();
            lista[i][1]=pasajes.get(i).getPasajero().getNomContacto().getNombre();
            lista[i][2]=pasajes.get(i).getPasajero().getNomContacto().toString();
            lista[i][3]=pasajes.get(i).getPasajero().getFonoContacto();
        }
        return lista;
    }

    public boolean existeDisponibilidad(){
        return (pasajes.size() < bus.getNroAsientos());
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
