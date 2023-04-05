package es.upm.dit.isst.tfg.tfgwebapp.model;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;

public class TFG {

    @Email

    private String email;

    private String pass;

    @NotEmpty

    private String nombre;

    @NotEmpty

    private String titulo;

    @Email

    private String tutor;

    private int status;

    private byte[] memoria;

    private double nota;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getMemoria() {
        return memoria;
    }

    public void setMemoria(byte[] memoria) {
        this.memoria = memoria;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

}