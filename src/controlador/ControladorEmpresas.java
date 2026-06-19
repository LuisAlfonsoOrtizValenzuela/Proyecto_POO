package controlador;

import utilidades.*;
import excepciones.*;
import modelo.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


public class ControladorEmpresas implements Serializable {
    private static final long serialVersionUID = 1L;
    private static ControladorEmpresas instance;

    private final ArrayList<Empresa> empresas;
    private final ArrayList<Bus> buses;
    private final ArrayList<Terminal> terminales;

    private ControladorEmpresas() {
        this.empresas = new ArrayList<>();
        this.buses = new ArrayList<>();
        this.terminales = new ArrayList<>();
    }

    public static ControladorEmpresas getInstance() {
        if (instance == null) {
            instance = new ControladorEmpresas();
        }
        return instance;
    }



    public void createEmpresa(Rut rut, String nombre, String url) {
        Optional<Empresa> buscarEmpresa = findEmpresa(rut);

            if (buscarEmpresa.isPresent()) {
                throw new SistemaVentaPasajesException(":::: Ya existe una Empresa con el rut ingresado");
            }

        Empresa nuevo = new Empresa(rut, nombre);
        nuevo.setUrl(url);

        empresas.add(nuevo);
    }

    public void createBus(String patente, String marca, String modelo, int nroAsientos, Rut rutEmp) {
        Optional<Bus> buscarBus = findBus(patente);

        if (buscarBus.isPresent()) {
            throw new SistemaVentaPasajesException(":::: Ya existe un Bus con la patente indicada");
        }

        Optional<Empresa> buscarEmpresa = findEmpresa(rutEmp);

        if (buscarEmpresa.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe una Empresa con el rut indicado");
        }

        Empresa empresa = buscarEmpresa.get();

        Bus nuevo = new Bus(patente.trim(), nroAsientos, empresa);
        nuevo.setMarca(marca);
        nuevo.setModelo(modelo);


        empresa.addBus(nuevo);
    }

    public void createTerminal(String nombre, Direccion direccion) {
        Optional<Terminal> buscarTerminal = findTerminal(nombre);
        Optional<Terminal> buscarTerminalPorComuna = findTerminalPorComuna(direccion.getComuna());

        if (buscarTerminal.isPresent()) {
            throw new SistemaVentaPasajesException(":::: Ya existe un Terminal con el nombre indicado");
        }

        if (buscarTerminalPorComuna.isPresent()) {
            throw new SistemaVentaPasajesException(":::: Ya existe un Terminal en la comuna indicada");
        }

        Terminal nuevo = new Terminal(nombre, direccion);

        terminales.add(nuevo);
    }

    public void hireConductorForEmpresa(String rut, IdPersona id, Nombre nom, Direccion dir) {
        Optional<Empresa> buscarEmpresa = findEmpresa(Rut.of(rut));

        if (buscarEmpresa.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe una Empresa con el rut indicado");
        }

        Empresa empresa = buscarEmpresa.get();
        boolean contratado = empresa.addConductor(id, nom, null, dir);

        if (!contratado) {
            throw new SistemaVentaPasajesException(":::: Ya esta contratado un Auxiliar/Conductor con el id dado en la empresa señalada");
        }
    }

    public void hireAuxiliarForEmpresa(String rut, IdPersona id, Nombre nom, Direccion dir) {

        Optional<Empresa> buscarEmpresa = findEmpresa(Rut.of(rut));

        if (buscarEmpresa.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe una Empresa con el rut indicado");
        }

        Empresa empresa = buscarEmpresa.get();
        boolean contratado = empresa.addAuxiliar(id, nom, null, dir);

        if (!contratado) {
            throw new SistemaVentaPasajesException(":::: Ya esta contratado Auxiliar/Conductor con el id dado en la empresa señalada");
        }

    }

    public String[][] listEmpresas() {
        return empresas.stream()
                .map(empresa -> new String[] {
                        empresa.getRut().toString(),
                        empresa.getNombre(),
                        empresa.getUrl(),
                        String.valueOf(empresa.getTripulantes().length),
                        String.valueOf(empresa.getBuses().length),
                        String.valueOf(empresa.getVentas().length)
                }).toArray(String[][]::new);
    }

    public String[][] listLlegadasSalidasTerminal(String nombre, LocalDate fecha) {
        Optional<Terminal> buscarTerminal = findTerminal(nombre);

        if (buscarTerminal.isEmpty()) {

            throw new SistemaVentaPasajesException(":::: No existe un Terminal con el nombre dado");
        }
        Terminal terminal = buscarTerminal.get();
        ArrayList<String[]> lista = new ArrayList<>();

        //veri

        for (Viaje v : terminal.getSalidas()) {
            if (v.getFecha().equals(fecha)) {

                String[] row = getStrings(v);
                lista.add(row);
            }
        }

        for (Viaje v : terminal.getLlegadas()) {
            if (v.getFecha().equals(fecha)) {
                String nombreEmpresa = "";
                for (Empresa e : empresas) {
                    for (Bus b : e.getBuses()) {
                        if (b.getPatente().equals(v.getBus().getPatente())) {
                            nombreEmpresa = e.getNombre();
                            break;
                        }
                    }
                }
                String[] row = {
                        "Llegada",
                        v.getFechaHoraTermino().toLocalTime().toString(),
                        v.getBus().getPatente(),
                        nombreEmpresa,
                        String.valueOf(v.getBus().getNroAsientos() - v.getNroAsientosDisponibles())
                };
                lista.add(row);
            }


        }
        return lista.toArray(new String[0][0]);
    }

    private String[] getStrings(Viaje v) {
        String nombreEmpresa = "";
        for (Empresa e : empresas) {
            for (Bus b : e.getBuses()) {
                if (b.getPatente().equals(v.getBus().getPatente())) {
                    nombreEmpresa = e.getNombre();
                    break;
                }
            }
        }

        String[] row = {
                "Salida",
                v.getHora().toString(),
                v.getBus().getPatente(),
                nombreEmpresa,
                String.valueOf(v.getBus().getNroAsientos() - v.getNroAsientosDisponibles())
        };
        return row;
    }

    public String[][] listVentasEmpresa(Rut rut) {
        Optional<Empresa> buscarEmpresa = findEmpresa(rut);

        if (buscarEmpresa.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe una Empresa con el rut indicado");
        }

        Empresa empresa = buscarEmpresa.get();
        ArrayList<String[]> lista = new ArrayList<>();

        return Arrays.stream(buscarEmpresa.get().getVentas())
                .map(venta -> {
                    String tipoPago;
                    String montoPagado;

                    if (venta.getTipoPago() == null) {
                        tipoPago = "PENDIENTE";
                        montoPagado = "0";
                    } else {
                        tipoPago = venta.getTipoPago();
                        montoPagado = String.valueOf(venta.getMontoPagado());
                    }

                    return new String[]{
                            venta.getFecha().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                            venta.getTipo().toString(),
                            montoPagado,
                            tipoPago
                    };
                }).toArray(String[][]::new);
    }




    // BUSCADORES


    protected Optional<Empresa> findEmpresa(Rut rut) {
        return empresas.stream().filter(empresa -> empresa.getRut().equals(rut)).findFirst();
    }

    protected Optional<Terminal> findTerminal(String nombre) {
        return terminales.stream().filter(terminal -> terminal.getNombre().equalsIgnoreCase(nombre)).findFirst();
    }

    protected Optional<Terminal> findTerminalPorComuna(String comuna) {
        return terminales.stream().filter(terminal -> terminal.getDireccion().getComuna().equalsIgnoreCase(comuna)).findFirst();
    }

    protected Optional<Bus> findBus(String patente) {
        return empresas.stream().flatMap(empresa -> Arrays.stream(empresa.getBuses()))
                .filter(bus -> bus.getPatente().equalsIgnoreCase(patente.trim())).findFirst();
    }

    protected Optional<Auxiliar> findAuxiliar(IdPersona idPersona) {
        return empresas.stream().flatMap(empresa -> Arrays.stream(empresa.getTripulantes()))
                .filter(tripulante -> tripulante instanceof Auxiliar)
                .map(tripulante -> (Auxiliar) tripulante)
                .filter(auxiliar -> auxiliar.getIdPersona().equals(idPersona)).findFirst();
    }

    protected Optional<Conductor> findConductor(IdPersona idPersona) {
        return empresas.stream().flatMap(empresa -> Arrays.stream(empresa.getTripulantes()))
                .filter(tripulante -> tripulante instanceof Conductor)
                .map(tripulante -> (Conductor) tripulante)
                .filter(conductor -> conductor.getIdPersona().equals(idPersona)).findFirst();
    }

    public void setInstanciaPersistente(ControladorEmpresas c) {
        this.empresas.clear();
        this.empresas.addAll(c.empresas);
        this.buses.clear();
        this.buses.addAll(c.buses);
        this.terminales.clear();
        this.terminales.addAll(c.terminales);
    }

    public void setDatosIniciales(Object[] objetos) throws SistemaVentaPasajesException {

        this.empresas.clear();
        this.buses.clear();
        this.terminales.clear();


        for (Object obj : objetos) {
            if (obj instanceof Empresa emp) {
                Optional<Empresa> existente = findEmpresa(emp.getRut());
                if (existente.isEmpty()) {
                    empresas.add(emp);
                }
            } else if (obj instanceof Terminal term) {
                Optional<Terminal> existente = findTerminal(term.getNombre());
                if (existente.isEmpty()) {
                    terminales.add(term);
                }
            } else if (obj instanceof Bus bus) {
                buses.add(bus);
            }
        }


        for (Object obj : objetos) {
            if (obj instanceof Viaje viaje) {

                Bus bus = viaje.getBus();
                if (bus != null && !buses.contains(bus)) {
                    buses.add(bus);
                }
            }
        }
    }
}
