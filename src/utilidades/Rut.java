package utilidades;

public class Rut implements IdPersona{

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
        return "utilidades.Rut:" + numero + "-" + dv;
    }

    @Override
    public boolean equals(Object objecto) {
        if (objecto == null || getClass() != objecto.getClass()) return false;
        Rut rut = (Rut) objecto;
        return numero == rut.numero && dv == rut.dv;
    }

    public static Rut of(String rut) {

        int posicionGuion = -1;
        for (int i = 0; i < rut.length(); i++) {
            if (rut.charAt(i) == '-') {
                posicionGuion = i;
                break;
            }
        }
        if (posicionGuion == -1) {
            return null;
        }
        String numeroTexto = "";
        for (int i = 0; i < posicionGuion; i++) {
            char c = rut.charAt(i);
            if (c >= '0' && c <= '9') {  // si es un dígito
                numeroTexto = numeroTexto + c;
            }

        }
        char digitoVerificador = rut.charAt(posicionGuion + 1);
        int numero = 0;
        for (int i = 0; i < numeroTexto.length(); i++) {
            char c = numeroTexto.charAt(i);
            int digito = c - '0';
            numero = numero * 10 + digito;
        }
        if (digitoVerificador >= '0' && digitoVerificador <= '9') {

        }
        else if (digitoVerificador == 'K' || digitoVerificador == 'k') {
        } else {
            return null;
        }
        return new Rut(numero, digitoVerificador);

    }


}