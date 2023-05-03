package es.upm.dit.isst.tfgapi.model;

import java.util.Objects;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Embeddable;

@Embeddable
public class jornadasPK implements Serializable { 
    
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;

    private LocalDate fecha;

    public jornadasPK() {

    }

    public jornadasPK(String idEmpleado, LocalDate fecha) {
        this.idEmpleado = idEmpleado;
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        jornadasPK that = (jornadasPK) o;
        return Objects.equals(idEmpleado, that.idEmpleado) && Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idEmpleado, fecha);
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    } 
}