package es.upm.dit.isst.tfgapi.model;

import javax.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;


@Entity
@Table(name = "Jornadas")
@IdClass(value = jornadasPK.class)
public class Jornadas {

    @Id
    @Column(name = "idEmpleado", length = 4)
    private String idEmpleado;
    @Id
    private LocalDate fecha;
    private LocalTime hora_entrada;
    private LocalTime hora_salida;
    private LocalTime entrada_teorica;
    private LocalTime salida_teorica;
    private int minutos_trabajados;
    private int minutos_teoricos;
    private int saldo;
    @Column(name = "incidencia", length = 3)
    private String incidencia;
    @Column(name = "estado", length = 2)
    private String estado;
    private String notas;

    public Jornadas() {

    }

    public String getIdEmpleado() {
        return idEmpleado;
    }
    public void setIdEmpleado(String idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public LocalTime getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(LocalTime hora_entrada) {
        this.hora_entrada = hora_entrada;
    }
    public LocalTime getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(LocalTime hora_salida) {
        this.hora_salida = hora_salida;
    }
    public LocalTime getEntrada_teorica() {
        return entrada_teorica;
    }

    public void setEntrada_teorica(LocalTime entrada_teorica) {
        this.entrada_teorica = entrada_teorica;
    }
    public LocalTime getSalida_teorica() {
        return salida_teorica;
    }

    public void setSalida_teorica(LocalTime salida_teorica) {
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