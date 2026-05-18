package vista;

import controlador.*;
import excepciones.SistemaVentaPasajesException;
import utilidades.*;
import modelo.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
        int opcion;

        do {
            System.out.println("=========================================");
            System.out.println("      ...::: Menú principal :::...");
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
            System.out.println(" 14. Salir");
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.print("...::: Ingrese su opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            while (opcion < 1 || opcion > 14) {
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
                    System.out.println("- Cerrando el menu... ¡Hasta pronto!");
                    break;
            }

        } while (opcion != 14);
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

            controlador.createEmpresa(rut, nombre, url);

            System.out.println();
            System.out.println(" ...:::: Empresa guardada exitosamente ::::.... ");
        } catch (SistemaVentaPasajesException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    private void contrataTripulante() {

    }

    private void createTerminal() {

    }

    private void createCliente() {
        System.out.println("   ...:::: Crear un nuevo Cliente ::::....");
        System.out.println();
        System.out.print("  Rut[1] o Pasaporte[2] : ");
        int tipo = sc.nextInt();
        sc.nextLine();

        IdPersona id;

        if (tipo == 1) {
            System.out.print("     R.U.T [11222333-9] : ");
            String rut = sc.nextLine();

            id = Rut.of(rut);
        } else {
            System.out.print("              Pasaporte : ");
            String numero = sc.nextLine();
            System.out.print("           Nacionalidad : ");
            String nacionalidad = sc.nextLine();

            id = Pasaporte.of(numero, nacionalidad);
        }

        System.out.print("     Sr. [1] o Sra. [2] : ");
        int trat = sc.nextInt();
        Tratamiento tratamiento;

        if (trat == 1) {
            tratamiento = Tratamiento.SR;
        } else {
            tratamiento = Tratamiento.SRA;
        }

        System.out.print("                Nombres : ");
        sc.nextLine();
        String nombres = sc.nextLine();

        System.out.print("       Apellido paterno : ");
        String paterno = sc.nextLine();

        System.out.print("       Apellido materno : ");
        String materno = sc.nextLine();

        Nombre nombreCompleto = new Nombre();
        nombreCompleto.setTratamiento(tratamiento);
        nombreCompleto.setNombre(nombres);
        nombreCompleto.setApellido_paterno(paterno);
        nombreCompleto.setApellido_materno(materno);

        System.out.print("         Telefono movil : ");
        String fono = sc.nextLine();

        System.out.print("                  Email : ");
        String email = sc.nextLine();

        boolean clienteCreado = sistema.createCliente(id, nombreCompleto, fono, email);

        if (clienteCreado) {
            System.out.println("...:::: Cliente guardado exitosamente ::::....");
        } else {
            System.out.println("...:::: El cliente ingresado ya existe ::::...");
        }

    }

    private void createBus() {
        try {
            System.out.println("      ...:::: Creando un nuevo Bus ::::....");
            System.out.println();
            System.out.print("                  Patente : ");
            String patente = sc.next();

            System.out.print("                    Marca : ");
            String marca = sc.next();

            System.out.print("                   Modelo : ");
            String modelo = sc.next();

            System.out.print("       Numero de asientos : ");
            int nroAsientos = sc.nextInt();
            sc.nextLine();

            System.out.println(":::: Datos de la empresa");
            System.out.print("       R.U.T [11222333-9] : ");

            String rutEmpresa = sc.nextLine();
            Rut rutEmp = Rut.of(rutEmpresa);

            controlador.createBus(patente, marca, modelo, nroAsientos, rutEmp);

            System.out.println();
            System.out.println("    ...:::: Bus guardado exitosamente ::::....");
        } catch (SistemaVentaPasajesException e) {
            System.out.println();
            System.out.println(e.getMessage());
        }
    }

    private void createViaje() {
        System.out.println("...:::: Creacion de un nuevo Viaje ::::....");
        System.out.println();
        System.out.print("    Fecha [dd/mm/aaaa] : ");
        String fechaus = sc.next();

        LocalDate fecha = LocalDate.parse(fechaus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("          Hora [hh:mm] : ");
        String horaStr = sc.next();

        LocalTime hora = LocalTime.parse(horaStr);

        System.out.print("                Precio : ");
        int precio = sc.nextInt();

        System.out.print("           Patente Bus : ");
        String patente = sc.next();
        System.out.println();

        boolean viajeCreado = sistema.createViaje(fecha, hora, precio, patente);

        if (viajeCreado) {
            System.out.println(" ...::: Viaje guardado exitosamente :::...");
        } else {
            System.out.println("..::: No fue posible crear el viaje :::..");
        }

    }

    private void vendePasaje(){
        /*
        System.out.printf("%10s%n", ":::: Datos de la Venta");
        System.out.printf("%10s%n", "ID Documento :");
        String id=sc.next();
        System.out.printf("%10s%n", "Tipo Documento: [1] Boleta [2] Factura :");
        int opcion=sc.nextInt();
        TipoDocumento tipo=null;
        if(opcion==1){
            tipo= TipoDocumento.BOLETA;
        }else if(opcion==2){
            tipo= TipoDocumento.FACTURA;
        }
        System.out.printf("%10s%n", "Fecha de Venta [dd/mm/yyyy] :");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha=LocalDate.parse(sc.next(), formatter);


        System.out.printf("%10s%n", ":::: Datos del cliente");
        System.out.printf("%10s%n", "[1] Rut o [2] Pasaporte :");
        int opcion2=sc.nextInt();
        Rut rut=null;
        Pasaporte pasaporte=null;
        IdPersona idPersona=null;
        if(opcion2==1){
            System.out.printf("%10s%n", " R.U.T :");
            String rut2=sc.next();
            String[] partes = rut2.split("-");
            int numero_rut=Integer.parseInt(partes[0]);
            String codigo=partes[1];
            char verificador=codigo.charAt(0);
            rut = Rut.of(id);
            idPersona=rut;

        }else if(opcion2==2){
            System.out.printf("%10s%n", "Numero :");
            String numero=sc.next();
            System.out.printf("%10s%n", "Nacionalidad :");
            String nacionalidad=sc.next();
            pasaporte = Pasaporte.of(numero, nacionalidad);
            idPersona=pasaporte;
        }

        String nombrePasajero=sistema.getNombrePasajero(idPersona);



        System.out.printf("%10s%n", "Nombre Cliente :" + nombrePasajero);

        boolean inicio_venta=sistema.iniciaVenta(id,tipo,fecha,idPersona);

        if(inicio_venta){
            System.out.printf("%10s%n", ":::: Pasajes a vender :");
            System.out.printf("%10s%n", "Cantidad de pasajes :");
            int cantidad_pasajes=sc.nextInt();
            System.out.printf("%10s%n", "Fecha de Viaje [dd/mm/yyyy] :");
            DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha2=LocalDate.parse(sc.next(),formatter3);
            System.out.printf("%10s%n", ":::: Listado de horarios disponibles :");
            String[][] lista=sistema.getHorariosDisponibles(fecha2);
            System.out.println("*----------*----------*----------*----------*");
            System.out.printf("|%-9s | %-9s| %-9s| %-9s|\n", "Bus", "Salida", "Valor", "Asientos");
            for(int i=0;i<lista.length;i++){
                System.out.println("*----------*----------*----------*----------*");
                System.out.print(i + 1);
                System.out.printf("|%-9s | %-9s| %-9s| %-9s|\n", lista[i][0], lista[i][1], lista[i][2], lista[i][3]);
                if(i<lista.length-1) {
                    System.out.println("|----------*----------*----------*----------|");
                }

            }
            System.out.println("*----------*----------*----------*----------*");

            System.out.printf("%10s%n", "Seleccione viaje en [1.."+lista.length+1+"]");
            int opcion3=sc.nextInt();
            String patente=lista[opcion3][0];
            String hora=lista[opcion3][1];
            DateTimeFormatter formatter2=DateTimeFormatter.ofPattern("hh:mm");
            String[][] lista2=sistema.listAsientosDeViaje(fecha2,LocalTime.parse(hora,formatter2),patente);
            System.out.printf("%10s%n", ":::: Asientos disponibles para el viaje seleccionado");
            System.out.println("*----------*----------*----------*----------*");
            for(int i=0;i<lista.length;i++){
                System.out.print(i + 1);
                System.out.printf("|%-9s | %-9s| %-9s| %-9s| %-9s\n", lista[i][0], lista[i][1],"   ", lista[i][2], lista[i][3]);
                if(i<lista.length-1) {
                    System.out.println("|----------*----------*----------*----------|");
                }

            }
            System.out.println("*----------*----------*----------*----------*");
            System.out.printf("%10s%n", "Seleccione sus asientos [separe por ,] :");
            String asientos=sc.next();
            String[] asientos2=asientos.split(",");

            for(int i=0;i<cantidad_pasajes;i++) {
                System.out.printf("%10s%n", ":::: Datos pasajero " + i + 1 + ":");
                System.out.printf("%10s%n", " Pasaporte[1] o Rut[2] :");
                int opcion4 = sc.nextInt();
                Pasaporte pasaporte2=null;
                Rut rut2=null;
                IdPersona idPersona2=null;
                if(opcion4==1){
                    System.out.printf("%10s%n", " Numero :");
                    String numero=sc.next();
                    System.out.printf("%10s%n", " Nacionalidad :");
                    String nacionalidad=sc.next();
                    pasaporte2 = Pasaporte.of(numero, nacionalidad);
                    idPersona2=pasaporte2;
                }else if(opcion4==2){
                    System.out.printf("%10s%n", " R.U.T :");
                    String rut3=sc.next();
                    String[] partes = rut3.split("-");
                    int numero_rut=Integer.parseInt(partes[0]);
                    String codigo=partes[1];
                    char verificador=codigo.charAt(0);
                    rut2 = Rut.of(id);
                    idPersona2=rut2;
                }
            }
        }else{
            System.out.printf("%10s%n", "Error");
        } */
    }

    private void pagaVentaPasajes() {

    }

    private void listPasajerosViaje() {
        System.out.println("\n...:::: Listado de pasajeros de un viaje ::::....\n");

        System.out.print("Fecha del viaje [dd/mm/yyyy] : ");
        String fechaus = sc.nextLine();

        LocalDate fecha = LocalDate.parse(fechaus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("      Hora del viaje [hh:mm] : ");
        String horaStr = sc.nextLine();
        LocalTime hora = LocalTime.parse(horaStr);

        System.out.print("                 Patente bus : ");
        String patente = sc.nextLine();
        System.out.println(" ");

        String[][] pasajeros = sistema.listPasajeros(fecha, hora, patente);

        if (pasajeros.length == 0) {
            System.out.println(":::: No existe un viaje con los datos indicados \n");
            return;
        }

        System.out.println("\n*---------*----------*----------*----------*-------------------*");
        System.out.println("\n| ASIENTO | RUT/PASS | PASAJERO | CONTACTO | TELEFONO CONTACTO |");

        for (String[] p : pasajeros) {

            System.out.println ("|---------+----------+----------+----------+-------------------+");
            System.out.printf("| %-7s | %-8s | %-8s | %-8s | %-17s |\n",
                    p[0],
                    p[1],
                    p[2],
                    p[3],
                    p[4]
            );
        }

        System.out.println("*---------*----------*----------*----------*-------------------*");
    }


    private void listVentas() {

        String[][] ventas = sistema.listVentas();

        System.out.println("\n...:::: Listado de ventas ::::....\n");

        if (ventas.length == 0) {
            System.out.println(":::: No hay ventas registradas \n");
            return;

        }

        System.out.println("*-------------*-----------*-------*---------------*---------*--------------*-------------*");
        System.out.println("| ID DOCUMENT | TIPO DOCU | FECHA | RUT/PASAPORTE | CLIENTE | CANT BOLETOS | TOTAL VENTA |");


        for (String[] v : ventas) {

            System.out.println("|-------------+-----------+-------+---------------+---------+--------------+-------------|");
            System.out.printf("|  %-11s  | %-9s | %-8s | %-13s | %-7s | %-12s | $%-8s|\n",
                    v[0],
                    v[1],
                    v[2],
                    v[3],
                    v[4],
                    v[5],
                    v[6]
            );
        }
        System.out.println("*-------------*-----------*-------*---------------*---------*--------------*-------------*");

    }

    private void listViajes() {
        System.out.println("\n......: Listado de viajes ......:\n");

        String[][] viajes = sistema.listViajes();

        if (viajes.length == 0) {
            System.out.println("No hay viajes registrados \n");
            return;
        }
        System.out.println("*------------*----------*--------*-------------*----------*");
        System.out.println("|   FECHA    |   HORA   | PRECIO | DISPONIBLES | PATENTE  |");

        for (String[] v : viajes) {

            System.out.println("|------------+----------+--------+-------------+----------|");
            System.out.printf ("| %-10s | %-8s | $%-5s | %-11s | %-8s |\n",
                    v[0],
                    v[1],
                    v[2],
                    v[3],
                    v[4]
            );
            System.out.println("*------------*----------*--------*-------------*----------*");

        }
    }

    private void consultaFecha() {
        System.out.println("..::: Consulta de viajes disponibles :::.. ");

        System.out.print(" Fecha del viaje [dd/mm/yyyy] : ");
        String fechaus = sc.nextLine();

        LocalDate fecha = LocalDate.parse(fechaus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        String[][] horarios = sistema.getHorariosDisponibles(fecha);

        if (horarios.length == 0) {
            System.out.println(":::: No hay viajes para la fecha indicada");
            return;
        }
        System.out.println("*----------*---------*-------------*---------*");
        System.out.println("|   HORA   |  PRECIO | DISPONIBLES | PATENTE  |");

        for (String[] h : horarios) {
            System.out.println("|----------+---------+-------------+---------|");
            System.out.printf("|  %-6s  |  $%-3s  |  %-10s | %-6s  |\n",
                    h[1],
                    h[2],
                    h[3],
                    h[0]
            );
        }
        System.out.println("*----------*---------*-------------*---------*");
    }

    private void listEmpresas() {
        System.out.println("       ...:::: Listado de empresas ::::....");
        System.out.println();

        String[][] empresas = controlador.listEmpresas();

        if (empresas.length == 0) {
            System.out.println(":::: No se han registrado empresas \n");
            return;
        }

        System.out.println("*--------------*--------------------------------*--------------------------------*------------------*------------*-------------*");
        System.out.println("| RUT EMPRESA  | NOMBRE                         | URL                            | NRO. TRIPULANTES | NRO. BUSES | NRO. VENTAS |");

        for (String[] e : empresas) {
            System.out.println("*--------------*--------------------------------*--------------------------------*------------------*------------*-------------*");
            System.out.printf("| %-12s | %-30s | %-30s | %-16s | %-10s | %-11s |\n",
                    e[0],
                    e[1],
                    e[2],
                    e[3],
                    e[4],
                    e[5]
            );
        }
        System.out.println("*--------------*--------------------------------*--------------------------------*------------------*------------*-------------*");
    }

    private void listLlegadasSalidasTerminal() {

    }

    private void VentasEmpresa() {

    }
}
