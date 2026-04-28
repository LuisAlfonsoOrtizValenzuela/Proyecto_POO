
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private Scanner sc=new Scanner(System.in);

    public static void main(String[] args){

    }

    private void menu() {
        int opcion = 0;
        do {
            System.out.printf("%50s%n", "...::: Menu Principal :::...");
            System.out.printf("%10s%n", "1) Crear cliente");
            System.out.printf("%10s%n", "2) Crear bus");
            System.out.printf("%10s%n", "3) Crear viaje");
            System.out.printf("%10s%n", "4) Vender pasaje");
            System.out.printf("%10s%n", "5) Lista de pasajeros");
            System.out.printf("%10s%n", "6) Lista de ventas");
            System.out.printf("%10s%n", "7) Lista de viajes");
            System.out.printf("%10s%n", "8) Consulta Viajes disponibles por fecha");
            System.out.printf("%10s%n", "9) Salir");
            opcion = sc.nextInt();
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



            }
        } while (opcion != 9);
    }

    private void createCliente(){
        SistemaVentaPasaje sistema=new SistemaVentaPasaje();
        System.out.printf("%50s%n", "...::: Crear Cliente :::...");
        int opcion=0;
        System.out.printf("%10s%n", "Rut[1] o Pasaporte[2]:");
        opcion = sc.nextInt();
        Rut rut=null;
        Pasaporte pasaporte=null;
        IdPersona id=null;
        if(opcion==1){
            System.out.printf("%10s%n", " R.U.T :");
            String rut2=sc.next();
            String[] partes = rut2.split("-");
            int numero_rut=Integer.parseInt(partes[0]);
            String codigo=partes[1];
            char verificador=codigo.charAt(0);
            rut=new Rut(numero_rut,verificador);
            id=rut;
        }else if(opcion==2){
            System.out.printf("%10s%n", "Numero del pasaporte :");
            String numero=sc.next();
            System.out.printf("%10s%n", "Nacionalidad :");
            String nacionalidad=sc.next();
            pasaporte=new Pasaporte(numero,nacionalidad);
            id=pasaporte;
        }
        int opcion2=0;
        System.out.printf("%10s%n", "Sr.[1] o Sra[2] :");
        opcion2 = sc.nextInt();
        Tratamiento tratamiento=null;
        if(opcion2==1){
            tratamiento=Tratamiento.SR;

        }else if(opcion2==2){
            tratamiento=Tratamiento.SRA;
        }
        System.out.printf("%10s%n", "Nombres :");
        String nombres=sc.next();
        System.out.printf("%10s%n", "Apellido paterno :");
        String apellido_paterno=sc.next();
        System.out.printf("%10s%n", "Apellido materno :");
        String apellido_materno=sc.next();

        Nombre nombre;
        nombre = new Nombre();
        nombre.setTratamiento(tratamiento);
        nombre.setNombres(nombres);
        nombre.setApellido_paterno(apellido_paterno);
        nombre.setApellido_materno(apellido_materno);

        System.out.printf("%10s%n", "Telefono movil :");
        String telefono=sc.next();
        System.out.printf("%10s%n", "Email :");
        String email=sc.next();

        boolean cliente_creado=sistema.createCliente(id,nombre,telefono,email);
        if(cliente_creado){
            System.out.printf("%50s%n", "...::: Cliente creado exitosamente :::...");
        }else{
            System.out.printf("%50s%n", "...::: Error :::...");
        }

    }


    private void createBus(){
        SistemaVentaPasaje sistema=new SistemaVentaPasaje();
        System.out.printf("%50s%n", "...::: Creación de un nuevo BUS :::...");
        System.out.printf("%10s%n", "Patente :");
        String patente=sc.next();
        System.out.printf("%10s%n", "Marca :");
        String marca=sc.next();
        System.out.printf("%10s%n", "Modelo :");
        String modelo=sc.next();
        System.out.printf("%10s%n", "Numero de asientos :");
        int numero_asientos=sc.nextInt();

        boolean bus_creado=sistema.createBus(patente,marca,modelo,numero_asientos);
        if(bus_creado){
            System.out.printf("%50s%n", "...::: Bus guardado exitosamente :::...");
        }else{
            System.out.printf("%50s%n", "...::: Error :::...");
        }


    }

    private void createViaje(){
        SistemaVentaPasaje sistema=new SistemaVentaPasaje();
        System.out.printf("%50s%n", "...::: Creación de un nuevo Viaje :::...");
        System.out.printf("%10s%n", "Fecha[dd/mm/yyyy] :");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fecha=LocalDate.parse(sc.next(),formatter);
        System.out.printf("%10s%n", "Hora[hh:mm] :");
        DateTimeFormatter formatter2 =DateTimeFormatter.ofPattern("hh:mm");
        LocalTime hora=LocalTime.parse(sc.next(),formatter2);
        System.out.printf("%10s%n", "Precio :");
        int precio=sc.nextInt();
        System.out.printf("%10s%n", "Patente Bus :");
        String patente=sc.next();

        boolean viaje_creado=sistema.createViaje(fecha,hora,precio,patente);

        if(viaje_creado){
            System.out.printf("%50s%n", "...::: Viaje guardado exitosamente :::...");
        }else{
            System.out.printf("%50s%n", "...::: Error :::...");
        }



    }






    private void vendePasaje(){
        SistemaVentaPasaje sistema=new SistemaVentaPasaje();
        System.out.printf("%50s%n", "...::: Crear Cliente :::...");
        System.out.printf("%10s%n", ":::: Datos de la Venta");
        System.out.printf("%10s%n", "ID Documento :");
        String id=sc.next();
        System.out.printf("%10s%n", "Tipo Documento: [1] Boleta [2] Factura :");
        int opcion=sc.nextInt();
        TipoDocumento tipo=null;
        if(opcion==1){
            tipo=TipoDocumento.BOLETA;
        }else if(opcion==2){
            tipo=TipoDocumento.FACTURA;
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
            rut=new Rut(numero_rut,verificador);
            idPersona=rut;

        }else if(opcion2==2){
            System.out.printf("%10s%n", "Numero :");
            String numero=sc.next();
            System.out.printf("%10s%n", "Nacionalidad :");
            String nacionalidad=sc.next();
            pasaporte=new Pasaporte(numero,nacionalidad);
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
                    pasaporte2=new Pasaporte(numero,nacionalidad);
                    idPersona2=pasaporte2;
                }else if(opcion4==2){
                    System.out.printf("%10s%n", " R.U.T :");
                    String rut3=sc.next();
                    String[] partes = rut3.split("-");
                    int numero_rut=Integer.parseInt(partes[0]);
                    String codigo=partes[1];
                    char verificador=codigo.charAt(0);
                    rut2=new Rut(numero_rut,verificador);
                    idPersona2=rut2;


                }


            }


        }else{
            System.out.printf("%10s%n", "Error");
        }







    }
}
