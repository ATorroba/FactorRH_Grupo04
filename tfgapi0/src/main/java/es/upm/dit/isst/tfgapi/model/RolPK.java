package es.upm.dit.isst.tfgapi.model;
import java.util.Objects;
import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class RolPK implements Serializable{

    @Column(name = "idrol", length = 3)
    private String idrol;

    @Column(name = "idempleado", length = 4)
    private String idempleado;

    public RolPK() {

    }

    public RolPK(String idrol, String idempleado) {
        this.idrol = idrol;
        this.idempleado = idempleado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RolPK that = (RolPK) o;
        return Objects.equals(idrol, that.idrol) && Objects.equals(idempleado, that.idempleado);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idrol, idempleado);
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
