package es.upm.dit.isst.tfg.tfgwebapp.model;


public class Concepto {

    public Integer idConcepto;
    public String desc_concepto;
    public String tipo; 
    public Double precio;
    
    public Concepto() {
    }

    public Integer getIdConcepto() {
        return idConcepto;
    }

    public String getDesc_concepto() {
        return desc_concepto;
    }

    public String getTipo() {
        return tipo;
    }

      public Double getPrecio() {
        return precio;
    }

    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }

    public void setDesc_concepto(String desc_concepto) {
        this.desc_concepto = desc_concepto;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    
}
