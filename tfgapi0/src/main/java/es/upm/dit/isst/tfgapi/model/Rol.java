package es.upm.dit.isst.tfgapi.model;


import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;

@Entity
public class Rol {
    
    @EmbeddedId
    private RolPK clave;
    
    @Column(name = "activo", length = 1)
    private String activo;

    

    public Rol() {
        
    }

    public RolPK getClave() {
        return clave;
    }
    public void setClave(RolPK clave) {
        this.clave = clave;
    }

    public String getActivo() {
        return activo;
    }
    public void setActivo(String activo) {
        this.activo = activo;
    }
}
