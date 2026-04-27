import java.util.Objects;

public class Pasaporte implements IdPersona{

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
    public static Pasaporte of(String pasaporte,String nacionalidad) {

        int posicionEspacio = -1;
        for (int i = 0; i < pasaporte.length(); i++) {
            if (pasaporte.charAt(i) == ' ') {
                posicionEspacio = i;
                break;
            }
        }
        if (posicionEspacio == -1) {
            return null;
        }
        String numero = "";
        for (int i = 0; i < posicionEspacio; i++) {
            numero = numero + pasaporte.charAt(i);
        }
        nacionalidad = "";
        for (int i = posicionEspacio + 1; i < pasaporte.length(); i++) {
            nacionalidad = nacionalidad + pasaporte.charAt(i);
        }
        if (numero.length() == 0 || nacionalidad.length() == 0) {
            return null;
        }
        return new Pasaporte(numero, nacionalidad);
    }

    }




