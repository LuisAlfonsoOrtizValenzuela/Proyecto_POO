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
            throw new SistemaVentaPasajesException("ERROR: El RUT no puede estar vacío");
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
            throw new SistemaVentaPasajesException("ERROR: Formato de RUT inválido - Debe contener un guión (ej: 12345678-9)");
        }
        if (posicionGuion == 0 || posicionGuion == rut.length() - 1) {
            throw new SistemaVentaPasajesException("ERROR: RUT inválido - El guión no puede estar al inicio o al final");
        }

        String numeroTexto = "";
        for (int i = 0; i < posicionGuion; i++) {
            char c = rut.charAt(i);
            if (c >= '0' && c <= '9') {
                numeroTexto += c;
            } else {
                throw new SistemaVentaPasajesException("ERROR: RUT inválido - Solo se permiten números antes del guión");
            }
        }

        if (numeroTexto.length() == 0) {
            throw new SistemaVentaPasajesException("ERROR: RUT inválido - Debe ingresar un número antes del guión");
        }

        char digitoVerificador = rut.charAt(posicionGuion + 1);

        if (!((digitoVerificador >= '0' && digitoVerificador <= '9') ||
                digitoVerificador == 'K' || digitoVerificador == 'k')) {
            throw new SistemaVentaPasajesException("ERROR: Dígito verificador inválido - Debe ser un número o K");
        }

        int numero = 0;
        for (int i = 0; i < numeroTexto.length(); i++) {
            numero = numero * 10 + (numeroTexto.charAt(i) - '0');
        }

        char dv = Character.toUpperCase(digitoVerificador);

        return new Rut(numero, dv);
    }


}