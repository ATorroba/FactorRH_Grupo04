package es.upm.dit.isst.tfgapi.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "Remesa", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "ejercicio", "mes", "tipo_nomina" }) })
public class Remesa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRemesa")
    private Integer idRemesa;
    private Integer ejercicio;
    private Integer mes;
    @Column(name = "tipo_nomina", length = 1)
    private String tipo_nomina;
    @Column(name = "estado", length = 1)
    private String estado;
    @Temporal(TemporalType.DATE)
    private Date fecha_remesa;
    @Temporal(TemporalType.DATE)
    private Date fecha_pago;

    public Remesa() {
    }

    public Integer getIdRemesa() {
        return idRemesa;
    }

    public void setIdRemesa(Integer idRemesa) {
        this.idRemesa = idRemesa;
    }

    public Integer getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public String getTipo_nomina() {
        return tipo_nomina;
    }

    public void setTipo_nomina(String tipo_nomina) {
        this.tipo_nomina = tipo_nomina;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha_remesa() {
        return fecha_remesa;
    }

    public void setFecha_remesa(Date fecha_remesa) {
        this.fecha_remesa = fecha_remesa;
    }

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

}
