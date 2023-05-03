package es.upm.dit.isst.tfg.tfgwebapp.model;


public class Concepto {

    public Integer idConcepto;
    public String desc_concepto;
    public String tipo; // Date
    public String IBAN;
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

    public String getIBAN() {
        return IBAN;
    }

    public Double getPrecio() {
        return precio;
    }

    
}
