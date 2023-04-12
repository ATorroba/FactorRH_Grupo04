package es.upm.dit.isst.tfgapi.model;

import javax.persistence.*;

import java.sql.Time;
import java.util.Date;





@Entity
@Table(name = "Jornadas")
@IdClass(value = jornadasPK.class)
public class Jornadas {

    @Id
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;
    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha", nullable = true, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date fecha;
    private Time hora_entrada;
    private Time hora_salida;
    private Time entrada_teorica;
    private Time salida_teorica;
    private Integer minutos_trabajados;
    private Integer minutos_teoricos;
    private Integer saldo;
    @Column(name = "incidencia", length = 3)
    private String incidencia;
    @Column(name = "estado", length = 2)
    private String estado;
    private String notas;



    public String getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public Date getFecha() {
        return fecha;
    }
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    public Date getHora_entrada() {
        return hora_entrada;
    }
    public void setHora_entrada(Time hora_entrada) {
        this.hora_entrada = hora_entrada;
    }
    public Date getHora_salida() {
        return hora_salida;
    }
    public void setHora_salida(Time hora_salida) {
        this.hora_salida = hora_salida;
    }
    public Date getEntrada_teorica() {
        return entrada_teorica;
    }
    public void setEntrada_teorica(Time entrada_teorica) {
        this.entrada_teorica = entrada_teorica;
    }
    public Date getSalida_teorica() {
        return salida_teorica;
    }
    public void setSalida_teorica(Time salida_teorica) {
        this.salida_teorica = salida_teorica;
    }
    public Integer getMinutos_trabajados() {
        return minutos_trabajados;
    }
    public void setMinutos_trabajados(Integer minutos_trabajados) {
        this.minutos_trabajados = minutos_trabajados;
    }
    public Integer getMinutos_teoricos() {
        return minutos_teoricos;
    }
    public void setMinutos_teoricos(Integer minutos_teoricos) {
        this.minutos_teoricos = minutos_teoricos;
    }
    public Integer getSaldo() {
        return saldo;
    }
    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }
    public String getIncidencia() {
        return incidencia;
    }
    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNotas() {
        return notas;
    }
    public void setNotas(String notas) {
        this.notas = notas;
    }
}