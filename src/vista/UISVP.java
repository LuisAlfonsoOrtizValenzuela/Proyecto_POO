package vista;

import controlador.*;
import excepciones.SVPException;
import utilidades.*;
import modelo.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Optional;
import java.util.Scanner;

public class UISVP {
    private static UISVP instance;
    SistemaVentaPasajes sistema = SistemaVentaPasajes.getInstance();
    ControladorEmpresas controlador = ControladorEmpresas.getInstance();

    private final Scanner sc = new Scanner(System.in);

    private UISVP() {
    }

    public static UISVP getInstance() {
        if (instance == null) {
            instance = new UISVP();
        }
        return instance;
    }



    public void menu() {
        int opcion = 0;

        do {
            System.out.println("=========================================");
            System.out.println("      ...::: Menu principal :::...");
            System.out.println();
            System.out.println("  1. Crear empresa");
            System.out.println("  2. Contratar tripulante");
            System.out.println("  3. Crear terminal");
            System.out.println("  4. Crear cliente");
            System.out.println("  5. Crear bus");
            System.out.println("  6. Crear viaje");
            System.out.println("  7. Vender pasajes");
            System.out.println("  8. Listar ventas");
            System.out.println("  9. Listar viajes");
            System.out.println(" 10. Listar pasajeros de viaje");
            System.out.println(" 11. Listar empresas");
            System.out.println(" 12. Listar llegadas/salidas de terminal");
            System.out.println(" 13. Listar ventas de empresa");
            System.out.println(" 14. Generar pasajes venta");
            System.out.println(" 15. Leer datos iniciales");
            System.out.println(" 16. Guardar datos del sistema");
            System.out.println(" 17. Leer datos del sistema");
            System.out.println(" 18. Salir");
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.print("...::: Ingrese su opción: ");
            try {
            opcion = sc.nextInt();
            sc.nextLine();


                while (opcion < 1 || opcion > 18) {
                    System.out.println("- LA OPCIÓN INGRESADA NO ES VALIDA...");
                    System.out.print("...::: Ingrese su opción: ");
                    opcion = sc.nextInt();
                    sc.nextLine();
                }

                switch (opcion) {
                    case 1:
                        createEmpresa();
                        break;
                    case 2:
                        contrataTripulante();
                        break;
                    case 3:
                        createTerminal();
                        break;
                    case 4:
                        createCliente();
                        break;
                    case 5:
                        createBus();
                        break;
                    case 6:
                        createViaje();
                        break;
                    case 7:
                        vendePasaje();
                        break;
                    case 8:
                        listVentas();
                        break;
                    case 9:
                        listViajes();
                        break;
                    case 10:
                        listPasajerosViaje();
                        break;
                    case 11:
                        listEmpresas();
                        break;
                    case 12:
                        listLlegadasSalidasTerminal();
                        break;
                    case 13:
                        VentasEmpresa();
                        break;
                    case 14:
                        generatePasajesVenta();
                        break;
                    case 15:
                        readDatosIniciales();
                        break;
                    case 16:
                        saveDatosSistema();
                        break;
                    case 17:
                        readDatosSistema();
                        break;
                    case 18:
                        System.out.println(":::: Cerrando el menu... ¡Hasta pronto!");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println(":::: Opcion ingresada no valida");
                sc.nextLine();

            }

        } while (opcion != 18);

    }

    private void createEmpresa() {
        try {
            System.out.println("  ...:::: Creando una nueva Empresa ::::....");
            System.out.println();
            System.out.print("     R.U.T [11222333-9] : ");

            String rutEmpresa = sc.nextLine();
            Rut rut = Rut.of(rutEmpresa);

            System.out.print("                 Nombre : ");
            String nombre = sc.nextLine();

            System.out.print("                    url : ");
            String url = sc.nextLine();

            sistema.createEmpresa(rut, nombre, url);

            System.out.println();
            System.out.println(" ...:::: Empresa guardada exitosamente ::::.... ");
        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    private void contrataTripulante() {

        try {
            System.out.println("  ...:::: Contratando un nuevo Tripulante ::::....");
            System.out.println(" ");

            System.out.println(":::: Datos de la Empresa");
            System.out.println();
            System.out.print("        R.U.T [11222333-9] : ");
            String ru = sc.nextLine();
            Rut rut = Rut.of(ru);

            System.out.println();
            System.out.println(":::: Datos del Tripulante");
            System.out.println();
            System.out.print("Auxiliar[1] o Conductor[2] : ");
            int tipoTripulante;
            try {
                tipoTripulante = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println(":::: Debe ingresar un numero");
                sc.nextLine();
                return;
            }
            sc.nextLine();
            if (tipoTripulante < 1 || tipoTripulante > 2) {
                throw new SVPException("La opcion solo puede ser 1 o 2");
            }

            System.out.print("     Rut[1] o Pasaporte[2] : ");
            int ruop;
            try {
                ruop = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println(":::: Debe ingresar un numero");
                sc.nextLine();
                return;
            }
            sc.nextLine();
            if (ruop < 1 || ruop > 2) {
                throw new SVPException("La opcion solo puede ser 1 o 2");
            }

            IdPersona id;

            if (ruop == 1) {
                System.out.print("        R.U.T [11222333-9] : ");
                String ruts = sc.nextLine();
                id = Rut.of(ruts);
            } else {
                System.out.print("                 Pasaporte : ");
                String numero = sc.nextLine();

                System.out.print("              Nacionalidad : ");
                String nacio = sc.nextLine();
                id = Pasaporte.of(numero, nacio);
            }

            System.out.print("        Sr. [1] o Sra. [2] : ");
            int tipo;
            try {
                tipo = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println(":::: Debe ingresar un numero");
                sc.nextLine();
                return;
            }
            sc.nextLine();
            if (tipo < 1 || tipo > 2) {
                throw new SVPException("La opcion solo puede ser 1 o 2");
            }

            Tratamiento tratamiento;

            if (tipo == 1){
                tratamiento = Tratamiento.SR;
            } else {
                tratamiento = Tratamiento.SRA;
            }

            System.out.print("                   Nombres : ");
            String nombres = sc.nextLine();
            System.out.print("          Apellido Paterno : ");
            String paterno = sc.nextLine();
            System.out.print("          Apellido Materno : ");
            String materno = sc.nextLine();

            Nombre nombreCompleto = new Nombre();
            nombreCompleto.setTratamiento(tratamiento);
            nombreCompleto.setNombre(nombres);
            nombreCompleto.setApellido_paterno(paterno);
            nombreCompleto.setApellido_materno(materno);

            System.out.print("                     Calle : ");
            String calle = sc.nextLine();
            System.out.print("                    Numero : ");
            int numero = sc.nextInt();
            sc.nextLine();
            System.out.print("                    Comuna : ");
            String comuna = sc.nextLine();

            Direccion direccion = new Direccion(calle, numero, comuna);

            if (tipoTripulante == 1) {
                controlador.hireAuxiliarForEmpresa(ru, id, nombreCompleto, direccion);

                System.out.println(" ");
                System.out.println(" ...:::: Auxiliar contratado exitosamente ::::....");
            }  else {
                controlador.hireConductorForEmpresa(ru, id, nombreCompleto, direccion);

                System.out.println();
                System.out.println(" ...:::: Conductor contratado exitosamente ::::....");
            }
        } catch (SVPException e) {
            System.out.println(" ");
            System.out.println(e.getMessage());
        }

    }

    private void createTerminal() {
        try {
            System.out.println("   ...:::: Crea un nuevo Terminal ::::....");
            System.out.println();
            System.out.print("              Nombre : ");
            String nombre = sc.nextLine();

            System.out.print("               Calle : ");
            String calle = sc.nextLine();

            System.out.print("              Numero : ");
            int numero;
            try {
                numero = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println(":::: Debe ingresar un numero");
                sc.nextLine();
                return;
            }
            sc.nextLine();

            System.out.print("              Comuna : ");
            String comuna = sc.nextLine();

            Direccion direccion = new Direccion(calle, numero, comuna);

            sistema.createTerminal(nombre, direccion);

            System.out.println();
            System.out.println(" ...:::: Terminal guardado exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println(e.getMessage());
        }

    }

    private void createCliente() {
        try {
            System.out.println("   ...:::: Creando un nuevo Cliente ::::....");
            System.out.println();
            System.out.print("    Rut[1] o Pasaporte[2] : ");
            int tipo;
            try {
                tipo = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println(":::: Debe ingresar un numero");
                sc.nextLine();
                return;
            }
            sc.nextLine();
            if (tipo < 1 || tipo > 2) {
                throw new SVPException("La opcion solo puede ser 1 o 2");
            }

            IdPersona id;

            if (tipo == 1) {
                System.out.print("       R.U.T [11222333-9] : ");
                String rut = sc.nextLine();

                id = Rut.of(rut);
            } else {
                System.out.print("                Pasaporte : ");
                String numero = sc.nextLine();
                System.out.print("             Nacionalidad : ");
                String nacionalidad = sc.nextLine();

                id = Pasaporte.of(numero, nacionalidad);
            }

            System.out.print("       Sr. [1] o Sra. [2] : ");
            int trat;
            try {
                trat = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println(":::: Debe ingresar un numero");
                sc.nextLine();
                return;
            }
            sc.nextLine();
            Tratamiento tratamiento;
            if (trat < 1 || trat > 2) {
                throw new SVPException("La opcion solo puede ser 1 o 2");
            }

            if (trat == 1) {
                tratamiento = Tratamiento.SR;
            } else {
                tratamiento = Tratamiento.SRA;
            }

            System.out.print("                  Nombres : ");
            String nombres = sc.nextLine();

            System.out.print("         Apellido paterno : ");
            String paterno = sc.nextLine();

            System.out.print("         Apellido materno : ");
            String materno = sc.nextLine();

            Nombre nombreCompleto = new Nombre();
            nombreCompleto.setTratamiento(tratamiento);
            nombreCompleto.setNombre(nombres);
            nombreCompleto.setApellido_paterno(paterno);
            nombreCompleto.setApellido_materno(materno);

            System.out.print("           Telefono movil : ");
            String fono = sc.nextLine();

            System.out.print("                    Email : ");
            String email = sc.nextLine();

            sistema.createCliente(id, nombreCompleto, fono, email);

            System.out.println();
            System.out.println(" ...:::: Cliente guardado exitosamente ::::.... ");
        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    private void createBus() {
        try {
            System.out.println("      ...:::: Creando un nuevo Bus ::::....");
            System.out.println();
            System.out.print("                  Patente : ");
            String patente = sc.next().trim();
            sc.nextLine();

            System.out.print("                    Marca : ");
            String marca = sc.nextLine();

            System.out.print("                   Modelo : ");
            String modelo = sc.nextLine();

            System.out.print("       Numero de asientos : ");
            int nroAsientos;
            try {
                nroAsientos = sc.nextInt();
            } catch (InputMismatchException e) {
                System.out.println();
                System.out.println(":::: Debe ingresar un numero");
                sc.nextLine();
                return;
            }
            sc.nextLine();

            System.out.println(":::: Datos de la empresa");
            System.out.print("       R.U.T [11222333-9] : ");

            String rutEmpresa = sc.nextLine();
            Rut rutEmp = Rut.of(rutEmpresa);

            sistema.createBus(patente, marca, modelo, nroAsientos, rutEmp);

            System.out.println();
            System.out.println("    ...:::: Bus guardado exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    private void createViaje() {
        try {
            System.out.println("   ...:::: Creando un nuevo Viaje ::::....");
            System.out.println();

            System.out.print("      Fecha [dd/mm/aaaa] : ");
            String fechaus = sc.next();

            LocalDate fecha = LocalDate.parse(fechaus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("            Hora [hh:mm] : ");
            String horaStr = sc.next();

            LocalTime hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));

            System.out.print("                  Precio : ");
            int precio = sc.nextInt();

            System.out.print("      Duracion [Minutos] : ");
            int duracion = sc.nextInt();
            sc.nextLine();

            System.out.print("             Patente Bus : ");
            String patente = sc.next().trim();

            System.out.print("     Nro. de Conductores : ");
            int numConductor = sc.nextInt();
            sc.nextLine();

            if (numConductor < 1 || numConductor > 2) {
                throw new SVPException("Solo se puede tener 1 o 2 Conductores por viaje");
            }

            String[] nomComunas = new String[2];
            IdPersona[] idTripulantes = new IdPersona[numConductor + 1];

            System.out.println();
            System.out.println("       :: Id Auxiliar ::");
            System.out.println();

            System.out.print("   Rut[1] o Pasaporte[2] : ");
            int tipo = sc.nextInt();
            sc.nextLine();
            if (tipo < 1 || tipo > 2) {
                throw new SVPException("La opcion solo puede ser 1 o 2");
            }

            IdPersona idAux;

            if (tipo == 1) {
                System.out.print("      R.U.T [11222333-9] : ");
                String rut = sc.nextLine();

                idAux = Rut.of(rut);
            } else {
                System.out.print("               Pasaporte : ");
                String numero = sc.nextLine();
                System.out.print("            Nacionalidad : ");
                String nacionalidad = sc.nextLine();

                idAux = Pasaporte.of(numero, nacionalidad);


            }

            idTripulantes[0] = idAux;

            for (int i = 0; i < numConductor; i++) {
                System.out.println();
                System.out.println("    :: Id Conductor " + (i+1) + " ::");
                System.out.println();

                System.out.print("   Rut[1] o Pasaporte[2] : ");
                tipo = sc.nextInt();
                sc.nextLine();
                if (tipo < 1 || tipo > 2) {
                    throw new SVPException("La opcion solo puede ser 1 o 2");
                }

                IdPersona idCond;

                if (tipo == 1) {
                    System.out.print("      R.U.T [11222333-9] : ");
                    String rut = sc.nextLine();

                    idCond = Rut.of(rut);
                } else {
                    System.out.print("               Pasaporte : ");
                    String numero = sc.nextLine();
                    System.out.print("            Nacionalidad : ");
                    String nacionalidad = sc.nextLine();

                    idCond = Pasaporte.of(numero, nacionalidad);
                }

                idTripulantes[i+1] = idCond;
            }

            System.out.print(" Nombre comuna de salida : ");
            nomComunas[0] = sc.nextLine();

            System.out.print("Nombre comuna de llegada : ");
            nomComunas[1] = sc.nextLine();


            sistema.createViaje(fecha, hora, precio, duracion, patente, idTripulantes, nomComunas);

            System.out.println();
            System.out.println("...:::: Viaje guardado exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println();
            System.out.println(":::: Formato de fecha u hora Invalido, intentar con [dd/mm/aaaa] y [hh:mm]");
        }
    }

    private void vendePasaje(){
        try {
            System.out.println("         ...:::: Venta de pasajes ::::....");
            System.out.println();

            System.out.println(" :::: Datos de la Venta");
            System.out.println();
            System.out.print("               ID Documento : ");
            String documento = sc.nextLine();

            System.out.print(" Tipo de Documento [1] Boleta [2] Factura : ");
            int tipo = sc.nextInt();
            sc.nextLine();
            if (tipo < 1 || tipo > 2) {
                throw new SVPException("La opcion solo puede ser 1 o 2");
            }

            TipoDocumento tipoDoc;

            if (tipo == 1) {
                tipoDoc = TipoDocumento.BOLETA;
            } else {
                tipoDoc = TipoDocumento.FACTURA;
            }

            System.out.print("Fecha de Viaje [dd/mm/yyyy] : ");
            String fechaViaje = sc.nextLine();

            LocalDate fecha = LocalDate.parse(fechaViaje, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("            Origen [Comuna] : ");
            String salida = sc.nextLine();

            System.out.print("           Destino [Comuna] : ");
            String llegada = sc.nextLine();
            System.out.println();

            System.out.println(" :::: Datos del Cliente");
            System.out.println();
            System.out.print("      Rut[1] o Pasaporte[2] : ");
            int tipoId = sc.nextInt();
            sc.nextLine();
            if (tipoId < 1 || tipoId > 2) {
                throw new SVPException("La opcion solo puede ser 1 o 2");
            }

            IdPersona id;

            if (tipoId == 1) {
                System.out.print("         R.U.T [11222333-9] : ");
                String rut = sc.nextLine();

                id = Rut.of(rut);
            } else {
                System.out.print("                  Pasaporte : ");
                String numero = sc.nextLine();
                System.out.print("               Nacionalidad : ");
                String nacionalidad = sc.nextLine();

                id = Pasaporte.of(numero, nacionalidad);
            }

            System.out.println(" :::: Pasajes a Vender");
            System.out.println();
            System.out.print("        Cantidad de Pasajes : ");
            int cantidad = sc.nextInt();
            sc.nextLine();


            String[][] horario = sistema.getHorariosDisponibles(fecha, salida, llegada, cantidad);

            if (horario.length == 0) {
                System.out.println();
                System.out.println(":::: No existen Viajes disponibles \n");
                return;
            }


            System.out.println();
            System.out.println(":::: Listado de Horarios disponibles");
            System.out.println("*----*----------*----------*----------*----------*");
            System.out.println("| N° | BUS      | SALIDA   | VALOR    | ASIENTOS |");

            int contador = 0;

            for (String[] h : horario) {
                System.out.println("*----+----------+----------+----------+----------*");
                System.out.printf("| %-2s | %-8s | %-8s | $%-7s | %-8s |\n",
                        contador + 1,
                        h[0],
                        h[1],
                        h[2],
                        h[3]
                );

                contador++;
            }

            System.out.println("*----+----------+----------+----------+----------*");
            System.out.print(":::: Seleccione un Viaje : ");
            int selec = sc.nextInt();
            sc.nextLine();

            String patente = horario[selec - 1][0];
            LocalTime hora = LocalTime.parse(horario[selec - 1][1], DateTimeFormatter.ofPattern("HH:mm"));

            System.out.println();

            System.out.println();
            System.out.println(":::: Asientos disponibles para el viaje seleccionado");
            System.out.println("*----*----*---*----*----*");

            String[][] asientos = sistema.listAsientosDeViaje(fecha, hora, patente);
            int totalAsientos = asientos.length;
            int filas = (totalAsientos + 3) / 4;

            for (int i = 0; i < filas; i++) {
                int a1 = i * 4 + 1;
                int a2 = i * 4 + 2;
                int a4 = i * 4 + 4;
                int a3 = i * 4 + 3;

                String s1 = (a1 <= totalAsientos && asientos[a1-1][1].equals("disponible")) ? String.valueOf(a1) : "*";
                String s2 = (a2 <= totalAsientos && asientos[a2-1][1].equals("disponible")) ? String.valueOf(a2) : "*";
                String s4 = (a4 <= totalAsientos && asientos[a4-1][1].equals("disponible")) ? String.valueOf(a4) : "*";
                String s3 = (a3 <= totalAsientos && asientos[a3-1][1].equals("disponible")) ? String.valueOf(a3) : "*";

                System.out.printf("| %2s | %2s |   | %2s | %2s |\n", s1, s2, s4, s3);

                if (i < filas - 1) {
                    System.out.println("|----+----+---+----+----|");
                }
            }
            System.out.println("*----*----*---*----*----*");


            System.out.print(":::: Seleccione sus Asientos [Separar por ,] : ");
            String elegidos = sc.nextLine();
            String[] partido = elegidos.split(",");

            if (partido.length != cantidad) {
                System.out.println();
                throw new SVPException("Solo puede Elegir " + cantidad + " Asientos");
            }

            for (int i = 0; i < cantidad; i++) {
                for (int j = i + 1; j < cantidad; j++) {
                    if (partido[i].equals(partido[j])) {
                        throw new SVPException("No se puede elegir el mismo Asiento mas de una vez");
                    }
                }
            }

            int[] asientosElegidos = new int[cantidad];

            for (int i = 0; i < partido.length; i++) {
                try {
                    asientosElegidos[i] = Integer.parseInt(partido[i].trim());
                } catch (NumberFormatException e) {
                    throw new SVPException("Los asientos deben ser numeros validos");
                }
            }

            sistema.iniciaVenta(documento, tipoDoc, fecha, salida, llegada, id, cantidad);


            for (int i = 0; i < cantidad; i++) {
                System.out.println();
                System.out.println(":::: Datos del Pasajero " + (i+1));
                System.out.println();
                System.out.print("      Rut[1] o Pasaporte[2] : ");
                tipo = sc.nextInt();
                sc.nextLine();
                if (tipo < 1 || tipo > 2) {
                    throw new SVPException("La opcion solo puede ser 1 o 2");
                }

                IdPersona idPasajero;

                if (tipo == 1) {
                    System.out.print("         R.U.T [11222333-9] : ");
                    String rut = sc.nextLine();

                    idPasajero = Rut.of(rut);
                } else {
                    System.out.print("                  Pasaporte : ");
                    String numero = sc.nextLine();
                    System.out.print("               Nacionalidad : ");
                    String nacionalidad = sc.nextLine();

                    idPasajero = Pasaporte.of(numero, nacionalidad);
                }

                Optional<String> buscarPasajero = sistema.getNombrePasajero(idPasajero);

                if (buscarPasajero.isEmpty()) {
                    System.out.println();
                    System.out.println(":::: Pasajero no Registrado, ingrese los Datos");
                    System.out.println();

                    System.out.print("       Sr. [1] o Sra. [2] : ");
                    int trat = sc.nextInt();
                    sc.nextLine();
                    if (trat < 1 || trat > 2) {
                        throw new SVPException("La opcion solo puede ser 1 o 2");
                    }
                    Tratamiento tratamiento;

                    if (trat == 1) {
                        tratamiento = Tratamiento.SR;
                    } else {
                        tratamiento = Tratamiento.SRA;
                    }

                    System.out.print("                    Nombres : ");
                    String nombres = sc.nextLine();

                    System.out.print("           Apellido paterno : ");
                    String apePaterno = sc.nextLine();

                    System.out.print("           Apellido materno : ");
                    String apeMaterno = sc.nextLine();

                    System.out.println(":::: Datos del Contacto");

                    System.out.print("       Sr. [1] o Sra. [2] : ");
                    int tratContacto = sc.nextInt();
                    sc.nextLine();
                    if (tratContacto < 1 || tratContacto > 2) {
                        throw new SVPException("La opcion solo puede ser 1 o 2");
                    }
                    Tratamiento tratamientoCont;

                    if (tratContacto == 1) {
                        tratamientoCont = Tratamiento.SR;
                    } else {
                        tratamientoCont = Tratamiento.SRA;
                    }

                    System.out.print("           Nombres contacto : ");
                    String nomContacto = sc.nextLine();

                    System.out.print("  Apellido paterno contacto : ");
                    String paternoContacto = sc.nextLine();

                    System.out.print("  Apellido materno contacto : ");
                    String maternoContacto = sc.nextLine();

                    System.out.print("              Fono contacto : ");
                    String fonoContacto = sc.nextLine();

                    Nombre nombreCompleto = new Nombre();
                    nombreCompleto.setTratamiento(tratamiento);
                    nombreCompleto.setNombre(nombres);
                    nombreCompleto.setApellido_paterno(apePaterno);
                    nombreCompleto.setApellido_materno(apeMaterno);

                    Nombre nombreContacto = new Nombre();
                    nombreContacto.setTratamiento(tratamientoCont);
                    nombreContacto.setNombre(nomContacto);
                    nombreContacto.setApellido_paterno(paternoContacto);
                    nombreContacto.setApellido_materno(maternoContacto);

                    sistema.createPasajero(idPasajero, nombreCompleto, null, nombreContacto, fonoContacto);
                }

                sistema.vendePasaje(documento, tipoDoc, fecha, hora, patente, asientosElegidos[i], idPasajero);

                System.out.println();
                System.out.println(":::: Pasaje agregado exitosamente");
            }

            int precio;
            try {
                precio = Integer.parseInt(horario[selec -1][2]);
            } catch (NumberFormatException e) {
                throw new SVPException("Error al procesar el precio del viaje");
            }

            int total = precio * cantidad;
            System.out.println();
            System.out.println(":::: Monto total de la Venta : $" + total);
            System.out.println();
            pagaVentaPasajes(documento, tipoDoc);

        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println();
            System.out.println(":::: Formato de fecha u hora Invalido, intentar con [dd/mm/aaaa] y [hh:mm]");
        }
    }

    private void pagaVentaPasajes(String documento, TipoDocumento tipoDoc) {
        try {
            System.out.println(":::: Pago de la Venta");
            System.out.print(" Efectivo [1] o Tarjeta [2] : ");
            int tipoPago = sc.nextInt();
            sc.nextLine();
            if (tipoPago < 1 || tipoPago > 2) {
                throw new SVPException("La opcion solo puede ser 1 o 2");
            }

            if (tipoPago == 1) {
                sistema.pagaVenta(documento, tipoDoc);
            } else {
                System.out.print("          Numero de Tarjeta : ");
                long numTarjeta;
                try {
                    numTarjeta = sc.nextLong();
                } catch (InputMismatchException e) {
                    System.out.println();
                    System.out.println(":::: Numero de tarjeta Invalido");
                    sc.nextLine();
                    return;
                }
                sc.nextLine();

                sistema.pagaVenta(documento, tipoDoc, numTarjeta);
            }

            System.out.println();
            System.out.println("  ...:::: Venta realizada exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    private void listPasajerosViaje() {
        try {
            System.out.println("   ...:::: Listado de Pasajeros de un Viaje ::::....");
            System.out.println();

            System.out.print("Fecha del viaje [dd/mm/yyyy] : ");
            String fechaus = sc.nextLine();

            LocalDate fecha = LocalDate.parse(fechaus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            System.out.print("      Hora del viaje [hh:mm] : ");
            String horaStr = sc.nextLine();
            LocalTime hora = LocalTime.parse(horaStr, DateTimeFormatter.ofPattern("HH:mm"));

            System.out.print("                 Patente bus : ");
            String patente = sc.nextLine();
            System.out.println(" ");

            String[][] pasajeros = sistema.listPasajeros(fecha, hora, patente);

            if (pasajeros.length == 0) {
                System.out.println(":::: No existe un viaje con los datos indicados");
                System.out.println();
                return;
            }

            System.out.println("*---------*-----------------*----------------------------------*----------------------------------*-------------------*");
            System.out.println("| ASIENTO | RUT / PASAPORTE | PASAJERO                         | CONTACTO                         | TELEFONO CONTACTO |");

            for (String[] p : pasajeros) {
                String pasajero = p[2].toUpperCase();
                if (pasajero.length() > 32) {
                    pasajero = pasajero.substring(0, 29) + "...";
                }

                String contacto = p[3].toUpperCase();
                if (contacto.length() > 32) {
                    contacto = contacto.substring(0, 29) + "...";
                }

                String pass = p[1].toUpperCase();
                if (pass.length() > 15) {
                    pass = pass.substring(0, 12) + "...";
                }

                System.out.println("*---------+-----------------+----------------------------------+----------------------------------+-------------------*");
                System.out.printf("| %-7s | %-15s | %-32s | %-32s | %-17s |\n",
                        p[0],
                        pass.toUpperCase(),
                        pasajero,
                        contacto,
                        p[4]
                );
            }

            System.out.println("*---------*-----------------*----------------------------------*----------------------------------*-------------------*");
            System.out.println();

        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println();
            System.out.println(":::: Formato de fecha u hora Invalido, intentar con [dd/mm/aaaa] y [hh:mm]");
        }
    }


    private void listVentas() {

        String[][] ventas = sistema.listVentas();

        System.out.println("    ...:::: Listado de ventas ::::....");
        System.out.println();

        if (ventas.length == 0) {
            System.out.println(":::: No hay ventas registradas");
            System.out.println();
            return;
        }

        System.out.println("*--------------*----------------*------------*-----------------*----------------------------------*---------*-------------*");
        System.out.println("| ID DOCUMENTO | TIPO DOCUMENTO | FECHA      | RUT / PASAPORTE | CLIENTE                          | BOLETOS | TOTAL VENTA |");


        for (String[] v : ventas) {
            String cliente = v[4].toUpperCase();
            if (cliente.length() > 32) {
                cliente = cliente.substring(0, 29) + "...";
            }

            String pass = v[3].toUpperCase();
            if (pass.length() > 15) {
                pass = pass.substring(0, 12) + "...";
            }

            System.out.println("*--------------+----------------+------------+-----------------+----------------------------------+---------+-------------*");
            System.out.printf("| %-12s | %-14s | %-10s | %-15s | %-32s | %-7s | $%-10s |\n",
                    v[0],
                    v[1].toUpperCase(),
                    v[2],
                    pass.toUpperCase(),
                    cliente,
                    v[5],
                    v[6]
            );
        }
        System.out.println("*--------------*----------------*------------*-----------------*----------------------------------*---------*-------------*");
        System.out.println();

    }

    private void listViajes() {
        System.out.println("     ...:::: Listado de viajes ::::....");
        System.out.println();

        String[][] viajes = sistema.listViajes();

        if (viajes.length == 0) {
            System.out.println(":::: No hay viajes registrados \n");
            return;
        }
        System.out.println("*--------------*-------------*--------------*--------*-----------------*--------------*-----------------*-----------------*");
        System.out.println("| FECHA        | HORA SALIDA | HORA LLEGADA | PRECIO | ASIENTOS DISP.  | PATENTE      | ORIGEN          | DESTINO         |");

        for (String[] v : viajes) {
            System.out.println("*--------------+-------------+--------------+--------+-----------------+--------------+-----------------+-----------------*");
            System.out.printf ("| %-12s | %-11s | %-12s | $%-5s | %-15s | %-12s | %-15s | %-15s |\n",
                    v[0],
                    v[1],
                    v[2],
                    v[3],
                    v[4],
                    v[5].toUpperCase(),
                    v[6].toUpperCase(),
                    v[7].toUpperCase()
            );
        }
        System.out.println("*--------------*-------------*--------------*--------*-----------------*--------------*-----------------*-----------------*");
        System.out.println();
    }

    private void listEmpresas() {
        System.out.println("       ...:::: Listado de Empresas ::::....");
        System.out.println();

        String[][] empresas = sistema.listEmpresas();

        if (empresas.length == 0) {
            System.out.println(":::: No se han registrado Empresas \n");
            return;
        }

        System.out.println("*--------------*--------------------------------*--------------------------------*------------------*------------*-------------*");
        System.out.println("| RUT EMPRESA  | NOMBRE                         | URL                            | NRO. TRIPULANTES | NRO. BUSES | NRO. VENTAS |");

        for (String[] e : empresas) {
            System.out.println("*--------------+--------------------------------+--------------------------------+------------------+------------+-------------*");
            System.out.printf("| %-12s | %-30s | %-30s | %-16s | %-10s | %-11s |\n",
                    e[0],
                    e[1].toUpperCase(),
                    e[2],
                    e[3],
                    e[4],
                    e[5]
            );
        }
        System.out.println("*--------------*--------------------------------*--------------------------------*------------------*------------*-------------*");
        System.out.println();
    }

    private void listLlegadasSalidasTerminal() {

        try {
            System.out.println(" ...:::: Listado de Llegadas y Salidas de un terminal ::::....");
            System.out.println(" ");

            System.out.print("             Nombre terminal : ");
            String nombreTerminal = sc.nextLine();

            System.out.print("          Fecha [dd/mm/yyyy] : ");
            String fechau = sc.nextLine();
            LocalDate fecha = LocalDate.parse(fechau, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

            String[][] resultados = controlador.listLlegadasSalidasTerminal(nombreTerminal, fecha);

            if (resultados.length == 0) {
                System.out.println(" ");
                System.out.println(":::: No hay Llegadas ni Salidas para este terminal en esa fecha");
                System.out.println(" ");
                return;
            }

            System.out.println(" ");

            System.out.println("*----------------*-------*-------------*--------------------------------*----------------*");
            System.out.println("| LLEGADA/SALIDA | HORA  | PATENTE BUS | NOMBRE EMPRESA                 | NRO. PASAJEROS |");

            for (String[] r : resultados) {
                System.out.println("|----------------+-------+-------------+--------------------------------+----------------|");
                System.out.printf("| %-14s | %-5s | %-11s | %-30s | %-14s |\n",
                        r[0].toUpperCase(),
                        r[1],
                        r[2].toUpperCase(),
                        r[3].toUpperCase(),
                        r[4]
                );
            }
            System.out.println("*----------------*-------*-------------*--------------------------------*----------------*");
            System.out.println(" ");

        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        } catch (DateTimeParseException e) {
            System.out.println();
            System.out.println(":::: Formato de fecha u hora Invalido, intentar con [dd/mm/aaaa] y [hh:mm]");
        }
    }

    private void VentasEmpresa() {
        try {
            System.out.println(" ...:::: Listado de Ventas de una Empresa ::::....");
            System.out.println(" ");

            System.out.print("         R.U.T [11222333-9] : ");
            String rutStr = sc.nextLine();
            Rut rut = Rut.of(rutStr);

            String[][] ventas = controlador.listVentasEmpresa(rut);

            if (ventas.length == 0) {
                System.out.println();
                System.out.println(":::: La Empresa no tiene ventas registradas");
                System.out.println();
                return;
            }

            System.out.println("*------------*----------*--------------*----------------*");
            System.out.println("| FECHA      | TIPO     | MONTO PAGADO | TIPO PAGO      |");

            for (String[] v : ventas) {
                System.out.println("*------------*----------*--------------*----------------*");
                System.out.printf("| %-10s | %-8s | $%-11s | %-14s |\n",
                        v[0],
                        v[1].toUpperCase(),
                        v[2],
                        v[3].toUpperCase()
                );
            }
            System.out.println("*------------*----------*--------------*----------------*");
            System.out.println();

        } catch (SVPException e) {
            System.out.println(" ");
            System.out.println(e.getMessage());
        }
    }
    private void generatePasajesVenta() {
        try {
            System.out.println("   ...::: Generar Pasajes de Venta ::::....");
            System.out.println();
            System.out.print("               ID Documento : ");
            String idDoc = sc.nextLine();
            System.out.print(" Tipo de Documento [1] Boleta [2] Factura : ");
            int tipo = sc.nextInt();
            sc.nextLine();

            TipoDocumento tipoDoc = tipo == 1 ? TipoDocumento.BOLETA : TipoDocumento.FACTURA;

            sistema.generatePasajesVenta(idDoc, tipoDoc);
            System.out.println();
            System.out.println(" ...::: Pasajes generados exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    private void readDatosIniciales() {
        try {
            System.out.println("   ...::: Leyendo Datos Iniciales ::::....");
            System.out.println();
            sistema.readDatosIniciales();
            System.out.println();
            System.out.println(" ...::: Datos iniciales cargados exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    private void saveDatosSistema() {
        try {
            System.out.println("   ...::: Guardando Datos del Sistema ::::....");
            System.out.println();
            sistema.saveDatosSistema();
            System.out.println();
            System.out.println(" ...::: Datos guardados exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    private void readDatosSistema() {
        try {
            System.out.println("   ...::: Leyendo Datos del Sistema ::::....");
            System.out.println();
            sistema.readDatosSistema();
            System.out.println();
            System.out.println(" ...::: Datos cargados exitosamente ::::....");
        } catch (SVPException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

}