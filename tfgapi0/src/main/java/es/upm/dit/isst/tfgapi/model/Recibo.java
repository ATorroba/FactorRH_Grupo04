package es.upm.dit.isst.tfgapi.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Recibo")
public class Recibo{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRecibo")
    private Integer idRecibo;
    @ManyToOne(optional = false)
    @JoinColumn(name="idRemesa")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Remesa idRemesa;
    @ManyToOne(optional = false)
    @JoinColumn(name = "idEmpleado")
    private Empleado idEmpleado;
    @Temporal(TemporalType.DATE)
    private Date fecha_pago;
    @Column(name = "IBAN", length = 24)
    private String IBAN;
    @Column(name = "SWIFT", length = 11)
    private String SWIFT;

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

    public Date getFecha_pago() {
        return fecha_pago;
    }

    public void setFecha_pago(Date fecha_pago) {
        this.fecha_pago = fecha_pago;
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

}
