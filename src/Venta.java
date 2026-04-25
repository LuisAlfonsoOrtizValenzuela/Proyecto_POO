import java.time.LocalDate;
import java.util.ArrayList;

public class Venta {
    private String idDocumento;
    private TipoDocumento tipoDocumento;
    private LocalDate fecha;
    private ArrayList<Cliente> clientes;

    public Venta(String idDocumento, TipoDocumento tipoDocumento, LocalDate fecha, Cliente cliente) {
        this.idDocumento = idDocumento;
        this.tipoDocumento = tipoDocumento;
        this.fecha = fecha;
    }

    public Cliente getCliente(){
        return clientes.getLast();
    }
}
