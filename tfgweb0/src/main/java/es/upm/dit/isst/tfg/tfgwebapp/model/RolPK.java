package es.upm.dit.isst.tfg.tfgwebapp.model;

import java.util.Objects;
import java.io.Serializable;

public class RolPK {

    private String idrol;

    private String idempleado;

    public RolPK() {

    }

    public RolPK(String idrol, String idempleado) {
        this.idrol = idrol;
        this.idempleado = idempleado;
    }

    public String getIdrol() {
        return idrol;
    }

    public void setIdrol(String idrol) {
        this.idrol = idrol;
    }

    public String getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(String idempleado) {
        this.idempleado = idempleado;
    }

}
