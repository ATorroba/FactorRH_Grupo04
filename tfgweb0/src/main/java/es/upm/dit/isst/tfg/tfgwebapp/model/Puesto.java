package es.upm.dit.isst.tfg.tfgwebapp.model;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

public class Puesto {

    // @Size(max = 5, message = "Longitud máxima 5 caracteres")
    // @NotEmpty(message = "El código de puesto no puede estar vacio")
    private String idpuesto;
    @Size(max = 60, message = "Longitud máxima 60 caracteres")
    @NotEmpty(message = "El nombre de puesto no puede estar vacio")
    private String nombre;
    @Size(max = 255)
    @NotEmpty(message = "La descripción no puede estar vacía")
    private String desc_puesto;
    @DecimalMin(value = "0.00", inclusive = true, message = "El sueldo no puede ser negativo.")
    @DecimalMax(value = "1000000.00", inclusive = true, message = "El sueldo máximo para un puesto es 1 M€")
    private BigDecimal sueldo_orientativo;
    @Size(max = 255, message = "Longitud máxima 255 caracteres")
    private String req_exp_form;
    @Size(max = 255, message = "Longitud máxima 255 caracteres")
    private String req_idiomas;
    @Size(max = 255, message = "Longitud máxima 255 caracteres")
    private String req_disponibilidad;
    @Size(max = 255, message = "Longitud máxima 255 caracteres")
    private String req_otros;
    @Size(max = 3, message = "El código de departamento es 3 caracteres como máximo")
    @NotEmpty(message = "Debe indicar el código del departamento")
    private String depto;
    @Size(max = 10)
    private String estado;

    public Puesto() {

    }

    @Override
    public String toString() {
        return "Puesto [id_puesto=" + idpuesto + ", nombre_puesto=" + nombre + ", desc_puesto=" + desc_puesto
                + ", sueldo_orientativo=" + sueldo_orientativo + ", req_exp_form=" + req_exp_form + ", req_idiomas="
                + req_idiomas + ", req_disponibilidad=" + req_disponibilidad + ", req_otros=" + req_otros + ", depto="
                + depto + ", estado=" + estado + "]";
    }

    public String getId_puesto() {
        return idpuesto;
    }

    public void setId_puesto(String id_puesto) {
        this.idpuesto = id_puesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre_puesto) {
        this.nombre = nombre_puesto;
    }

    public String getDesc_puesto() {
        return desc_puesto;
    }

    public void setDesc_puesto(String desc_puesto) {
        this.desc_puesto = desc_puesto;
    }

    public BigDecimal getSueldo_orientativo() {
        return sueldo_orientativo;
    }

    public void setSueldo_orientativo(BigDecimal sueldo_orientativo) {
        this.sueldo_orientativo = sueldo_orientativo;
    }

    public String getReq_exp_form() {
        return req_exp_form;
    }

    public void setReq_exp_form(String req_exp_form) {
        this.req_exp_form = req_exp_form;
    }

    public String getReq_idiomas() {
        return req_idiomas;
    }

    public void setReq_idiomas(String req_idiomas) {
        this.req_idiomas = req_idiomas;
    }

    public String getReq_disponibilidad() {
        return req_disponibilidad;
    }

    public void setReq_disponibilidad(String req_disponibilidad) {
        this.req_disponibilidad = req_disponibilidad;
    }

    public String getReq_otros() {
        return req_otros;
    }

    public void setReq_otros(String req_otros) {
        this.req_otros = req_otros;
    }

    public String getDepto() {
        return depto;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
