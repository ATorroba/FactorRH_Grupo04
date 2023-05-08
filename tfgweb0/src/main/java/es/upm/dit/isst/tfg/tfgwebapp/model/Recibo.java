package es.upm.dit.isst.tfg.tfgwebapp.model;


public class Recibo {

    public Integer idRecibo;
    public Remesa idRemesa;
    public Empleado idEmpleado;
    public String SWIFT;
    public String IBAN;
    private Double bruto;
    private Double deduccion;
    private Double neto;
        
    public Recibo() {
    }

    public Integer getIdRecibo() {
        return idRecibo;
    }

    public Remesa getIdRemesa() {
        return idRemesa;
    }

    public Empleado getIdEmpleado() {
        return idEmpleado;
    }

    public String getSWIFT() {
        return SWIFT;
    }

    public String getIBAN() {
        return IBAN;
    }

    public Double getBruto() {
        return bruto;
    }

    public Double getDeduccion() {
        return deduccion;
    }

    public Double getNeto() {
        return neto;
    }

    public void setIdRecibo(Integer idRecibo) {
        this.idRecibo = idRecibo;
    }

    public void setIdRemesa(Remesa idRemesa) {
        this.idRemesa = idRemesa;
    }

    public void setIdEmpleado(Empleado idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setSWIFT(String sWIFT) {
        SWIFT = sWIFT;
    }

    public void setIBAN(String iBAN) {
        IBAN = iBAN;
    }

    public void setBruto(Double bruto) {
        this.bruto = bruto;
    }

    public void setDeduccion(Double deduccion) {
        this.deduccion = deduccion;
    }

    public void setNeto(Double neto) {
        this.neto = neto;
    }
    
    
    
}
