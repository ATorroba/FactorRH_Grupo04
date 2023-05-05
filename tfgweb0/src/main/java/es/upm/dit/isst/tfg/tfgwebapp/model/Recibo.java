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
    
    
    
}
