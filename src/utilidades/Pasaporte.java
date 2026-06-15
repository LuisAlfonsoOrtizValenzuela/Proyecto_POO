package utilidades;
import excepciones.SistemaVentaPasajesException;
import java.util.Objects;
import java.io.Serializable;
public class Pasaporte implements IdPersona, Serializable{
    private static final long serialVersionUID = 1L;
    private String numero;
    private String nacionalidad;
    private Pasaporte (String num, String nacionalidad){
        this.nacionalidad=nacionalidad;
        this.numero=num;
    }

    public String getNumero() {
        return numero;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    @Override
    public String toString() {
        return numero + " " + nacionalidad;

    }

    @Override
    public boolean equals(Object objecto) {
        if (objecto == null || getClass() != objecto.getClass()) return false;
        Pasaporte pasaporte = (Pasaporte) objecto;
        return Objects.equals(numero, pasaporte.numero) && Objects.equals(nacionalidad, pasaporte.nacionalidad);
    }
    public static Pasaporte of(String numero, String nacionalidad) throws SistemaVentaPasajesException {
        if (numero == null || numero.trim().isEmpty()) {
            throw new SistemaVentaPasajesException("ERROR: El número de pasaporte no puede estar vacío");
        }

        if (nacionalidad == null || nacionalidad.trim().isEmpty()) {
            throw new SistemaVentaPasajesException("ERROR: La nacionalidad no puede estar vacía");
        }

        numero = numero.trim();
        nacionalidad = nacionalidad.trim();
        if (numero.length() < 6) {
            throw new SistemaVentaPasajesException("ERROR: El número de pasaporte debe tener al menos 6 caracteres");
        }
        if (!nacionalidad.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+")) {
            throw new SistemaVentaPasajesException("ERROR: La nacionalidad solo debe contener letras");
        }
        if (!numero.matches("[a-zA-Z0-9]+")) {
            throw new SistemaVentaPasajesException("ERROR: El número de pasaporte solo puede contener letras y números, sin espacios");
        }
        return new Pasaporte(numero, nacionalidad);
    }

    }




