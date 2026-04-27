import java.util.Scanner;

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

            while (opcion < 1 || opcion > 9) {
                System.out.println("- LA OPCIÓN INGRESADA NO ES VALIDA...");
                System.out.print("...::: Ingrese su opción: ");
                opcion = sc.nextInt();
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
        System.out.println("  Rut[1] o Pasaporte[2] : ");
        int tipo = sc.nextInt();
        sc.nextLine();

        IdPersona id;

        if (tipo == 1) {
            System.out.println("                  R.U.T : ");
            String rut = sc.nextLine();

            id = Rut.of(rut);
        } else {
            System.out.println("              Pasaporte : ");
            String numero = sc.nextLine();
            System.out.println("           Nacionalidad : ");
            String nacionalidad = sc.nextLine();

            id = Pasaporte.of(numero, nacionalidad);
        }

        System.out.println("     Sr. [1] o Sra. [2] : ");
        int trat = sc.nextInt();
        Tratamiento tratamiento;

        if (trat == 1) {
            tratamiento = Tratamiento.SR;
        } else {
            tratamiento = Tratamiento.SRA;
        }

        System.out.println("                Nombres : ");
        sc.nextLine();
        String nombres = sc.nextLine();

        System.out.println("       Apellido paterno : ");
        String paterno = sc.nextLine();

        System.out.println("       Apellido materno : ");
        String materno = sc.nextLine();

        Nombre nombreCompleto = new Nombre();
        nombreCompleto.setTratamiento(tratamiento);
        nombreCompleto.setNombre(nombres);
        nombreCompleto.setApellido_paterno(paterno);
        nombreCompleto.setApellido_materno(materno);

        System.out.println("         Telefono movil : ");
        String fono = sc.nextLine();

        System.out.println("                  Email : ");
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
        System.out.println("              Patente : ");
        String patente = sc.nextLine();

        System.out.println("                Marca : ");
        String marca = sc.nextLine();

        System.out.println("               Modelo : ");
        String modelo = sc.nextLine();

        System.out.println("   Numero de asientos : ");
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

    }

    private void listVentas() {

    }

    private void listViajes() {

    }

    static void main(String[] args) {

        Main menu = new Main();

        System.out.println(menu);

    }

}