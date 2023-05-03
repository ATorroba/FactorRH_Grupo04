package es.upm.dit.isst.tfgapi.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

import javax.persistence.Id;

@Table(name = "puestos")

@Entity
public class Puesto {

    @Id
    @Column(name = "idpuesto")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer idpuesto;
    @Column(name = "nombre_puesto", length = 60)
    private String nombre;
    private String desc_puesto;
    @Column(name = "sueldo_orientativo", precision = 8, scale = 2)
    private BigDecimal sueldo_orientativo;
    private String req_exp_form;
    private String req_idiomas;
    private String req_disponibilidad;
    private String req_otros;
    @Column(name = "depto", length = 3)
    private String depto;
    @Column(name = "estado", length = 10)
    private String estado;

    public Puesto() {

    }

    @Override
    public String toString() {
        return "Puesto []";
    }

    public Integer getId_puesto() {
        return idpuesto;
    }

    public void setId_puesto(Integer id_puesto) {
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