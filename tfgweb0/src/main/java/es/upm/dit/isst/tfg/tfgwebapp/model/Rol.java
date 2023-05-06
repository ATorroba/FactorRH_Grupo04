package es.upm.dit.isst.tfg.tfgwebapp.model;

public class Rol {

    public RolPK clave;

    public String activo;

    public Rol() {

    }

    public RolPK getClave() {
        return clave;
    }

    public void setClave(RolPK clave) {
        this.clave = clave;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
