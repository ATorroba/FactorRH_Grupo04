package es.upm.dit.isst.tfgapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ConceptoRecibo")
public class ConceptoRecibo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConceptoRecibo")
    private Long idConceptoRecibo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idRecibo")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Recibo idRecibo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "idConcepto")
    private Concepto idConcepto;

    @Column(name = "unidades", precision = 8, scale = 2)
    private Double unidades;
    @Column(name = "precio", precision = 8, scale = 2)
    private Double precio;
    @Column(name = "devengo", precision = 8, scale = 2)
    private Double devengo;
    @Column(name = "deduccion", precision = 8, scale = 2)
    private Double deduccion;

    public ConceptoRecibo() {
    }

    public Long getIdConceptoRecibo() {
        return idConceptoRecibo;
    }

    public void setIdConceptoRecibo(Long idConceptoRecibo) {
        this.idConceptoRecibo = idConceptoRecibo;
    }

    public Recibo getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(Recibo idRecibo) {
        this.idRecibo = idRecibo;
    }

    public Concepto getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Concepto idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Double getUnidades() {
        return unidades;
    }

    public void setUnidades(Double unidades) {
        this.unidades = unidades;
    }

    public Double getDevengo() {
        return devengo;
    }

    public void setDevengo(Double devengo) {
        this.devengo = devengo;
    }

    public Double getDeduccion() {
        return deduccion;
    }

    public void setDeduccion(Double deduccion) {
        this.deduccion = deduccion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

}
