package es.upm.dit.isst.tfg.tfgwebapp.model;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;
/*import javax.persistence.TemporalType;
import javax.persistence.Temporal*/
import java.util.Date;;

public class Empleado {
    @NotBlank(message = "campo obligatorio")
    private String idEmpleado;
    @Email

    private String email;
    @NotBlank(message = "campo obligatorio")

    private String nombre;
    private String apellido_1;
    private String apellido_2;
    private String NIF;
    private String NASS;
    private String direccion;
    private String CP;
    @Email

    private String email_particular;
    private String telefono;
    // Wed Mar 27 08:22:02 IST 2015
    private String fecha_alta;

    private String fecha_baja;
    private String IBAN;
    private String SWIFT;
    private String sueldo_base;
    private String antiguedad;
    private String password;
    private String puesto;

    public Empleado() {

    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public String getPuesto() {
        return puesto;
    }

    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFecha_alta() {
        return fecha_alta;
    }

    public void setFecha_alta(String fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public String getFecha_baja() {
        return fecha_baja;
    }

    public void setFecha_baja(String fecha_baja) {
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

    public String getSueldo_base() {
        return sueldo_base;
    }

    public void setSueldo_base(String sueldo_base) {
        this.sueldo_base = sueldo_base;
    }

    public String getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(String antiguedad) {
        this.antiguedad = antiguedad;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String contraseña) {
        this.password = contraseña;
    }

}