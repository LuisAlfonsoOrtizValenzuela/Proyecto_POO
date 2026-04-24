import java.util.Scanner;

public class Main {
    private Scanner sc;

    public void menu() {
        Scanner sc = new Scanner(System.in);
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
        int identificador = sc.nextInt();
        sc.nextLine();

        if (identificador == 1) {
            System.out.println("                  R.U.T : ");
            String rut = sc.nextLine();
        } else {
            System.out.println("              Pasaporte : ");
            String pase = sc.nextLine();
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
        String apellido_paterno = sc.nextLine();

        System.out.println("       Apellido materno : ");
        String apellido_materno = sc.nextLine();

        System.out.println("         Telefono movil : ");
        String telefono = sc.nextLine();

        System.out.println("                  Email : ");
        String email = sc.nextLine();
    }

    private void createBus() {

    }

    private void createViaje() {

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

        System.out.println();

    }

}