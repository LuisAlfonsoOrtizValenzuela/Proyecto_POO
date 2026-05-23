package controlador;

import excepciones.SistemaVentaPasajesException;
import modelo.*;
import utilidades.*;

import java.text.Format;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

public class SistemaVentaPasajes {
    private static SistemaVentaPasajes instance;
    private final ControladorEmpresas controlador;

    private final ArrayList<Cliente> clientes;
    private final ArrayList<Pasajero> pasajeros;
    private final ArrayList<Viaje> viajes;
    private final ArrayList<Venta> ventas;

    private SistemaVentaPasajes() {
        controlador = ControladorEmpresas.getInstance();
        clientes = new ArrayList<>();
        pasajeros = new ArrayList<>();
        viajes = new ArrayList<>();
        ventas = new ArrayList<>();
    }

    public static SistemaVentaPasajes getInstance() {
        if (instance == null) {
            instance = new SistemaVentaPasajes();
        }
        return instance;
    }



    public void createCliente(IdPersona id, Nombre nom, String fono, String email) throws SistemaVentaPasajesException {
        Optional<Cliente> buscarCliente = findCliente(id);

        if (buscarCliente.isPresent()) {
            throw new SistemaVentaPasajesException(":::: Ya existe un Cliente con el id indicado");
        }

        Cliente nuevo = new Cliente(nom, id, fono, email);

        clientes.add(nuevo);
    }

    public void createPasajero(IdPersona id, Nombre nom, String fono, Nombre nomContacto, String fonoContacto) throws SistemaVentaPasajesException {
        Optional<Pasajero> buscarPasajero = findPasajero(id);

        if (buscarPasajero.isPresent()) {
            throw new SistemaVentaPasajesException(":::: Ya existe un Pasajero con el id indicado");
        }

        Pasajero nuevo = new Pasajero(nom, id, fono, nomContacto, fonoContacto);

        pasajeros.add(nuevo);
    }

    public void createViaje(LocalDate fecha, LocalTime hora, int precio, int duracion, String patBus, IdPersona[] idTripulantes, String[] nomComunas) throws SistemaVentaPasajesException {
        Optional<Bus> buscarBus = controlador.findBus(patBus);
        Optional<Viaje> buscarViaje = findViaje(fecha, hora, patBus);
        Optional<Conductor> buscarConductor = controlador.findConductor(idTripulantes[1]);
        Optional<Auxiliar> buscarAuxiliar = controlador.findAuxiliar(idTripulantes[0]);
        Optional<Terminal> buscarTerminalSalida = controlador.findTerminalPorComuna(nomComunas[0]);
        Optional<Terminal> buscarTerminalLlegada = controlador.findTerminalPorComuna(nomComunas[1]);


        if (buscarBus.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Bus con la patente indicada");
        }

        if (buscarViaje.isPresent()) {
            throw new SistemaVentaPasajesException(":::: Ya existe un Viaje con fecha, hora y patente de bus indicados");
        }

        if (buscarConductor.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Conductor con el id indicado en la empresa con el rut indicado");
        }

        if (buscarAuxiliar.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Auxiliar con el id indicado en la empresa con el rut indicado");
        }

        if (buscarTerminalSalida.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Terminal de salida en la comuna indicada");
        }

        if (buscarTerminalLlegada.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Terminal de llegada en la comuna indicada");
        }

        Viaje nuevo = new Viaje(fecha, hora, precio, duracion, buscarBus.get(), buscarAuxiliar.get(), buscarConductor.get(), buscarTerminalSalida.get(), buscarTerminalLlegada.get());

        if (idTripulantes.length == 3) {
            Optional<Conductor> buscarSegundoConductor = controlador.findConductor(idTripulantes[2]);

            if (buscarSegundoConductor.isEmpty()) {

                throw new SistemaVentaPasajesException(":::: No se ha encontrado un segundo Conductor");
            }

            nuevo.addConductor(buscarSegundoConductor.get());
        }

        viajes.add(nuevo);

        buscarBus.get().addViaje(nuevo);

        buscarTerminalSalida.get().addSalida(nuevo);
        buscarTerminalLlegada.get().addLlegada(nuevo);
    }

    public void iniciaVenta(String idDoc, TipoDocumento tipo, Date fechaViaje, String comSalida, String comLlegada, IdPersona idCliente, int nroPasajes) throws SistemaVentaPasajesException {
        Optional<Venta> buscarVenta = findVenta(idDoc, tipo);
        Optional<Cliente> buscarCliente = findCliente(idCliente);

        if (buscarVenta.isPresent()) {
            throw new SistemaVentaPasajesException(":::: Ya existe una Venta con el id y tipo de documento indicados");
        }

        if (buscarCliente.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Cliente con el id indicado");
        }

        Cliente cliente = buscarCliente.get();

        Venta v = new Venta(idDoc, tipo, cliente);

        ventas.add(v);
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje, String comunaSalida, String comunaLlegada, int nroPasajes) {
        ArrayList<String[]> horariodis = new ArrayList<>();

        for (Viaje v : viajes) {
            if (v.getFecha().equals(fechaViaje) &&
                    v.getTerminalSalida().getDireccion().getComuna().equalsIgnoreCase(comunaSalida) &&
                    v.getTerminalLlegada().getDireccion().getComuna().equalsIgnoreCase(comunaLlegada) &&
                    v.getNroAsientosDisponibles() >= nroPasajes) {
                String[] row = {
                        v.getBus().getPatente().toUpperCase(),
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
        Optional<Viaje> buscarViaje = findViaje(fecha, hora, patBus);

        if (buscarViaje.isEmpty()) {
            return new String[0][0];
        }

        Viaje viaje = buscarViaje.get();

        return viaje.getAsientos();
    }

    public Optional<Integer> getMontoVenta(String idDocumento, TipoDocumento tipo) {
        Optional<Venta> buscarVenta = findVenta(idDocumento, tipo);
        if (buscarVenta.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buscarVenta.get().getMonto());
    }

    public Optional<String> getNombrePasajero(IdPersona idPasajero) {
        Optional<Pasajero> buscarPasajero = findPasajero(idPasajero);

        if (buscarPasajero.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(buscarPasajero.get().getNombreCompleto().toString());
    }

    public void vendePasaje(String idDoc, TipoDocumento tipo, LocalDate fechaViaje, LocalTime hora, String patBus, int asiento, IdPersona idPasajero) throws SistemaVentaPasajesException {
        Optional<Venta> buscarVenta = findVenta(idDoc, tipo);
        Optional<Viaje> buscarViaje = findViaje(fechaViaje, hora, patBus);
        Optional<Pasajero> buscarPasajero = findPasajero(idPasajero);

        if (buscarVenta.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe una Venta con el id y Tipo de documentos indicados");
        }

        if (buscarViaje.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Viaje con la fecha, hora y patente de bus indicados");
        }

        if (buscarPasajero.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Pasajero con el id indicado");
        }

        Viaje viaje = buscarViaje.get();

        if (!viaje.asientoDisponible(asiento)) {
            throw new SistemaVentaPasajesException(":::: El Asiento indicado ya esta ocupado");
        }

        if (asiento < 1 || asiento > viaje.getBus().getNroAsientos()) {
            throw new SistemaVentaPasajesException(":::: El numero del Asiento no es valido");
        }

        Pasajero pasajero = buscarPasajero.get();

        Venta venta = buscarVenta.get();

        Pasaje pasaje = new Pasaje(asiento, viaje, pasajero, venta);
    }

    public void pagaVenta(String idDocumento, TipoDocumento tipo) {

    }

    public void pagaVenta(String idDocumento, TipoDocumento tipo, long nroTarjeta) {

    }

    public String[][] listVentas() {
        ArrayList<String[]> lista = new ArrayList<>();

        for (Venta v : ventas) {
            String[] row = {
                    v.getIdDocumento(),
                    v.getTipo().toString(),
                    v.getFecha().toString(),
                    v.getCliente().getIdPersona().toString(),
                    v.getCliente().getNombreCompleto().toString(),
                    String.valueOf(v.getPasajes().length),
                    String.valueOf(v.getMonto())
            };
            lista.add(row);
        }
        return lista.toArray(new String[0][0]);
    }

    public String[][] listViajes() {

        ArrayList<String[]> lista = new ArrayList<>();

        for (Viaje v : viajes) {
            String[] row = {
                    v.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    v.getHora().toString(),
                    v.getFechaHoraTermino().format(DateTimeFormatter.ofPattern("HH:mm")),
                    String.valueOf(v.getPrecio()),
                    String.valueOf(v.getNroAsientosDisponibles()),
                    v.getBus().getPatente().toUpperCase(),
                    v.getTerminalSalida().getDireccion().getComuna(),
                    v.getTerminalLlegada().getDireccion().getComuna()

            };
            lista.add(row);
        }
        return lista.toArray(new String[0][0]);
    }

    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus) {
        Optional<Viaje> buscarViaje = findViaje(fecha, hora, patBus);

        if (buscarViaje.isEmpty()) {
            return new String[0][0];
        }

        Viaje viaje = buscarViaje.get();

        return viaje.getListaPasajeros();
    }





    // BUSCADORES



    private Optional<Cliente> findCliente(IdPersona id) {
        for (Cliente c : clientes) {
            if (c.getIdPersona().equals(id)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        for(Venta v : ventas) {
            if(v.getIdDocumento().equals(idDocumento) && v.getTipo().equals(tipoDocumento)) {
                return Optional.of(v);
            }
        }

        return Optional.empty();
    }

    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
        for (Viaje v : viajes) {
            if (v.getFecha().equals(fecha) && v.getHora().equals(hora) && v.getBus().getPatente().equalsIgnoreCase(patenteBus.trim())) {
                return Optional.of(v);
            }
        }

        return Optional.empty();
    }

    private Optional<Pasajero> findPasajero(IdPersona idPersona) {
        for (Pasajero p : pasajeros) {
            if (p.getIdPersona().equals(idPersona)) {
                return Optional.of(p);
            }
        }

        return Optional.empty();
    }

    // cosas

    public void createEmpresa(Rut rut, String nombre, String url) {
        controlador.createEmpresa(rut, nombre, url);
    }

    public void createBus(String patente, String marca, String modelo, int nroAsientos, Rut rutEmp) {
        controlador.createBus(patente, marca, modelo, nroAsientos, rutEmp);
    }

    public void createTerminal(String nombre, Direccion direccion) {
        controlador.createTerminal(nombre, direccion);
    }

    public String[][] listEmpresas() {
        return controlador.listEmpresas();
    }

}