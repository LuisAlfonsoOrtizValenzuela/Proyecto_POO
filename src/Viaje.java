import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Viaje {
    private LocalDate fecha;
    private LocalTime hora;
    private int precio;
    private ArrayList<Bus> buses;

    public Viaje(LocalDate fecha, LocalTime hora, int precio, Bus bus) {
        this.fecha = fecha;
        this.hora = hora;
        this.precio = precio;
        bus.addViaje(this);
        buses.add(bus);
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
        bu
        return
    }

    public String[][] getAsientos(){

        int length=bus.getNroAsientos();
        String[][] lista=new String[length][2];
        for(int i=0;i<length;i++){
            lista[i][0]= ;
            lista[i][1]= ;
        }
        return lista;
    }

}
