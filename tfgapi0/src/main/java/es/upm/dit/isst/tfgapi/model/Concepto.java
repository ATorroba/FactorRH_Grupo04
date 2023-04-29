package es.upm.dit.isst.tfgapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ConceptoRetributivo")
public class Concepto {
    @Id
    @Column(name = "idConcepto")
    private Integer idConcepto;
    @Column(name = "desc_concepto", length = 50)
    private String desc_concepto;  
    @Column(name = "tipo", length = 1)
    private String tipo;  
    @Column(name = "precio", precision = 8, scale = 2)
    private Double precio;
    
    public Concepto(){
    }
           
    public Integer getIdConcepto() {
        return idConcepto;
    }
    public void setIdConcepto(Integer idConcepto) {
        this.idConcepto = idConcepto;
    }
    public String getDesc_concepto() {
        return desc_concepto;
    }
    public void setDesc_concepto(String desc_concepto) {
        this.desc_concepto = desc_concepto;
    }
    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Double getPrecio() {
        return precio;
    }
    public void setPrecio(Double precio) {
        this.precio = precio;
    }  

    
}
