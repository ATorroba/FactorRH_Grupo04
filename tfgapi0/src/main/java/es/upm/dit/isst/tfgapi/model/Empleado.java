package es.upm.dit.isst.tfgapi.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.util.Date;
import javax.persistence.Column;

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
    private Integer NASS;
    private String direccion;
    @Column(name = "CP", length = 5)
    private Integer CP;
    @Column(name = "email_particular", length = 50)
    private String email_particular;
    @Column(name = "email", length = 50)
    private String email;
    @Column(name = "telefono", length = 12)
    private String telefono;
    @Column(name = "fecha_alta", precision = 8, scale = 2)
    private Date fecha_alta;
    @Column(name = "fecha_baja", precision = 8, scale = 2)
    private Date fecha_baja;
    @Column(name = "IBAN", length = 24)
    private Integer IBAN;
    @Column(name = "SWIFT", length = 11)
    private String SWIFT;
    @Column(name = "sueldo_base", length = 50)
    private Double sueldo_base;
    @Column(name = "antiguedad", length = 50)
    private Double antiguedad;
    // private final byte[] foto;
    @Column(name = "password", length = 50)
    private String password;

    /*
     * public Empleado(String idEmpleado, String nombre, String apellido_1, String
     * apellido_2, String nIF, Integer nASS,
     * String direccion, Integer cP, String email_particular, String email, String
     * telefono, Date fecha_alta,
     * Date fecha_baja, Integer iBAN, String sWIFT, Double sueldo_base, Double
     * antiguedad, // byte[] foto,
     * String contraseña) {
     * this.idEmpleado = idEmpleado;
     * this.nombre = nombre;
     * this.apellido_1 = apellido_1;
     * this.apellido_2 = apellido_2;
     * this.NIF = nIF;
     * this.NASS = nASS;
     * this.direccion = direccion;
     * this.CP = cP;
     * this.email_particular = email_particular;
     * this.email = email;
     * this.telefono = telefono;
     * this.fecha_alta = fecha_alta;
     * this.fecha_baja = fecha_baja;
     * this.IBAN = iBAN;
     * this.SWIFT = sWIFT;
     * this.sueldo_base = sueldo_base;
     * this.antiguedad = antiguedad;
     * // this.foto = foto;
     * this.contraseña = contraseña;
     * }
     */
    public Empleado() {
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido_1(String apellido_1) {
        this.apellido_1 = apellido_1;
    }

    public void setApellido_2(String apellido_2) {
        this.apellido_2 = apellido_2;
    }

    public void setNIF(String nIF) {
        NIF = nIF;
    }

    public void setNASS(Integer nASS) {
        NASS = nASS;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setCP(Integer cP) {
        CP = cP;
    }

    public void setEmail_particular(String email_particular) {
        this.email_particular = email_particular;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setFecha_alta(Date fecha_alta) {
        this.fecha_alta = fecha_alta;
    }

    public void setFecha_baja(Date fecha_baja) {
        this.fecha_baja = fecha_baja;
    }

    public void setIBAN(Integer iBAN) {
        IBAN = iBAN;
    }

    public void setSWIFT(String sWIFT) {
        SWIFT = sWIFT;
    }

    public void setSueldo_base(Double sueldo_base) {
        this.sueldo_base = sueldo_base;
    }

    public void setAntiguedad(Double antiguedad) {
        this.antiguedad = antiguedad;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIdEmpleado() {
        return idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido_1() {
        return apellido_1;
    }

    public String getApellido_2() {
        return apellido_2;
    }

    public String getNIF() {
        return NIF;
    }

    public Integer getNASS() {
        return NASS;
    }

    public String getDireccion() {
        return direccion;
    }

    public Integer getCP() {
        return CP;
    }

    public String getEmail_particular() {
        return email_particular;
    }

    public String getEmail() {
        return email;
    }

    public String getTelefono() {
        return telefono;
    }

    public Date getFecha_alta() {
        return fecha_alta;
    }

    public Date getFecha_baja() {
        return fecha_baja;
    }

    public Integer getIBAN() {
        return IBAN;
    }

    public String getSWIFT() {
        return SWIFT;
    }

    public Double getSueldo_base() {
        return sueldo_base;
    }

    public Double getAntiguedad() {
        return antiguedad;
    }

    // public byte[] getFoto() {
    // return foto;
    // }

    public String getPassword() {
        return password;
    }
}
