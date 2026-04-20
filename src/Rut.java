public class Rut {

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
        return "Rut:"+ numero +"-"+ dv ;
    }

    @Override
    public boolean equals(Object objecto) {
        if (objecto == null || getClass() != objecto.getClass()) return false;
        Rut rut = (Rut) objecto;
        return numero == rut.numero && dv == rut.dv;
    }




    public Rut of(String rutCONdv) {



        return null;
    }
}


