package es.upm.dit.isst.tfgapi.model;
import java.util.Objects;
import javax.persistence.*;
import java.io.Serializable;

public class permisosPK implements Serializable { 
    @Column(name = "ejercicio")
    private Integer ejercicio;

    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;

    public permisosPK(){

    }

    public permisosPK(String idEmpleado, Integer ejercicio){
        this.idEmpleado = idEmpleado;
        this.ejercicio = ejercicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        permisosPK that = (permisosPK) o;
        return Objects.equals(idEmpleado, that.idEmpleado) && ejercicio == that.ejercicio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado,ejercicio);
    }
}