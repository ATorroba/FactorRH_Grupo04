package es.upm.dit.isst.tfg.tfgwebapp.model;

import java.util.Date;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotEmpty;
/*import javax.persistence.TemporalType;
import javax.persistence.Temporal*/
import java.util.Date;;

public class Permisos {


    private Integer ejercicio;
    private String idEmpleado;
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

    public void setVacaciones(Integer vacaciones) {
        this.vacaciones = vacaciones;
    }

    public void setDiasGracia(Integer diasGracia) {
        this.diasGracia = diasGracia;
    }
}

