public class Pasajero extends Persona {
    private Nombre nomContacto;
    private String fonoContacto;

    public Pasajero(Nombre nombreCompleto, IdPersona idPersona, String telefono, Nombre nomContacto, String fonoContacto) {
        super(nombreCompleto, idPersona);
    }

    public Nombre getNomContacto() {
        return nomContacto;
    }
    public void setNomContacto(Nombre nomContacto) {
        this.nomContacto = nomContacto;
    }

    public String getFonoContacto() {
        return fonoContacto;
    }
    public void setFonoContacto(String fonoContacto) {
        this.fonoContacto = fonoContacto;
    }
}
