package es.upm.dit.isst.tfg.tfgwebapp.model;

import javax.validation.constraints.NotBlank;

public class Candidato {
    @NotBlank(message = "campo obligatorio")

    public String idcandidato;
    @NotBlank(message = "campo obligatorio")

    public String nombre;
    public String apellido_1;
    public String apellido_2;
    @NotBlank(message = "campo obligatorio")

    public String email;
    public String form_Experiencia;
    public String idiomas;
    public String disponibilidad;
    public String notas_reclutador;
    public String curriculum;
    @NotBlank(message = "campo obligatorio")

    public String preseleccionado;
    @NotBlank(message = "campo obligatorio")

    public String puesto;

    public Candidato() {
    }

    // Getters

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

    public String getcurriculum() {
        return curriculum;
    }

    public String getpreseleccionado() {
        return preseleccionado;
    }

    public String getpuesto() {
        return puesto;
    }

    // Setters

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

    public void setcurriculum(String curriculum) {
        this.curriculum = curriculum;
    }

    public void setpreseleccionado(String preseleccionado) {
        this.preseleccionado = preseleccionado;
    }

    public void setpuesto(String puesto) {
        this.puesto = puesto;
    }

}
