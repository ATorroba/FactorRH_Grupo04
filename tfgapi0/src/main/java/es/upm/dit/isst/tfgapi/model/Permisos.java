package es.upm.dit.isst.tfgapi.model;
import javax.persistence.*;

@Entity
@Table(name = "Permisos")
@IdClass(value = permisosPK.class )
public class Permisos {

    @Id
    private Integer ejercicio;
    @Id
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;
    private Integer vacaciones;
    private Integer dias_gracia;

    public Permisos(){

    }

    @Override
    public String toString() {
        return "Permisos []";
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

    public void setVacaciones(Integer vacaciones) {
        this.vacaciones = vacaciones;
    }

    public Integer getDiasGracia() {
        return dias_gracia;
    }

    public void setDiasGracia(Integer dias_gracia) {
        this.dias_gracia = dias_gracia;
    }
}
