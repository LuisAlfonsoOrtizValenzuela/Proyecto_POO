package modelo;

import java.time.LocalDate;
import java.util.*;

public class Venta {

    private String idDocumento;
    private TipoDocumento tipo;
    private LocalDate fecha;
    private Cliente cliente;

    private ArrayList<Pasaje> pasajes;

    public Venta(String idDocumento, TipoDocumento tipo, Cliente cliente) {

        this.idDocumento = idDocumento;

        this.tipo = tipo;
        this.fecha = LocalDate.now();
        this.cliente = cliente;


        pasajes = new ArrayList<>();

        cliente.addVenta(this);

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

    public Cliente getCliente() {
        return cliente;
    }

    public boolean createPasaje(int asiento, Viaje viaje, Pasajero pasajero) {

        Pasaje p = new Pasaje(asiento, viaje, pasajero, this);
        pasajes.add(p);
        return true;
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



    }}