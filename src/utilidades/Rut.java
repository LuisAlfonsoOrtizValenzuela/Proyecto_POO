package utilidades;
import excepciones.SistemaVentaPasajesException;
import java.io.Serializable;
public class Rut implements IdPersona, Serializable{
    private static final long serialVersionUID = 1L;
    private int numero;
    private char dv;

    public int getNumero() {
        return numero;
    }

    public char getDv() {
        return dv;
    }

    private Rut(int numero, char dv) {
        this.numero = numero;
        this.dv = dv;


    }

    @Override
    public String toString() {

        String numerof = String.format("%,d", numero).replace(",", ".");
        return numero + "-" + dv;
    }

    @Override
    public boolean equals(Object objecto) {
        if (objecto == null || getClass() != objecto.getClass()) return false;
        Rut rut = (Rut) objecto;
        return numero == rut.numero && dv == rut.dv;
    }


    public static Rut of(String rut) throws SistemaVentaPasajesException {

        if (rut == null || rut.trim().isEmpty()) {
            throw new SistemaVentaPasajesException(":::: El RUT no puede estar Vacio");
        }
        rut = rut.replace(".", "").replace(" ", "");

        int posicionGuion = -1;
        for (int i = 0; i < rut.length(); i++) {
            if (rut.charAt(i) == '-') {
                posicionGuion = i;
                break;
            }
        }

        if (posicionGuion == -1) {
            throw new SistemaVentaPasajesException(":::: Formato de RUT invalido - Debe contener un Guion [11222333-9]");
        }
        if (posicionGuion == 0 || posicionGuion == rut.length() - 1) {
            throw new SistemaVentaPasajesException(":::: RUT invalido - El Guion no puede estar al Inicio ni al Final");
        }

        String numeroTexto = "";
        for (int i = 0; i < posicionGuion; i++) {
            char c = rut.charAt(i);
            if (c >= '0' && c <= '9') {
                numeroTexto += c;
            } else {
                throw new SistemaVentaPasajesException(":::: RUT invalido - Solo se permiten Numeros antes del Guion");
            }
        }

        if (numeroTexto.length() == 0) {
            throw new SistemaVentaPasajesException(":::: RUT invalido - Debe ingresar un Numero antes del Guion");
        }

        char digitoVerificador = rut.charAt(posicionGuion + 1);

        if (!((digitoVerificador >= '0' && digitoVerificador <= '9') ||
                digitoVerificador == 'K' || digitoVerificador == 'k')) {
            throw new SistemaVentaPasajesException(":::: Digito Verificador invalido - Debe ser un Numero o K");
        }

        int numero = 0;
        for (int i = 0; i < numeroTexto.length(); i++) {
            numero = numero * 10 + (numeroTexto.charAt(i) - '0');
        }

        char dv = Character.toUpperCase(digitoVerificador);

        return new Rut(numero, dv);
    }


}