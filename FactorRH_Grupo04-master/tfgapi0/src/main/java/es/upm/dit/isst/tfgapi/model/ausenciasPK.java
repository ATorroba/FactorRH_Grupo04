
package es.upm.dit.isst.tfgapi.model;

import java.util.Objects;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class ausenciasPK implements Serializable {
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;
    @Temporal(TemporalType.DATE)

    @Column(name = "inicio", nullable = false, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date inicio;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ausenciasPK that = (ausenciasPK) o;
        return Objects.equals(idEmpleado, that.idEmpleado) && inicio == that.inicio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado, inicio);
    }
}