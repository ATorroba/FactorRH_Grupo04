
package es.upm.dit.isst.tfgapi.model;

import java.util.Objects;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


public class ausenciasPK implements Serializable { 
    @Column(name = "empleado", length = 4)
    private String empleado;

    @Column(name = "inicio")
    private Date inicio;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ausenciasPK that = (ausenciasPK) o;
        return Objects.equals(empleado, that.empleado) && inicio == that.inicio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(empleado, inicio);
    }
}