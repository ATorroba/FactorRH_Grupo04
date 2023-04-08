package es.upm.dit.isst.tfgapi.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Calendario")
public class Calendario {
    @Id
    private Date fecha;
    private String tipo;
    


    public Calendario() {
        
    }

    @Override
    public String toString() {
        return "Calendario []";
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}