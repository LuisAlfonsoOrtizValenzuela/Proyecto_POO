import java.time.LocalDate;
import java.util.*;

public class Venta {

    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;

    private ArrayList<Pasaje> pasajes;

    public Venta(String idDocumento, TipoDocumento tipo, LocalDate fecha, Cliente cliente) {

        this.idDocumento = idDocumento;
        this.tipo = tipo;
        this.fecha = fecha;
        this.cliente = cliente;

        pasajes = new ArrayList<>();

       //poner cliente.addVenta(this);

    }

    public String getIdDocumento() {
        return idDocumento;

    }

    public TipoDocumento getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public String getCliente(){
        return cliente;
    }

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero){

        Pasaje p = new Pasaje(asiento,viaje,pasajero,this);

        pasajes.add(p);

    }

    public Pasaje[] getPasajes(){
        return null;
    }

    public int getMonto(){
        return 0;
    }




}
