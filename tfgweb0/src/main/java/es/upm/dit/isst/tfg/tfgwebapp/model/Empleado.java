package es.upm.dit.isst.tfg.tfgwebapp.model;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotEmpty;

public class Empleado {

    @Email

    private String email;

    private String password;

    public Empleado() {

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String pass) {
        this.password = pass;
    }

}