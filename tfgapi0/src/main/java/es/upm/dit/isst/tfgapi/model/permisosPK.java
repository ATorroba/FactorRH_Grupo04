package es.upm.dit.isst.tfgapi.model;
import java.util.Objects;
import javax.persistence.*;
import java.io.Serializable;

public class permisosPK implements Serializable { 
    @Column(name = "ejercicio")
    private Integer ejercicio;

    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        permisosPK that = (permisosPK) o;
        return Objects.equals(ejercicio, that.ejercicio) && idEmpleado == that.idEmpleado;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ejercicio, idEmpleado);
    }
}