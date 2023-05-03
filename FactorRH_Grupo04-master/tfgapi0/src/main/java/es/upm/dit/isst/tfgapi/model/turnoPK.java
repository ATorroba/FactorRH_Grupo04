package es.upm.dit.isst.tfgapi.model;
import java.util.Objects;
import javax.persistence.*;
import java.io.Serializable;
//import javax.persistence.Lob;

@Embeddable
public class turnoPK implements Serializable { 
    
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;

    @Column(name = "idDia", length = 1)
    private int idDia;

    public turnoPK(){

    }

    public turnoPK(String idEmpleado, int idDia) {
        this.idEmpleado = idEmpleado;
        this.idDia = idDia;
    }

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

    public String getIdEmpleado(){
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado){
        this.idEmpleado=idEmpleado;
    }

    public int getIdDia(){
        return idDia;
    }

    public void setIdDia(int idDia){
        this.idDia=idDia;
    }
}
