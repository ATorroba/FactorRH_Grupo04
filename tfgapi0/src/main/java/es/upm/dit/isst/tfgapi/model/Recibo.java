package es.upm.dit.isst.tfgapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Recibo", uniqueConstraints = {
    @UniqueConstraint(columnNames = { "idEmpleado", "idRemesa"}) })
public class Recibo{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRecibo")
    private Integer idRecibo;
    @OrderBy("idEmpleado")
    @ManyToOne(optional = false)
    @JoinColumn(name="idRemesa")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Remesa idRemesa;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idEmpleado")
    private Empleado idEmpleado;
    @Column(name = "IBAN", length = 24)
    private String IBAN;
    @Column(name = "SWIFT", length = 11)
    private String SWIFT;
    private Double bruto;
    private Double deduccion;
    private Double neto;

    public Recibo(){
    }

    public Integer getIdRecibo() {
        return idRecibo;
    }

    public void setIdRecibo(Integer idRecibo) {
        this.idRecibo = idRecibo;
    }

    public Remesa getIdRemesa() {
        return idRemesa;
    }

    public void setIdRemesa(Remesa idRemesa) {
        this.idRemesa = idRemesa;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

     public String getIBAN() {
        return IBAN;
    }

    public void setIBAN(String iBAN) {
        IBAN = iBAN;
    }

    public String getSWIFT() {
        return SWIFT;
    }

    public void setSWIFT(String sWIFT) {
        SWIFT = sWIFT;
    }

    public Double getBruto() {
        return bruto;
    }

    public void setBruto(Double bruto) {
        this.bruto = bruto;
    }

    public Double getDeduccion() {
        return deduccion;
    }

    public void setDeduccion(Double deduccion) {
        this.deduccion = deduccion;
    }

    public Double getNeto() {
        return neto;
    }

    public void setNeto(Double neto) {
        this.neto = neto;
    }

    
}
