import java.time.LocalTime;
import java.util.*;
import java.time.LocalDate;

public class SistemaVentaPasajes {

    private ArrayList<Cliente> clientes;
    private ArrayList<Pasajero> pasajeros;
    private ArrayList<Bus> buses;
    private ArrayList<Viaje> viajes;
    private ArrayList<Venta> ventas;

    public SistemaVentaPasajes() {

        clientes = new ArrayList<>();
        pasajeros = new ArrayList<>();
        buses = new ArrayList<>();
        viajes = new ArrayList<>();
        ventas = new ArrayList<>();

    }

    public boolean createCliente(IdPersona id, Nombre nom, String fono, String email){

        for (Cliente c : clientes) {
            if (c.getIdPersona().equals(id)) return false;
        }

        Cliente nuevo = new Cliente(nom, id, fono, email);

        clientes.add(nuevo);

        return true;

    }

    public boolean createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) {

        for (Pasajero p : pasajeros) {
            if (p.getIdPersona().equals(id)) return false;
        }

        Pasajero nuevo = new Pasajero(nom, id, fono, nomContacto, fonoContacto);
        pasajeros.add(nuevo);
        return true;
    }



    public boolean createBus(String patente, String marca, String modelo, int nroAsientos){

        for (Bus b : buses) {
            if (b.getPatente().equals(patente)) return false;
        }

        Bus nuevo = new Bus(patente, nroAsientos);
        nuevo.setMarca(marca);
        nuevo.setModelo(modelo);
        buses.add(nuevo);
        return true;

    }

    public boolean createViaje(LocalDate fecha, LocalTime hora, int precio, String patBus) {
        Bus bus = findBus(patBus);
        if (bus == null) return false;

        for (Viaje v : viajes) {
            if (v.getFecha().equals(fecha) && v.getHora().equals(hora) && v.getBus().equals(bus)) {
                return false;
            }
        }
        Viaje nuevo = new Viaje(fecha, hora, precio, bus);
        viajes.add(nuevo);
        return true;
    }



    //verificar y revisar
    public boolean iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaVenta, IdPersona idCliente){

        for (Venta v : ventas) {
            if (v.getIdDocumento().equals(idDoc)) return false;
        }

        Cliente cliente = findCliente(idCliente);
        if (cliente == null) return false;

        Venta v = new Venta(idDoc, tipo, cliente);
        ventas.add(v);

        return true;
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje) {

        ArrayList<String[]> horariodis = new ArrayList<>();

        for (Viaje v : viajes) {
            if (v.getFecha().equals(fechaViaje)) {
                String[] row = {
                        v.getBus().getPatente(),
                        v.getHora().toString(),
                        String.valueOf(v.getPrecio()),
                        String.valueOf(v.getNroAsientosDisponibles())
                };
                horariodis.add(row);
            }
        }
        return horariodis.toArray(new String[0][0]);
    }


    public String[][] listAsientosDeViaje(LocalDate fecha, LocalTime hora, String patBus) {
        Viaje viaje = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viaje == null) return new String[0][0]; //revisar bien
        return viaje.getAsientos(); //revisar
    }

    public int getMontoVenta(String idDocumento, TipoDocumento tipo) {
        Venta v = findVenta(idDocumento, tipo);
        if (v == null) return 0;
        return v.getMonto();
    }

    public String getNombrePasajero(IdPersona idPasajero) {
        Pasajero pas = findPasajero(idPasajero);
        if (pas == null) return null;
        return pas.getNombreCompleto().toString();
    }

    public boolean vendePasaje(String idDoc, LocalDate fecha, LocalTime hora, String patBus, int asiento, IdPersona idPasajero, TipoDocumento tipo ) {
        Venta venta = findVenta(idDoc, tipo); //agrege el tipo por que el finVenta pide un tipo de documento para poder usar el metodo
        if (venta == null) return false;

        Viaje viaje = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viaje == null) return false;

        Pasajero pasajero = findPasajero(idPasajero);
        if (pasajero == null) return false;

        return venta.createPasaje(asiento, viaje, pasajero);
    }


    // waaaaaaaaaa revisar lo correspondiente de la figura 12
    public String[][] listVentas() {
        ArrayList<String[]> lista = new ArrayList<>();

        for (Venta v : ventas) {
            String[] row = {
                    v.getIdDocumento(),
                    v.getTipo().toString(),
                    v.getFecha().toString(),
                    v.getCliente().getIdPersona().toString(),
                    String.valueOf(v.getPasajes().length),
                    String.valueOf(v.getMonto())
            };
            lista.add(row);
        }
        return lista.toArray(new String[0][0]);
    }

    // no se bien si se puede usar el String row
    public String[][] listViajes() {

        ArrayList<String[]> lista = new ArrayList<>();

        for (Viaje v : viajes) {
            String[] row = {
                    v.getFecha().toString(),
                    v.getHora().toString(),
                    String.valueOf(v.getPrecio()),
                    String.valueOf(v.getNroAsientosDisponibles()),
                    v.getBus().getPatente()
            };
            lista.add(row);
        }
        return lista.toArray(new String[0][0]);
    }

    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus) {
        Viaje viaje = findViaje(fecha.toString(), hora.toString(), patBus);
        if (viaje == null) {
            return new String[0][0];
        }
        return viaje.getListaPasajeros();

    }




















    //buscadores



    private Cliente findCliente(IdPersona id) {
        for (Cliente c : clientes) {
            if (c.getIdPersona().equals(id)) return c;
        }
        return null;
    }

    private Venta findVenta(String idDocumento, TipoDocumento tipoDocumento) {

        for(Venta v : ventas) {
            if(v.getIdDocumento().equals(idDocumento) && v.getTipo().equals(tipoDocumento)) {
                return v;
            }
        }
        return null;
    }


    private Bus findBus(String patente) {
        for (Bus b : buses) {
            if (b.getPatente().equals(patente)) return b;
        }
        return null;
    }

    private Viaje findViaje(String fecha, String hora, String patenteBus) {
        for (Viaje v : viajes) {
            if (v.getFecha().toString().equals(fecha) &&
                    v.getHora().toString().equals(hora) &&
                    v.getBus().getPatente().equals(patenteBus)) {
                return v;
            }
        }
        return null;
    }

    private Pasajero findPasajero(IdPersona idPersona) {

        for (Pasajero p : pasajeros) {
            if (p.getIdPersona().equals(idPersona)) return p;
        }

        return null;

    }


}