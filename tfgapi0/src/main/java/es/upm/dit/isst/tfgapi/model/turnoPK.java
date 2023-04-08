package es.upm.dit.isst.tfgapi.model;
import java.util.Objects;
import javax.persistence.*;
import java.io.Serializable;
//import javax.persistence.Lob;

public class turnoPK implements Serializable { 
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;

    @Column(name = "idDia")
    private int idDia;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        turnoPK that = (turnoPK) o;
        return Objects.equals(idEmpleado, that.idEmpleado) && idDia == that.idDia;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado, idDia);
    }
}
