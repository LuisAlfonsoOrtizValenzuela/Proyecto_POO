package modelo;

import java.time.LocalDate;
import java.util.*;

public class Venta {

    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;
    private Pago pago;
    private ArrayList<Pasaje> pasajes;

    public Venta(String idDocumento, TipoDocumento tipo, Cliente cliente) {

        this.idDocumento = idDocumento;
        this.tipo = tipo;
        this.fecha = LocalDate.now();
        this.cliente = cliente;
        pasajes = new ArrayList<>();
        cliente.addVenta(this);

    }

    public String getIdDocumento() {return idDocumento;}

    public TipoDocumento getTipo() {
        return tipo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void createPasaje(Pasajero pasajero, Viaje viaje, int numeroAsiento) {
        Pasaje p = new Pasaje(numeroAsiento, viaje, pasajero, this);
        pasajes.add(p);
    }

    public Pasaje[] getPasajes() {
        Pasaje[] pasajess = new Pasaje[pasajes.size()];
        return pasajes.toArray(pasajess);
    }

    public int getMonto() {

        int total = 0;

        for (Pasaje p : pasajes) {
            total += p.getViaje().getPrecio();
        }
        return total;



    }

    public boolean pagaMonto() {
        if (pago != null) return false;
        pago = new PagoEfectivo(getMonto());
        return true;
    }
    public boolean pagaMonto(long nroTarjeta) {
        if (pago != null) return false;
        pago = new PagoTarjeta(getMonto(), nroTarjeta);
        return true;
    }


}