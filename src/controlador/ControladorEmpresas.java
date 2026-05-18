package controlador;

import utilidades.*;
import excepciones.*;
import modelo.*;

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
            throw new SistemaVentaPasajesException(":::: Ya existe Bus con la patente indicada");
        }

        Optional<Empresa> buscarEmpresa = findEmpresa(rutEmp);

        if (buscarEmpresa.isEmpty()) {
            throw new SistemaVentaPasajesException(":::: No existe empresa con el rut indicado");
        }

        Empresa empresa = buscarEmpresa.get();

        Bus nuevo = new Bus(patente, nroAsientos);
        nuevo.setMarca(marca);
        nuevo.setModelo(modelo);


        empresa.addBus(nuevo);
    }

    public void createTerminal(String nombre, Direccion direccion) {

    }

    public void hireConductorForEmpresa(String rutEmp, IdPersona id, Nombre nom, Direccion dir) {

    }

    public void hireAuxiliarForEmpresa(String rutEmp, IdPersona id, Nombre nom, Direccion dir) {

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



    // BUSCADORES


    protected Optional<Empresa> findEmpresa(Rut rut) {
        for (Empresa e : empresas) {
            if (e.getRut().equals(rut)) {
                return Optional.of(e);
            }
        }

        return Optional.empty();
    }

    protected Optional<Bus> findBus(String patente) {
        for (Empresa e : empresas) {
            for (Bus b : e.getBuses()) {
                if (b.getPatente().equals(patente)) {
                    return Optional.of(b);
                }
            }
        }

        return Optional.empty();
    }
}
