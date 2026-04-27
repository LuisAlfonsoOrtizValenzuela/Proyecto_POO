import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Viaje {
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private ArrayList<Pasaje> pasajes;
    private Bus bus;

    public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        this.bus = bus;
        bus.addViaje(this);
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Bus getBus(){
        return bus;
    }

    public String[][] getAsientos() {
        int length = bus.getNroAsientos();
        String[][] lista = new String[length][2];
        for (int i = 0; i < length; i++) {
            lista[i][0] = String.valueOf((i + 1));
            for(Pasaje pasaje:pasajes) {
                if(pasaje.getAsiento()==(i+1)){
                    lista[i][1]="Ocupado";
                }else{
                    lista[i][1]=String.valueOf(i+1);
                }
            }
        }
        return lista;
    }

    public void addPasaje(Pasaje pasaje){
        pasajes.add(pasaje);
    }

    public String[][] getListaPasajeros(){
        int size=pasajes.size();
        String[][] lista=new String[size][4];
        for(int i=0;i<size;i++){
            IdPersona id=pasajes.get(i).getVenta().getCliente().getIdPersona();
            lista[i][0]=id.toString();
            lista[i][1]=pasajes.get(i).getPasajero().getNomContacto().getNombres();
            lista[i][2]=pasajes.get(i).getPasajero().getNomContacto().toString();
            lista[i][3]=pasajes.get(i).getPasajero().getFonoContacto();
        }
        return lista;
    }

    public boolean existeDisponibilidad(){
        String[][] lista=getAsientos();
        int ocupados=0;
        for(int i=0;i<lista.length;i++){
            if(lista[i][1].equals("Ocupado")){
                ocupados++;
            }
        }
        if(ocupados<bus.getNroAsientos()){
            return true;
        }

        return false;
    }

    public int getNroAsientosDisponibles(){
        String[][] lista=getAsientos();
        int disponibles=0;
        for(int i=0;i<lista.length;i++){
            if(!lista[i][1].equals("Ocupado")){
                disponibles++;
            }
        }
        return disponibles;
    }


}
