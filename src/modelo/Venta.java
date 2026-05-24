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

    public void createPasaje(int asiento, Viaje viaje, Pasajero pasajero) {
        Pasaje pasaje = new Pasaje(asiento, viaje, pasajero, this);

        pasajes.add(pasaje);
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

    public int getMontoPagado() {
        if (pago == null) return 0;
        return pago.getMonto();
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

    public String getTipoPago() {
        if (pago == null) return null;

        if (pago instanceof PagoEfectivo) {
            return "EFECTIVO";
        }

        if (pago instanceof PagoTarjeta) {
            return "TARJETA";
        }

        return "DESCONOCIDO";
    }
}