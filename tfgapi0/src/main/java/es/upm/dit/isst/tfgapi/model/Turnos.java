package es.upm.dit.isst.tfgapi.model;

import javax.persistence.*;
import java.sql.Time;

@Entity
public class Turnos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTurno;
    private String idEmpleado;
    private Integer idDia;
    private Time hora_entrada;
    private Time hora_salida;
    private int minutos_jornada;
    

    public Turnos() {
        
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Integer getIdDia() {
        return idDia;
    }

    public void setIdDia(Integer idDia) {
        this.idDia = idDia;
    }

    public Time getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(Time hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public Time getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(Time hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getMinutos_jornada() {
        return minutos_jornada;
    }

    public void setMinutos_jornada(int minutos_jornada) {
        this.minutos_jornada = minutos_jornada;
    }
    
}
