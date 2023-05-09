package es.upm.dit.isst.tfg.tfgwebapp.model;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.validation.constraints.Size;

public class Jornadas {
    
    @Size(max = 4, message = "Longitud máxima de idEmpleado es 4 caracteres")
    private String idEmpleado;
    private String fecha;
    private String hora_entrada;
    private String hora_salida;
    private String entrada_teorica;
    private String salida_teorica;
    private int minutos_trabajados;
    private int minutos_teoricos;
    private int saldo;
    private String incidencia;
    private String estado;
    @Size(max = 255, message = "Longitud máxima de la nota es 255 caracteres")
    private String notas;

    public Jornadas() {

    }

    public String getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    public String getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(String hora_entrada) {
        this.hora_entrada = hora_entrada;
    }
    public String getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(String hora_salida) {
        this.hora_salida = hora_salida;
    }
    public String getEntrada_teorica() {
        return entrada_teorica;
    }

    public void setEntrada_teorica(String entrada_teorica) {
        this.entrada_teorica = entrada_teorica;
    }
    public String getSalida_teorica() {
        return salida_teorica;
    }

    public void setSalida_teorica(String salida_teorica) {
        this.salida_teorica = salida_teorica;
    }
    public int getMinutos_trabajados() {
        return minutos_trabajados;
    }

    public void setMinutos_trabajados(int minutos_trabajados) {
        this.minutos_trabajados = minutos_trabajados;
    }
    public int getMinutos_teoricos() {
        return minutos_teoricos;
    }

    public void setMinutos_teoricos(int minutos_teoricos) {
        this.minutos_teoricos = minutos_teoricos;
    }
    public int getSaldo() {
        return saldo;
    }

    public void setSaldo(int saldo) {
        this.saldo = saldo;
    }
    public String getIncidencia() {
        return incidencia;
    }
    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    public String getNotas() {
        return notas;
    }
    public void setNotas(String notas) {
        this.notas = notas;
    }
}
