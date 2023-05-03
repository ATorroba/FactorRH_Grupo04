package es.upm.dit.isst.tfg.tfgwebapp.model;

public class Departamento {

    private String depto;
    private String nombre;    
    private String oficina;
    private String padre;

    public Departamento() {
        
    }

    public String getDepto() {
        return depto;
    }
    public void setDepto(String depto) {
        this.depto = depto;
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
