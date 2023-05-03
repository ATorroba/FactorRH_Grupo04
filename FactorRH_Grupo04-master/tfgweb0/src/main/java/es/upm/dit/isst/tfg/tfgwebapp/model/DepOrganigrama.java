package es.upm.dit.isst.tfg.tfgwebapp.model;

import java.util.ArrayList;
import java.util.List;
import es.upm.dit.isst.tfg.tfgwebapp.model.Departamento;

public class DepOrganigrama {
    private Departamento departamento;
    private List<DepOrganigrama> hijos;

    public DepOrganigrama() {

    }

    public DepOrganigrama(Departamento departamento) {
        this.departamento = departamento;
        this.hijos = new ArrayList<>();
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public List<DepOrganigrama> getHijos() {
        return hijos;
    }

    public void setHijo(DepOrganigrama hijo) {
        this.hijos.add(hijo);
    }
}
