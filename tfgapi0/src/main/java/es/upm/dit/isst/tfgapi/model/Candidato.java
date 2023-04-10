package es.upm.dit.isst.tfgapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class Candidato {

    @Id
    @Column(name = "idcandidato", length = 8)
    private String idcandidato;
    @Column(name = "nombre", length = 40)
    private String nombre;
    @Column(name = "apellido_1", length = 40)
    private String apellido_1;
    @Column(name = "apellido_2", length = 40)
    private String apellido_2;
    @Column(name = "email", length = 50)
    private String email;
    private String form_Experiencia;
    private String idiomas;
    private String disponibilidad;
    private String notas_reclutador;
    @Lob
    private Byte[] curriculum;
    @Column(name = "preseleccionado", length = 2)
    private String preseleccionado;
    @Column(name = "puesto", length = 5)
    private String puesto;
    

    public Candidato() {    
    }

    //Getters

    @Override
    public String toString() {
        return "Candidato []";
    }

     
    public String getidcandidato() {
        return idcandidato;
    }

    public String getnombre() {
        return nombre;
    }

    public String getapellido_1() {
        return apellido_1;
    }


    public String getapellido_2() {
        return apellido_2;
    }

    public String getEmail() {
        return email;
    }

  
    public String getform_Experiencia() {
        return form_Experiencia;
    }


    public String getidiomas() {
        return idiomas;
    }


    public String getdisponibilidad() {
        return disponibilidad;
    }


    public String getnotas_reclutador() {
        return notas_reclutador;
    }


    public Byte[] getcurriculum() {
        return curriculum;
    }


    public String getpreseleccionado() {
        return preseleccionado;
    }


    public String getpuesto() {
        return puesto;
    }

    //Setters

    public void setidcandidato(String idcandidato) {
        this.idcandidato = idcandidato;
    }


    public void setnombre(String nombre) {
        this.nombre = nombre;
    }


    public void setapellido_1(String apellido_1) {
        this.apellido_1 = apellido_1;
    }


    public void setapellido_2(String apellido_2) {
        this.apellido_2 = apellido_2;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public void setform_Experiencia(String form_Experiencia) {
        this.form_Experiencia = form_Experiencia;
    }


    public void setidiomas(String idiomas) {
        this.idiomas = idiomas;
    }


    public void setdisponibilidad(String disponibilidad) {
        this.disponibilidad = disponibilidad;
    }


    public void setnotas_reclutador(String notas_reclutador) {
        this.notas_reclutador = notas_reclutador;
    }


    public void setcurriculum(Byte[] curriculum) {
        this.curriculum = curriculum;
    }


    public void setpreseleccionado(String preseleccionado) {
        this.preseleccionado = preseleccionado;
    }


    public void setpuesto(String puesto) {
        this.puesto = puesto;
    }


}
