package es.upm.dit.isst.tfg.tfgwebapp.model;


public class IncidenciaNomina {

    public Integer idIncidencia;
    public Remesa idRemesa;
    public Concepto idConcepto;
    public Empleado idEmpleado;
    public Integer ejercicio;
    public Integer mes;
    private Double importe;
    private Double unidades;
    private Double precio;
        
    public IncidenciaNomina() {
    }

    public Integer getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(Integer idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public Remesa getIdRemesa() {
        return idRemesa;
    }

    public void setIdRemesa(Remesa idRemesa) {
        this.idRemesa = idRemesa;
    }

    public Concepto getIdConcepto() {
        return idConcepto;
    }

    public void setIdConcepto(Concepto idConcepto) {
        this.idConcepto = idConcepto;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
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

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
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

    
    
    
}
