package es.upm.dit.isst.tfgapi.model;

import java.util.Date;

import javax.persistence.*;
import javax.persistence.Temporal;

@Entity
@Table(name = "Ausencias")
@IdClass(value = ausenciasPK.class)
public class Ausencias {

    @Id
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;
    @Id
    @Temporal(TemporalType.DATE)
    @Column(name = "inicio", nullable = true, columnDefinition = "DATE DEFAULT null")
    private Date inicio;
    @Temporal(TemporalType.DATE)
    @Column(name = "fin", nullable = true, columnDefinition = "DATE DEFAULT null")
    private Date fin;
    @Column(name = "tipo_ausencia", length = 3)
    private String tipo_ausencia;
    @Column(name = "autorizada", length = 1)
    private String autorizada;
    @Column(name = "n_dias", precision = 8, scale = 2)
    private Integer n_dias;
    @Temporal(TemporalType.DATE)

    @Column(name = "fecha_comunicacion", nullable = true, columnDefinition = "DATE DEFAULT null")
    private Date fecha_comunicacion;
    @Column(name = "notas", length = 255)
    private String notas;

    public Ausencias() {

    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setEmpleado(String idempleado) {
        this.idEmpleado = idempleado;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getTipo_ausencia() {
        return tipo_ausencia;
    }

    public void setTipo_ausencia(String tipo_ausencia) {
        this.tipo_ausencia = tipo_ausencia;
    }

    public String getAutorizada() {
        return autorizada;
    }

    public void setAutorizada(String autorizada) {
        this.autorizada = autorizada;
    }

    public Integer getN_dias() {
        return n_dias;
    }

    public void setN_dias(Integer n_dias) {
        this.n_dias = n_dias;
    }

    public Date getFecha_comunicacion() {
        return fecha_comunicacion;
    }

    public void setFecha_comunicacion(Date fecha_comunicacion) {
        this.fecha_comunicacion = fecha_comunicacion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

}
