package controlador;
import persistencia.IOSVP;
import excepciones.SistemaVentaPasajesException;
import modelo.*;
import utilidades.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.LocalDate;

public class SistemaVentaPasajes implements Serializable{
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

    public void iniciaVenta(String idDoc, TipoDocumento tipo, LocalDate fechaViaje, String comSalida, String comLlegada, IdPersona idCliente, int nroPasajes) throws SistemaVentaPasajesException {
        Optional<Venta> buscarVenta = findVenta(idDoc, tipo);
        Optional<Cliente> buscarCliente = findCliente(idCliente);

        if (buscarVenta.isPresent()) {
            throw new SistemaVentaPasajesException(":::: Ya existe una Venta con el Id y Tipo de Documento indicados");
        }

        if (buscarCliente.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Cliente con el Id indicado");
        }

        Cliente cliente = buscarCliente.get();

        Venta v = new Venta(idDoc, tipo, cliente);

        ventas.add(v);
    }

    public String[][] getHorariosDisponibles(LocalDate fechaViaje, String comunaSalida, String comunaLlegada, int nroPasajes) {
        return viajes.stream()
                .filter(viaje -> viaje.getFecha().equals(fechaViaje))
                .filter(viaje -> viaje.getTerminalSalida().getDireccion().getComuna().equalsIgnoreCase(comunaSalida))
                .filter(viaje -> viaje.getTerminalLlegada().getDireccion().getComuna().equalsIgnoreCase(comunaLlegada))
                .filter(viaje -> viaje.existeDisponibilidad(nroPasajes))
                .map(viaje -> new String[]{
                        viaje.getBus().getPatente().toUpperCase(),
                        viaje.getHora().toString(),
                        String.valueOf(viaje.getPrecio()),
                        String.valueOf(viaje.getNroAsientosDisponibles())
                }).toArray(String[][]::new);
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
            throw new SistemaVentaPasajesException(":::: No existe una Venta con el Id y Tipo de Documentos indicados");
        }

        if (buscarViaje.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Viaje con la Fecha, Hora y Patente de bus indicados");
        }

        if (buscarPasajero.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Pasajero con el Id indicado");
        }

        Viaje viaje = buscarViaje.get();

        if (!viaje.asientoDisponible(asiento)) {
            throw new SistemaVentaPasajesException(":::: El Asiento indicado ya esta ocupado");
        }

        if (asiento < 1 || asiento > viaje.getBus().getNroAsientos()) {
            throw new SistemaVentaPasajesException(":::: El numero del Asiento no es valido");
        }

        Venta venta = buscarVenta.get();

        venta.createPasaje(asiento, viaje, buscarPasajero.get());
    }

    public void pagaVenta(String idDocumento, TipoDocumento tipo) throws SistemaVentaPasajesException {
        Optional<Venta> buscarVenta = findVenta(idDocumento, tipo);

        if (buscarVenta.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe una Venta con el Id y Tipo de Documento indicados");
        }

        boolean pagado = buscarVenta.get().pagaMonto();

        if (!pagado) {
            throw new SistemaVentaPasajesException(":::: La Venta ya fue pagada");
        }
    }

    public void pagaVenta(String idDocumento, TipoDocumento tipo, long nroTarjeta) throws SistemaVentaPasajesException {
        Optional<Venta> buscarVenta = findVenta(idDocumento, tipo);

        if (buscarVenta.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe una Venta con el Id y Tipo de Documento indicados");
        }

        buscarVenta.get().pagaMonto(nroTarjeta);
    }

    public String[][] listVentas() {
        return ventas.stream()
                .map(venta -> new String[] {
                        venta.getIdDocumento(),
                        venta.getTipo().toString(),
                        venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        venta.getCliente().getIdPersona().toString(),
                        venta.getCliente().getNombreCompleto().toString(),
                        String.valueOf(venta.getPasajes().length),
                        String.valueOf(venta.getMonto())
                }).toArray(String[][]::new);
    }

    public String[][] listViajes() {
        return viajes.stream()
                .map(viaje -> new String[] {
                        viaje.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        viaje.getHora().toString(),
                        viaje.getFechaHoraTermino().format(DateTimeFormatter.ofPattern("HH:mm")),
                        String.valueOf(viaje.getPrecio()),
                        String.valueOf(viaje.getNroAsientosDisponibles()),
                        viaje.getBus().getPatente().toUpperCase(),
                        viaje.getTerminalSalida().getDireccion().getComuna(),
                        viaje.getTerminalLlegada().getDireccion().getComuna()
                }).toArray(String[][]::new);
    }

    public String[][] listPasajeros(LocalDate fecha, LocalTime hora, String patBus) {
        Optional<Viaje> buscarViaje = findViaje(fecha, hora, patBus);

        if (buscarViaje.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe un Viaje con la Fecha, Hora y Patente indicados");
        }

        return buscarViaje.get().getListaPasajeros();
    }
    public void readDatosIniciales() throws SistemaVentaPasajesException {
        IOSVP iosvp = new IOSVP();
        Object[] objetos = iosvp.readDatosIniciales();


        clientes.clear();
        pasajeros.clear();
        viajes.clear();
        ventas.clear();


        controlador.setDatosIniciales(objetos);


        for (Object obj : objetos) {
            if (obj instanceof Cliente cliente) {
                if (findCliente(cliente.getIdPersona()).isEmpty()) {
                    clientes.add(cliente);
                }
            } else if (obj instanceof Pasajero pasajero) {
                if (findPasajero(pasajero.getIdPersona()).isEmpty()) {
                    pasajeros.add(pasajero);
                }
            } else if (obj instanceof Viaje viaje) {

                Optional<Viaje> existente = findViaje(viaje.getFecha(), viaje.getHora(), viaje.getBus().getPatente());
                if (existente.isEmpty()) {
                    viajes.add(viaje);
                }
            }
        }

    }
    public void saveDatosSistema() throws SistemaVentaPasajesException {
        IOSVP iosvp = new IOSVP();
        iosvp.saveControladores(this, controlador);
    }
    public void readDatosSistema() throws SistemaVentaPasajesException {
        IOSVP iosvp = new IOSVP();
        Object[] objetos = iosvp.readControladores();

        if (objetos.length >= 2) {
            SistemaVentaPasajes sistemaPersistido = (SistemaVentaPasajes) objetos[0];
            ControladorEmpresas controladorPersistido = (ControladorEmpresas) objetos[1];

            this.clientes.clear();
            this.clientes.addAll(sistemaPersistido.clientes);
            this.pasajeros.clear();
            this.pasajeros.addAll(sistemaPersistido.pasajeros);
            this.viajes.clear();
            this.viajes.addAll(sistemaPersistido.viajes);
            this.ventas.clear();
            this.ventas.addAll(sistemaPersistido.ventas);

            controlador.setInstanciaPersistente(controladorPersistido);
        }
    }


    // BUSCADORES



    private Optional<Cliente> findCliente(IdPersona id) {
        return clientes.stream().filter(cliente -> cliente.getIdPersona().equals(id)).findFirst();
    }

    private Optional<Venta> findVenta(String idDocumento, TipoDocumento tipoDocumento) {
        return ventas.stream()
                .filter(venta -> venta.getIdDocumento().equalsIgnoreCase(idDocumento) &&
                        venta.getTipo().equals(tipoDocumento)).findFirst();
    }

    private Optional<Viaje> findViaje(LocalDate fecha, LocalTime hora, String patenteBus) {
        return viajes.stream().filter(viaje -> viaje.getFecha().equals(fecha) &&
                viaje.getHora().equals(hora) && viaje.getBus().getPatente().equalsIgnoreCase(patenteBus.trim())).findFirst();
    }

    private Optional<Pasajero> findPasajero(IdPersona idPersona) {
        return pasajeros.stream().filter(pasajero -> pasajero.getIdPersona().equals(idPersona)).findFirst();
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

    public void generatePasajesVenta(String idDocumento, TipoDocumento tipo) throws SistemaVentaPasajesException {
        Optional<Venta> ventaOpt = findVenta(idDocumento, tipo);
        if (ventaOpt.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe una Venta con el Id y Tipo de Documento indicados");
        }
        Venta venta = ventaOpt.get();
        venta.generatePasajesVenta();
    }
}

