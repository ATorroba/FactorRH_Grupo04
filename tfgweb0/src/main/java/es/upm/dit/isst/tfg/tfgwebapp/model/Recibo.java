package es.upm.dit.isst.tfg.tfgwebapp.model;


public class Recibo {

    public Integer idRecibo;
    public Remesa idRemesa;
    public Empleado idEmpleado;
    public String fecha_pago; // Date
    public String SWIFT;
    public String IBAN;
        
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

    public String getFecha_pago() {
        return fecha_pago;
    }

    public String getSWIFT() {
        return SWIFT;
    }

    public String getIBAN() {
        return IBAN;
    }
        
}
