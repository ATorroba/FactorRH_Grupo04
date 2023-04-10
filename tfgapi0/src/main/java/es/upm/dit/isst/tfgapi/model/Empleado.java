package es.upm.dit.isst.tfgapi.model;

import javax.persistence.Lob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Empleado {

    @Id
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;
    @Column(name = "nombre", length = 40)
    private String nombre;
    @Column(name = "apellido_1", length = 40)
    private String apellido_1;
    @Column(name = "apellido_2", length = 40)
    private String apellido_2;
    @Column(name = "NIF", length = 9)
    private String NIF;
    @Column(name = "NASS", length = 12)
    private String NASS;
    private String direccion;
    @Column(name = "CP", length = 5)
    private String CP;
    @Column(name = "email_particular", length = 50)
    private String email_particular;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "telefono", length = 12)
    private String telefono;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_alta", nullable = true, columnDefinition = "DATE DEFAULT CURRENT_DATE")
    private Date fecha_alta;
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_baja", nullable = true, columnDefinition = "DATE DEFAULT null")
    private Date fecha_baja;
    @Column(name = "IBAN", length = 24)
    private String IBAN;
    @Column(name = "SWIFT", length = 11)
    private String SWIFT;
    @Column(name = "sueldo_base", precision = 8, scale = 2)
    private Double sueldo_base;
    @Column(name = "antiguedad", precision = 8, scale = 2)
    private Double antiguedad;
    // private final byte[] foto;
    @Column(name = "password", length = 50)
    private String password;
    @Column(name = "puesto", length = 5)
    private String puesto;
 
    public Empleado() {
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_1() {
        return apellido_1;
    }

    public void setApellido_1(String apellido_1) {
        this.apellido_1 = apellido_1;
    }

    public String getApellido_2() {
        return apellido_2;
    }

    public void setApellido_2(String apellido_2) {
        this.apellido_2 = apellido_2;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String nIF) {
        NIF = nIF;
    }

    public String getNASS() {
        return NASS;
    }

    public void setNASS(String nASS) {
        NASS = nASS;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCP() {
        return CP;
    }

    public void setCP(String cP) {
        CP = cP;
    }

    public String getEmail_particular() {
        return email_particular;
    }

    public void setEmail_particular(String email_particular) {
        this.email_particular = email_particular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
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

    public Double getSueldo_base() {
        return sueldo_base;
    }

    public void setSueldo_base(Double sueldo_base) {
        this.sueldo_base = sueldo_base;
    }

    public Double getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Double antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
}
