package es.upm.dit.isst.tfg.tfgwebapp.model;

public class ConceptoRecibo {

    public Long idConceptoRecibo;
    public Recibo idRecibo;
    public Concepto idConcepto;
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
        return idRecibo;
    }

    public Concepto getConcepto() {
        return idConcepto;
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
