package es.upm.dit.isst.tfgapi.model;

import javax.persistence.*;
import java.sql.Time;
//import javax.persistence.Lob;



@Entity
@Table(name = "Turnos")
@IdClass(value = turnoPK.class)
public class Turnos {

    @Id
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;
    @Id
    private Integer idDia;
    private Time hora_entrada;
    private Time hora_salida;
    private int minutos_jornada;
    
    // @Lob
    // private byte[] foto;

    public Turnos() {
        
    }

    @Override
    public String toString() {
        return "Turnos []";
    }

    public int getidDia() {
        return this.idDia;
    }

    public String getidEmpleado() {
        return this.idEmpleado;
    }

    public Time getHoraEntrada() {
        return hora_entrada;
    }

    public void setHoraEntrada(Time hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public Time getHoraSalida() {
        return hora_salida;
    }
    public void setHoraSalida(Time hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getMinutosJornada() {
        return minutos_jornada;
    }

    public void setMinutosJornada(int minutos_jornada) {
        this.minutos_jornada = minutos_jornada;
    }
}
