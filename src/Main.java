import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class Main {


    private Scanner sc = new Scanner(System.in);
    private SistemaVentaPasajes sistema = new SistemaVentaPasajes();

    public void menu() {
        int opcion;

        do {
            System.out.println("=========================================");
            System.out.println("      ...::: Menú principal :::...");
            System.out.println();
            System.out.println(" 1. Crear cliente");
            System.out.println(" 2. Crear bus");
            System.out.println(" 3. Crear viaje");
            System.out.println(" 4. Vender pasaje");
            System.out.println(" 5. Lista de pasajeros");
            System.out.println(" 6. Lista de ventas");
            System.out.println(" 7. Lista de viajes");
            System.out.println(" 8. Consulta viajes disponibles por fecha");
            System.out.println(" 9. Salir");
            System.out.println();
            System.out.println("-----------------------------------------");
            System.out.print("...::: Ingrese su opción: ");

            opcion = sc.nextInt();
            sc.nextLine();

            while (opcion < 1 || opcion > 9) {
                System.out.println("- LA OPCIÓN INGRESADA NO ES VALIDA...");
                System.out.print("...::: Ingrese su opción: ");
                opcion = sc.nextInt();
                sc.nextLine();
            }

            switch (opcion) {
                case 1:
                    createCliente();
                    break;
                case 2:
                    createBus();
                    break;
                case 3:
                    createViaje();
                    break;
                case 4:
                    vendePasaje();
                    break;
                case 5:
                    listPasajerosViaje();
                    break;
                case 6:
                    listVentas();
                    break;
                case 7:
                    listViajes();
                    break;
                case 8:
                    // Nose k metodo se pone ahi :v
                    break;
                case 9:
                    System.out.println("- Cerrando el menu... ¡Hasta pronto!");
                    break;
            }

        } while (opcion != 9);
    }

    private void createCliente() {
        System.out.println("   ...:::: Crear un nuevo Cliente ::::....");
        System.out.println();
        System.out.print("  Rut[1] o Pasaporte[2] : ");
        int tipo = sc.nextInt();
        sc.nextLine();

        IdPersona id;

        if (tipo == 1) {
            System.out.print("                  R.U.T : ");
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
        System.out.println("...:::: Creacion de un nuevo BUS ::::....");
        System.out.println();
        System.out.print("              Patente : ");
        String patente = sc.next();

        System.out.print("                Marca : ");
        String marca = sc.next();

        System.out.print("               Modelo : ");
        String modelo = sc.next();

        System.out.print("   Numero de asientos : ");
        int nroAsientos = sc.nextInt();
        sc.nextLine();

        boolean busCreado = sistema.createBus(patente, marca, modelo, nroAsientos);

        if (busCreado) {
            System.out.println("...:::: Bus guardado exitosamente ::::....");
        } else {
            System.out.println("..:: Ya existe un bus con esa patente ::..");
        }

    }

    private void createViaje() {
        System.out.println("...:::: Creacion de un nuevo Viaje ::::....");
        System.out.println();
        System.out.println("    Fecha [dd/mm/aaaa] : ");
        String fecha = sc.next();
        System.out.println("          Hora [hh:mm] : ");
        String hora = sc.next();
        System.out.println("                Precio : ");
        int precio = sc.nextInt();
        System.out.println("           Patente Bus : ");
        String patente = sc.next();

        System.out.println();

    }

    private void vendePasaje() {

    }

    private void listPasajerosViaje() {
        System.out.println("\n...:::: Listado de pasajeros de un viaje ::::....\n");

        System.out.print("Fecha del viaje [dd/mm/yyyy]: ");
        String fechaus = sc.nextLine();

       LocalDate fecha = LocalDate.parse(fechaus, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        System.out.print("Hora del viaje [hh:mm]: ");
        String horaStr = sc.nextLine();
        LocalTime hora = LocalTime.parse(horaStr);

        System.out.print("Patente bus: ");
        String patente = sc.nextLine();
        System.out.println(" ");

        String[][] pasajeros = sistema.listPasajeros(fecha, hora, patente);

        if (pasajeros.length == 0) {
            System.out.println("No existe un viaje con los datos indicados. \n");
            return;
        }

        System.out.println("\n*---------*----------*----------*----------*-------------------*");
        System.out.println("\n| ASIENTO | RUT/PASS | PASAJERO | CONTACTO | TELEFONO CONTACTO |");
        System.out.println  ("|---------+----------+----------+----------+-------------------+");

        for (String[] p : pasajeros) {
            System.out.printf("| %s | %s | %s | %s | %s |\n",
                    p[0],
                    p[1],
                    p[2],
                    p[3],
                    p[4]
            );
        }

        System.out.println  ("*---------*----------*----------*----------*-------------------*");
    }


    private void listVentas() {

        String[][] ventas = sistema.listVentas();

        System.out.println("\n...:::: Listado de ventas ::::....\n");

        if (ventas.length == 0) {
            System.out.println("No hay ventas registradas.\n");
            return;

        }


        System.out.println("*-------------*-----------*-------*---------------*---------*--------------*-------------*");
        System.out.println("| ID DOCUMENT | TIPO DOCU | FECHA | RUT/PASAPORTE | CLIENTE | CANT BOLETOS | TOTAL VENTA |");
        System.out.println("|-------------+-----------+-------+---------------+---------+--------------+-------------|");



        for (String[] v : ventas) {
            System.out.printf("| %s | %s | %s | %s | %s | %s | $%s |\n",
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

    }

    public static void main(String[] args) {
        Main main = new Main();
        main.menu();
    }

}