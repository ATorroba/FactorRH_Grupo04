package es.upm.dit.isst.tfg.tfgwebapp.model;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

public class Remesa {

    public Integer idRemesa;
    @Range(min = 2020, max = 2100, message = "Año admitido de 2020 a 2100")
    public Integer ejercicio;
    @Range(min = 1, max = 12, message = "Mes debe estar entre 1 y 12")
    public Integer mes;
    @Size(max = 1, message = "Longitud máxima 1 carácter")
    public String tipo_nomina;
    @Size(max = 1, message = "Longitud máxima 1 carácter")
    public String estado;

    public String fecha_remesa; // Date
    public String fecha_pago; // Date

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

    public String getFecha_remesa() {
        return fecha_remesa;
    }

    public void setFecha_remesa(String fecha_remesa) {
        this.fecha_remesa = fecha_remesa;
    }

    public String getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(String fecha_pago) {
        this.fecha_pago = fecha_pago;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }


    
}
