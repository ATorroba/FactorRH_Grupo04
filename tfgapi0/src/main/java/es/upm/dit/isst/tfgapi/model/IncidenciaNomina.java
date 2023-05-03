package es.upm.dit.isst.tfgapi.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "IncidenciaNomina", uniqueConstraints = {
    @UniqueConstraint(columnNames = { 
        "idEmpleado", "ejercicio", "mes", "idConcepto" }) })
public class IncidenciaNomina {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idIncidencia")
    private Integer idIncidencia;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "idEmpleado")
    private Empleado idEmpleado;
    
    @ManyToOne(optional = false)
    @JoinColumn(name = "idConcepto")
    private Concepto idConcepto;

    private Integer ejercicio;
    private Integer mes;
    
    @Column(name = "unidades", precision = 8, scale = 2)
    private Double unidades;
    @Column(name = "precio", precision = 8, scale = 2)
    private Double precio;
    @Column(name = "importe", precision = 8, scale = 2)
    private Double importe;

    @ManyToOne(optional = true)
    @JoinColumn(name = "idRemesa")
    private Remesa idRemesa;

    public IncidenciaNomina() {
    }

    public Integer getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(Integer idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Concepto getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Concepto idConcepto) {
        this.idConcepto = idConcepto;
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

    public Double getUnidades() {
        return unidades;
    }

    public void setUnidades(Double unidades) {
        this.unidades = unidades;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public Remesa getIdRemesa() {
        return idRemesa;
    }

    public void setIdRemesa(Remesa idRemesa) {
        this.idRemesa = idRemesa;
    }

   

}
