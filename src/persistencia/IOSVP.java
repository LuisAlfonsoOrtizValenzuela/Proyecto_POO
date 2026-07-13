package persistencia;

import controlador.ControladorEmpresas;
import excepciones.SVPException;
import modelo.*;
import utilidades.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IOSVP {
    /*private static IOSVP instance;

    private IOSVP() {

    }

    public static IOSVP getInstance() {
        if (instance == null) {
            instance = new IOSVP();
        }
        return instance;
    }*/

    private static final String ARCHIVO_INICIAL = "SVPDatosIniciales.txt";
    private static final String ARCHIVO_OBJETOS = "SVPObjetos.obj";

    public Object[] readDatosIniciales() throws SVPException {
        ArrayList<Object> objetos = new ArrayList<>();


        Map<String, Empresa> empresasMap = new HashMap<>();
        Map<String, Bus> busesMap = new HashMap<>();
        Map<String, Terminal> terminalesMap = new HashMap<>();
        Map<String, Auxiliar> auxiliaresMap = new HashMap<>();
        Map<String, Conductor> conductoresMap = new HashMap<>();
        List<ViajeData> viajesData = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_INICIAL))) {
            String linea;
            int seccion = 0;

            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue;

                if (linea.equals("+")) {
                    seccion++;
                    continue;
                }

                switch (seccion) {
                    case 0:
                        procesarClientePasajero(linea, objetos);
                        break;
                    case 1:
                        procesarEmpresa(linea, objetos, empresasMap);
                        break;
                    case 2:
                        procesarTripulante(linea, objetos, empresasMap, auxiliaresMap, conductoresMap);
                        break;
                    case 3:
                        procesarTerminal(linea, objetos, terminalesMap);
                        break;
                    case 4:
                        procesarBus(linea, objetos, empresasMap, busesMap);
                        break;
                    case 5:
                        procesarViaje(linea, objetos, viajesData);
                        break;
                }
            }

            procesarViajesPendientes(viajesData, objetos, busesMap, auxiliaresMap, conductoresMap, terminalesMap);

        } catch (FileNotFoundException e) {
            throw new SVPException("No existe o No se puede abrir el Archivo " + ARCHIVO_INICIAL);
        } catch (IOException e) {
            throw new SVPException("Error al intentar leer el Archivo " + ARCHIVO_INICIAL);
        }

        return objetos.toArray();
    }

    private void procesarClientePasajero(String linea, ArrayList<Object> objetos) throws SVPException {
        String[] partes = linea.split(";");
        if (partes.length < 8) {
            throw new SVPException("Linea de Cliente/Pasajero Incompleta: " + linea);
        }

        String tipo = partes[0];
        String rut = partes[1];
        String tratamiento = partes[2];
        String nombres = partes[3];
        String apPaterno = partes[4];
        String apMaterno = partes[5];
        String telefono = partes[6];

        try {
            IdPersona id;
            try {
                id = Rut.of(rut);
            } catch (Exception e) {
                id = Pasaporte.of(rut, "CHILE");
            }

            Nombre nombre = new Nombre();
            nombre.setTratamiento(Tratamiento.valueOf(tratamiento));
            nombre.setNombre(nombres);
            nombre.setApellido_paterno(apPaterno);
            nombre.setApellido_materno(apMaterno);

            if (tipo.equals("C") || tipo.equals("CP")) {
                String email = partes[7];
                Cliente cliente = new Cliente(nombre, id, telefono, email);
                objetos.add(cliente);
            }

            if (tipo.equals("P") || tipo.equals("CP")) {
                int offset = tipo.equals("CP") ? 8 : 7;
                if (partes.length >= offset + 5) {
                    String tratContacto = partes[offset];
                    String nomContacto = partes[offset + 1];
                    String apPatContacto = partes[offset + 2];
                    String apMatContacto = partes[offset + 3];
                    String fonoContacto = partes[offset + 4];

                    Nombre nombreContacto = new Nombre();
                    nombreContacto.setTratamiento(Tratamiento.valueOf(tratContacto));
                    nombreContacto.setNombre(nomContacto);
                    nombreContacto.setApellido_paterno(apPatContacto);
                    nombreContacto.setApellido_materno(apMatContacto);

                    Pasajero pasajero = new Pasajero(nombre, id, telefono, nombreContacto, fonoContacto);
                    objetos.add(pasajero);
                } else {
                    throw new SVPException("Datos de Contacto incompletos para Pasajero: " + linea);
                }
            }
        } catch (Exception e) {
            throw new SVPException("Error al intentar procesar Cliente/Pasajero: " + e.getMessage());
        }
    }

    private void procesarEmpresa(String linea, ArrayList<Object> objetos, Map<String, Empresa> empresasMap) throws SVPException {
        String[] partes = linea.split(";");
        if (partes.length < 3) {
            throw new SVPException("Linea de Empresa Incompleta: " + linea);
        }

        try {
            Rut rut = Rut.of(partes[0]);
            String nombre = partes[1];
            String url = partes[2];
            Empresa empresa = new Empresa(rut, nombre);
            empresa.setUrl(url);
            objetos.add(empresa);
            empresasMap.put(partes[0], empresa);
        } catch (Exception e) {
            throw new SVPException("Error al intentar procesar Empresa: " + e.getMessage());
        }
    }

    private void procesarTripulante(String linea, ArrayList<Object> objetos, Map<String, Empresa> empresasMap,
                                    Map<String, Auxiliar> auxiliaresMap, Map<String, Conductor> conductoresMap) throws SVPException {
        String[] partes = linea.split(";");
        if (partes.length < 10) {
            throw new SVPException("Linea de Tripulante Incompleta: " + linea);
        }

        String tipo = partes[0];
        String rut = partes[1];
        String tratamiento = partes[2];
        String nombres = partes[3];
        String apPaterno = partes[4];
        String apMaterno = partes[5];
        String calle = partes[6];
        String numero = partes[7];
        String comuna = partes[8];
        String rutEmpresa = partes[9];

        try {
            IdPersona id;
            try {
                id = Rut.of(rut);
            } catch (Exception e) {
                id = Pasaporte.of(rut, "CHILE");
            }

            Nombre nombre = new Nombre();
            nombre.setTratamiento(Tratamiento.valueOf(tratamiento));
            nombre.setNombre(nombres);
            nombre.setApellido_paterno(apPaterno);
            nombre.setApellido_materno(apMaterno);

            Direccion direccion = new Direccion(calle, Integer.parseInt(numero), comuna);

            Empresa empresa = empresasMap.get(rutEmpresa);
            if (empresa == null) {
                throw new SVPException("Empresa no Encontrada para Tripulante: " + rutEmpresa);
            }

            if (tipo.equals("A")) {
                Auxiliar auxiliar = new Auxiliar(id, nombre, null, direccion);
                empresa.addAuxiliar(id, nombre, null, direccion);
                objetos.add(auxiliar);
                auxiliaresMap.put(rut, auxiliar);
            } else if (tipo.equals("C")) {
                Conductor conductor = new Conductor(id, nombre, null, direccion);
                empresa.addConductor(id, nombre, null, direccion);
                objetos.add(conductor);
                conductoresMap.put(rut, conductor);
            } else {
                throw new SVPException("Tipo de Tripulante Invalido: " + tipo);
            }
        } catch (Exception e) {
            throw new SVPException("Error al intentar procesar Tripulante: " + e.getMessage());
        }
    }

    private void procesarTerminal(String linea, ArrayList<Object> objetos, Map<String, Terminal> terminalesMap) throws SVPException {
        String[] partes = linea.split(";");
        if (partes.length < 4) {
            throw new SVPException("Linea de Terminal Incompleta: " + linea);
        }

        try {
            String nombre = partes[0].trim();
            String calle = partes[1].trim();
            String numero = partes[2].trim();
            String comuna = partes[3].trim();

            Direccion direccion = new Direccion(calle, Integer.parseInt(numero), comuna);
            Terminal terminal = new Terminal(nombre, direccion);
            objetos.add(terminal);
            terminalesMap.put(nombre, terminal);
        } catch (NumberFormatException e) {
            throw new SVPException("Error al intentar procesar Terminal - Numero de Direccion Invalido: " + e.getMessage());
        } catch (Exception e) {
            throw new SVPException("Error al intentar procesar Terminal: " + e.getMessage());
        }
    }

    private void procesarBus(String linea, ArrayList<Object> objetos, Map<String, Empresa> empresasMap, Map<String, Bus> busesMap) throws SVPException {
        String[] partes = linea.split(";");
        if (partes.length < 5) return;

        String patente = partes[0];
        String marca = partes[1];
        String modelo = partes[2];
        int nroAsientos;
        String rutEmpresa;

        try {
            nroAsientos = Integer.parseInt(partes[3]);
            rutEmpresa = partes[4];

            Empresa empresa = empresasMap.get(rutEmpresa);
            if (empresa == null) {
                throw new SVPException("Empresa no Encontrada para Bus: " + rutEmpresa);
            }

            Bus bus = new Bus(patente, nroAsientos, empresa);
            bus.setMarca(marca);
            bus.setModelo(modelo);

            empresa.addBus(bus);
            objetos.add(bus);
            busesMap.put(patente, bus);
        } catch (NumberFormatException e) {
            throw new SVPException("Error al intentar procesar Bus - Numero de Asientos Invalido: " + e.getMessage());
        } catch (Exception e) {
            throw new SVPException("Error al intentar procesar Bus: " + e.getMessage());
        }
    }

    private void procesarViaje(String linea, ArrayList<Object> objetos, List<ViajeData> viajesData) throws SVPException {
        String[] partes = linea.split(";");
        if (partes.length < 9) {
            throw new SVPException("Linea de Viaje Incompleta: " + linea);
        }

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fecha = LocalDate.parse(partes[0].trim(), dateFormatter);
            LocalTime hora = LocalTime.parse(partes[1].trim());
            int precio = Integer.parseInt(partes[2].trim());
            int duracion = Integer.parseInt(partes[3].trim());
            String patente = partes[4].trim();
            String rutAuxiliar = partes[5].trim();
            String rutConductor = partes[6].trim();
            String terminalSalida = partes[7].trim();
            String terminalLlegada = partes[8].trim();

            viajesData.add(new ViajeData(fecha, hora, precio, duracion, patente,
                    rutAuxiliar, rutConductor, terminalSalida, terminalLlegada));
        } catch (Exception e) {
            throw new SVPException("Error al intentar procesar Viaje: " + e.getMessage());
        }
    }

    private void procesarViajesPendientes(List<ViajeData> viajesData, ArrayList<Object> objetos,
                                          Map<String, Bus> busesMap, Map<String, Auxiliar> auxiliaresMap,
                                          Map<String, Conductor> conductoresMap, Map<String, Terminal> terminalesMap) throws SVPException {
        for (ViajeData data : viajesData) {
            Bus bus = busesMap.get(data.patente);
            Auxiliar auxiliar = auxiliaresMap.get(data.rutAuxiliar);
            Conductor conductor = conductoresMap.get(data.rutConductor);
            Terminal salida = resolverTerminal(data.terminalSalida, terminalesMap);
            Terminal llegada = resolverTerminal(data.terminalLlegada, terminalesMap);

            if (bus == null) {
                throw new SVPException("Bus no encontrado para Viaje: " + data.patente);
            }
            if (auxiliar == null) {
                throw new SVPException("Auxiliar no encontrado para Viaje: " + data.rutAuxiliar);
            }
            if (conductor == null) {
                throw new SVPException("Conductor no encontrado para Viaje: " + data.rutConductor);
            }
            if (salida == null) {
                throw new SVPException("Terminal de salida No encontrado: " + data.terminalSalida);
            }
            if (llegada == null) {
                throw new SVPException("Terminal de llegada No encontrado: " + data.terminalLlegada);
            }

            Viaje viaje = new Viaje(data.fecha, data.hora, data.precio, data.duracion,
                    bus, auxiliar, conductor, salida, llegada);
            objetos.add(viaje);

            bus.addViaje(viaje);
            auxiliar.addViaje(viaje);
            conductor.addViaje(viaje);
            salida.addSalida(viaje);
            llegada.addLlegada(viaje);
        }
    }


    private Terminal resolverTerminal(String valor, Map<String, Terminal> terminalesMap) {
        String clave = valor.trim();
        Terminal terminal = terminalesMap.get(clave);
        if (terminal != null) {
            return terminal;
        }

        for (Terminal t : terminalesMap.values()) {
            if (t.getDireccion().getComuna().equalsIgnoreCase(clave)) {
                return t;
            }
        }

        return null;
    }

    private static class ViajeData {
        LocalDate fecha;
        LocalTime hora;
        int precio;
        int duracion;
        String patente;
        String rutAuxiliar;
        String rutConductor;
        String terminalSalida;
        String terminalLlegada;

        ViajeData(LocalDate fecha, LocalTime hora, int precio, int duracion, String patente,
                  String rutAuxiliar, String rutConductor, String terminalSalida, String terminalLlegada) {
            this.fecha = fecha;
            this.hora = hora;
            this.precio = precio;
            this.duracion = duracion;
            this.patente = patente;
            this.rutAuxiliar = rutAuxiliar;
            this.rutConductor = rutConductor;
            this.terminalSalida = terminalSalida;
            this.terminalLlegada = terminalLlegada;
        }
    }


    public void saveControladores(Object controlador1, Object controlador2) throws SVPException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_OBJETOS))) {
            oos.writeObject(controlador1);
            oos.writeObject(controlador2);
        } catch (FileNotFoundException e) {
            throw new SVPException("No se puede Abrir o Crear el Archivo " + ARCHIVO_OBJETOS);
        } catch (IOException e) {
            throw new SVPException("No se puede Grabar en el Archivo " + ARCHIVO_OBJETOS);
        }
    }

    public Object[] readControladores() throws SVPException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_OBJETOS))) {
            Object obj1 = ois.readObject();
            Object obj2 = ois.readObject();
            return new Object[]{obj1, obj2};
        } catch (FileNotFoundException e) {
            throw new SVPException("No existe o No se puede Abrir el Archivo " + ARCHIVO_OBJETOS);
        } catch (IOException | ClassNotFoundException e) {
            throw new SVPException("No se puede Leer el Archivo " + ARCHIVO_OBJETOS);
        }
    }

    public void savePasajesDeVenta(String[] pasajes, String nombreArchivo) throws SVPException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (String pasaje : pasajes) {
                bw.write(pasaje);
                bw.newLine();
                bw.newLine();
            }
        } catch (IOException e) {
            throw new SVPException("No se puede Abrir o Crear el Archivo " + nombreArchivo);
        }
    }

    // BUSCADORESS

    private Optional<Empresa> findEmpresa(ArrayList<Empresa> empresas, Rut rut) {
        return empresas.stream().filter(empresa -> empresa.getRut().equals(rut)).findFirst();
    }

    private Optional<Tripulante> findTripulante(Empresa empresa, IdPersona id) {
        return Arrays.stream(empresa.getTripulantes())
                .filter(tripulante -> tripulante.getIdPersona().equals(id))
                .filter(tripulante -> tripulante instanceof Conductor || tripulante instanceof Auxiliar)
                .findFirst();
    }

    private Optional<Bus> findBus(ArrayList<Bus> buses, String patente) {
        return buses.stream().filter(bus -> bus.getPatente().equalsIgnoreCase(patente)).findFirst();
    }

    private Optional<Terminal> findTerminal(ArrayList<Terminal> terminales, String nombre) {
        return terminales.stream().filter(terminal -> terminal.getNombre().equalsIgnoreCase(nombre)).findFirst();
    }
}