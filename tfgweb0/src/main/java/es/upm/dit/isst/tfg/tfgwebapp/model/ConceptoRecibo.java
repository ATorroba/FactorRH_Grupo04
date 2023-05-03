package es.upm.dit.isst.tfg.tfgwebapp.model;

public class ConceptoRecibo {

    public Long idConceptoRecibo;
    public Recibo recibo;
    public Concepto concepto;
    public Double unidades;
    public Double precio;
    public Double devengo;
    public Double deduccion;
    
    public ConceptoRecibo() {
    }

    public Long getIdConceptoRecibo() {
        return idConceptoRecibo;
    }

    public Recibo getRecibo() {
        return recibo;
    }

    public Concepto getConcepto() {
        return concepto;
    }

    public Double getUnidades() {
        return unidades;
    }

    public Double getPrecio() {
        return precio;
    }

    public Double getDevengo() {
        return devengo;
    }

    public Double getDeduccion() {
        return deduccion;
    }

    
    
}
