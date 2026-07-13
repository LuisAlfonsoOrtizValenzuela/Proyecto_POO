package utilidades;
import excepciones.SVPException;
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
    public static Pasaporte of(String numero, String nacionalidad) throws SVPException {
        if (numero == null || numero.trim().isEmpty()) {
            throw new SVPException("El Numero de Pasaporte no puede estar Vacio");
        }

        if (nacionalidad == null || nacionalidad.trim().isEmpty()) {
            throw new SVPException("La Nacionalidad no puede estar Vacia");
        }

        numero = numero.trim();
        nacionalidad = nacionalidad.trim();
        if (numero.length() < 6) {
            throw new SVPException("El Numero de Pasaporte debe tener al menos 6 Caracteres");
        }
        if (!nacionalidad.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ\\s]+")) {
            throw new SVPException("La Nacionalidad solo debe contener Letras");
        }
        if (!numero.matches("[a-zA-Z0-9]+")) {
            throw new SVPException("El Numero de Pasaporte solo puede contener Letras y Numeros, sin espacios");
        }
        return new Pasaporte(numero, nacionalidad);
    }

    }




