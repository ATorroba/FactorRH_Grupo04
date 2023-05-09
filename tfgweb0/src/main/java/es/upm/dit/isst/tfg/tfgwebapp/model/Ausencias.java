package es.upm.dit.isst.tfg.tfgwebapp.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;
/*import javax.persistence.TemporalType;
import javax.persistence.Temporal*/
import java.util.Date;;

public class Ausencias {
    private Integer idausencia;

    private String idempleado;
    @NotBlank(message = "campo obligatorio")

    private String inicio;
    @NotBlank(message = "campo obligatorio")

    private String fin;// date
    private String tipo_ausencia;
    private String autorizada;
    private Integer n_dias;
    private String fecha_comunicacion;// date
    private String notas;

    public Ausencias() {

    }

    public String getIdempleado() {
        return idempleado;
    }

    public void setIdempleado(String idempleado) {
        this.idempleado = idempleado;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
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

    public String getFecha_comunicacion() {
        return fecha_comunicacion;
    }

    public void setFecha_comunicacion(String fecha_comunicacion) {
        this.fecha_comunicacion = fecha_comunicacion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Integer getIdausencia() {
        return idausencia;
    }

    public void setIdausencia(Integer idausencia) {
        this.idausencia = idausencia;
    }

}
