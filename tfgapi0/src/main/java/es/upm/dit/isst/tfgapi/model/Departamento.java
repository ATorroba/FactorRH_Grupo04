package es.upm.dit.isst.tfgapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Departamento {
    
    @Id
    @Column(name = "Id_Depto", length = 3)
    private String Id_Depto;
    private String nombre;
    private String oficina;
    @Column(name = "padre", length = 3)
    private String padre;
    
    public Departamento(){

    }

    public String getId_Depto() {
        return Id_Depto;
    }

    public void setId_Depto(String id_Depto) {
        Id_Depto = id_Depto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOficina() {
        return oficina;
    }

    public void setOficina(String oficina) {
        this.oficina = oficina;
    }

    public String getPadre() {
        return padre;
    }

    public void setPadre(String padre) {
        this.padre = padre;
    }
    
 

}
