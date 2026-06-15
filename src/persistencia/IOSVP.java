package persistencia;

import excepciones.SistemaVentaPasajesException;
import modelo.*;
import utilidades.*;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class IOSVP {

    private static final String ARCHIVO_INICIAL = "SVPDatosIniciales.txt";
    private static final String ARCHIVO_OBJETOS = "SVPObjetos.obj";

    public Object[] readDatosIniciales() throws SistemaVentaPasajesException {
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

            // Procesar los viajes después de tener todos los objetos
            procesarViajesPendientes(viajesData, objetos, busesMap, auxiliaresMap, conductoresMap, terminalesMap);

        } catch (FileNotFoundException e) {
            throw new SistemaVentaPasajesException("No existe o no se puede abrir el archivo " + ARCHIVO_INICIAL);
        } catch (IOException e) {
            throw new SistemaVentaPasajesException("Error al leer el archivo " + ARCHIVO_INICIAL);
        }

        return objetos.toArray();
    }

    private void procesarClientePasajero(String linea, ArrayList<Object> objetos) throws SistemaVentaPasajesException {
        String[] partes = linea.split(";");
        if (partes.length < 8) return;

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
                }
            }
        } catch (Exception e) {
            throw new SistemaVentaPasajesException("Error al procesar cliente/pasajero: " + e.getMessage());
        }
    }

    private void procesarEmpresa(String linea, ArrayList<Object> objetos, Map<String, Empresa> empresasMap) throws SistemaVentaPasajesException {
        String[] partes = linea.split(";");
        if (partes.length < 3) return;

        try {
            Rut rut = Rut.of(partes[0]);
            String nombre = partes[1];
            String url = partes[2];
            Empresa empresa = new Empresa(rut, nombre);
            empresa.setUrl(url);
            objetos.add(empresa);
            empresasMap.put(partes[0], empresa);
        } catch (Exception e) {
            throw new SistemaVentaPasajesException("Error al procesar empresa: " + e.getMessage());
        }
    }

    private void procesarTripulante(String linea, ArrayList<Object> objetos, Map<String, Empresa> empresasMap,
                                    Map<String, Auxiliar> auxiliaresMap, Map<String, Conductor> conductoresMap) throws SistemaVentaPasajesException {
        String[] partes = linea.split(";");
        if (partes.length < 10) return;

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
                throw new SistemaVentaPasajesException("Empresa no encontrada para tripulante: " + rutEmpresa);
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
            }
        } catch (Exception e) {
            throw new SistemaVentaPasajesException("Error al procesar tripulante: " + e.getMessage());
        }
    }

    private void procesarTerminal(String linea, ArrayList<Object> objetos, Map<String, Terminal> terminalesMap) throws SistemaVentaPasajesException {
        String[] partes = linea.split(";");
        if (partes.length < 4) return;

        String nombre = partes[0];
        String calle = partes[1];
        String numero = partes[2];
        String comuna = partes[3];

        Direccion direccion = new Direccion(calle, Integer.parseInt(numero), comuna);
        Terminal terminal = new Terminal(nombre, direccion);
        objetos.add(terminal);
        terminalesMap.put(nombre, terminal);
    }

    private void procesarBus(String linea, ArrayList<Object> objetos, Map<String, Empresa> empresasMap, Map<String, Bus> busesMap) throws SistemaVentaPasajesException {
        String[] partes = linea.split(";");
        if (partes.length < 5) return;

        String patente = partes[0];
        String marca = partes[1];
        String modelo = partes[2];
        int nroAsientos = Integer.parseInt(partes[3]);
        String rutEmpresa = partes[4];

        try {
            Bus bus = new Bus(patente, nroAsientos);
            bus.setMarca(marca);
            bus.setModelo(modelo);

            Empresa empresa = empresasMap.get(rutEmpresa);
            if (empresa == null) {
                throw new SistemaVentaPasajesException("Empresa no encontrada para bus: " + rutEmpresa);
            }

            empresa.addBus(bus);
            objetos.add(bus);
            busesMap.put(patente, bus);
        } catch (Exception e) {
            throw new SistemaVentaPasajesException("Error al procesar bus: " + e.getMessage());
        }
    }

    private void procesarViaje(String linea, ArrayList<Object> objetos, List<ViajeData> viajesData) throws SistemaVentaPasajesException {
        String[] partes = linea.split(";");
        if (partes.length < 9) return;

        try {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fecha = LocalDate.parse(partes[0], dateFormatter);
            LocalTime hora = LocalTime.parse(partes[1]);
            int precio = Integer.parseInt(partes[2]);
            int duracion = Integer.parseInt(partes[3]);
            String patente = partes[4];
            String rutAuxiliar = partes[5];
            String rutConductor = partes[6];
            String terminalSalida = partes[7];
            String terminalLlegada = partes[8];

            viajesData.add(new ViajeData(fecha, hora, precio, duracion, patente,
                    rutAuxiliar, rutConductor, terminalSalida, terminalLlegada));
        } catch (Exception e) {
            throw new SistemaVentaPasajesException("Error al procesar viaje: " + e.getMessage());
        }
    }

    private void procesarViajesPendientes(List<ViajeData> viajesData, ArrayList<Object> objetos,
                                          Map<String, Bus> busesMap, Map<String, Auxiliar> auxiliaresMap,
                                          Map<String, Conductor> conductoresMap, Map<String, Terminal> terminalesMap) {
        for (ViajeData data : viajesData) {
            Bus bus = busesMap.get(data.patente);
            Auxiliar auxiliar = auxiliaresMap.get(data.rutAuxiliar);
            Conductor conductor = conductoresMap.get(data.rutConductor);
            Terminal salida = terminalesMap.get(data.terminalSalida);
            Terminal llegada = terminalesMap.get(data.terminalLlegada);

            if (bus != null && auxiliar != null && conductor != null && salida != null && llegada != null) {
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
    }

    // Clase auxiliar para almacenar datos de viaje temporalmente
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


    public void saveControladores(Object controlador1, Object controlador2) throws SistemaVentaPasajesException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_OBJETOS))) {
            oos.writeObject(controlador1);
            oos.writeObject(controlador2);
        } catch (FileNotFoundException e) {
            throw new SistemaVentaPasajesException("No se puede abrir o crear el archivo " + ARCHIVO_OBJETOS);
        } catch (IOException e) {
            throw new SistemaVentaPasajesException("No se puede grabar en el archivo " + ARCHIVO_OBJETOS);
        }
    }

    public Object[] readControladores() throws SistemaVentaPasajesException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARCHIVO_OBJETOS))) {
            Object obj1 = ois.readObject();
            Object obj2 = ois.readObject();
            return new Object[]{obj1, obj2};
        } catch (FileNotFoundException e) {
            throw new SistemaVentaPasajesException("No existe o no se puede abrir el archivo " + ARCHIVO_OBJETOS);
        } catch (IOException | ClassNotFoundException e) {
            throw new SistemaVentaPasajesException("No se puede leer el archivo " + ARCHIVO_OBJETOS);
        }
    }

    public void savePasajesDeVenta(String[] pasajes, String nombreArchivo) throws SistemaVentaPasajesException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo))) {
            for (String pasaje : pasajes) {
                bw.write(pasaje);
                bw.newLine();
                bw.newLine();
            }
        } catch (IOException e) {
            throw new SistemaVentaPasajesException("No se puede abrir o crear el archivo " + nombreArchivo);
        }
    }
}