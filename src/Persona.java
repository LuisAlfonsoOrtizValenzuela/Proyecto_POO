public class Persona {
    private IdPersona idPersona;
    private Nombre nombreCompleto;
    private String telefono;
    public Persona(Nombre nombreCompleto, IdPersona idPersona){
        this.nombreCompleto=nombreCompleto;
        this.idPersona=idPersona;



    }

    public void setNombreCompleto(Nombre nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public IdPersona getIdPersona() {
        return idPersona;
    }

    public Nombre getNombreCompleto() {
        return nombreCompleto;
    }

    public String getTelefono() {
        return telefono;
    }

    @Override
    public String toString() {
        return super.toString();
    }
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

}
