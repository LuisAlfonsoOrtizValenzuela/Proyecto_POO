package controlador;

import utilidades.*;
import excepciones.*;
import modelo.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;


public class ControladorEmpresas {
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

        Bus nuevo = new Bus(patente.trim(), nroAsientos);
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
        ArrayList<String[]> lista = new ArrayList<>();

        for (Empresa e : empresas) {
            String[] row = {
                    e.getRut().toString(),
                    e.getNombre(),
                    e.getUrl(),
                    String.valueOf(e.getTripulantes().length),
                    String.valueOf(e.getBuses().length),
                    String.valueOf(e.getVentas().length)
            };
            lista.add(row);
        }
        return lista.toArray(new String[0][0]);
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

    public String[][] listVentasEmpresa(Rut rut) {
        Optional<Empresa> buscarEmpresa = findEmpresa(rut);

        if (buscarEmpresa.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe una Empresa con el rut indicado");
        }

        Empresa empresa = buscarEmpresa.get();
        ArrayList<String[]> lista = new ArrayList<>();

        for (Venta v : empresa.getVentas()) {
            String[] row = {
                    v.getFecha().toString(),
                    v.getTipo().toString().toLowerCase(),
                    String.valueOf(v.getMonto()),
                    "Pendiente"
            };
            lista.add(row);
        }

        return lista.toArray(new String[0][0]);
    }




    // BUSCADORES


    protected Optional<Empresa> findEmpresa(Rut rut) {
        for (Empresa e : empresas) {
            if (e.getRut().equals(rut)) {
                return Optional.of(e);
            }
        }

        return Optional.empty();
    }

    protected Optional<Terminal> findTerminal(String nombre) {
        for (Terminal t : terminales) {
            if (t.getNombre().equalsIgnoreCase(nombre)) {

                return Optional.of(t);
            }
        }

        return Optional.empty();
    }

    protected Optional<Terminal> findTerminalPorComuna(String comuna) {
        for (Terminal t : terminales) {
            if (t.getDireccion().getComuna().equalsIgnoreCase(comuna)) {
                return Optional.of(t);
            }
        }

        return Optional.empty();
    }

    protected Optional<Bus> findBus(String patente) {
        for (Empresa e : empresas) {
            for (Bus b : e.getBuses()) {
                if (b.getPatente().trim().equalsIgnoreCase(patente.trim())) {
                    return Optional.of(b);
                }
            }
        }

        return Optional.empty();
    }

    protected Optional<Auxiliar> findAuxiliar(IdPersona idPersona) {
        for (Empresa e : empresas) {
            for (Tripulante t : e.getTripulantes()) {
                if (t instanceof Auxiliar && t.getIdPersona().equals(idPersona)) {

                    return Optional.of((Auxiliar) t);
                }
            }
        }

        return Optional.empty();
    }

    protected Optional<Conductor> findConductor(IdPersona idPersona) {
        for (Empresa e : empresas) {
            for (Tripulante t : e.getTripulantes()) {
                if (t instanceof Conductor && t.getIdPersona().equals(idPersona)) {

                    return Optional.of((Conductor) t);
                }
            }
        }

        return Optional.empty();
    }
}
