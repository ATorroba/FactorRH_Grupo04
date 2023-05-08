package es.upm.dit.isst.tfgapi.model;
import javax.persistence.*;

@Entity
@Table(name = "Permisos")
@IdClass(value = permisosPK.class )
public class Permisos {


    @Id
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;
    @Id
    private Integer ejercicio;
    private Integer vacaciones;
    private Integer diasGracia;

    public Permisos(){

    }

    public Integer getEjercicio(){
        return ejercicio;
    }

    public String getIdEmpleado(){
        return idEmpleado;
    }

    public Integer getVacaciones() {
        return vacaciones;
    }

    public Integer getDiasGracia() {
        return diasGracia;
    }

    public void setEjercicio(Integer ejercicio) {
        this.ejercicio = ejercicio;
    }

    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public void setVacaciones(Integer vacaciones) {
        this.vacaciones = vacaciones;
    }

    public void setDiasGracia(Integer diasGracia) {
        this.diasGracia = diasGracia;
    }
}
