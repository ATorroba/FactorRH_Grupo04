package es.upm.dit.isst.tfg.tfgwebapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import es.upm.dit.isst.tfg.tfgwebapp.model.Jornadas;
import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;


@Controller
public class JornadasController {

    public final String JORNADASMANAGER_STRING = "http://localhost:8083/jornadas/";
    public final String DATOSMANAGER_STRING = "http://localhost:8083/datos/";
    private RestTemplate restTemplate = new RestTemplate();
    
    @PostMapping("fichar/entrada")
    public String ficharEntrada(Principal principal, Model model) {
        String idEmpleado;

        try {
            Empleado empleadoActual = restTemplate.getForObject(DATOSMANAGER_STRING + principal.getName(), Empleado.class);
            idEmpleado = empleadoActual.getIdEmpleado();
        } catch (Exception e) {
            return "401";
        }

        LocalDate fecha = LocalDate.now();

        LocalTime horaActual = LocalTime.now(ZoneId.systemDefault());
        
        Jornadas jornada;

        try {
            jornada = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, Jornadas.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                jornada = null;
            } else {
                throw e;
            }
        }

        if (jornada == null) {
            jornada = new Jornadas();
            jornada.setIdEmpleado(idEmpleado);
            jornada.setFecha(fecha);
            jornada.setHora_entrada(horaActual);
            restTemplate.postForObject(JORNADASMANAGER_STRING, jornada, Jornadas.class);
        } else if (jornada.getHora_entrada() == null){
            jornada.setHora_entrada(horaActual);
            restTemplate.put(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, jornada, Jornadas.class);
        }else {
            model.addAttribute("error", "Ya ha fichado la entrada, no puede volver a fichar su entrada hasta mañana");
            
        }
    
        return "redirect:/"; 
    }  

    @PostMapping("fichar/salida")
    public String ficharSalida(Principal principal, Model model) {
        
        String idEmpleado;

        try {
            Empleado empleadoActual = restTemplate.getForObject(DATOSMANAGER_STRING + principal.getName(), Empleado.class);
            idEmpleado = empleadoActual.getIdEmpleado();
        } catch (Exception e) {
            return "401";
        }

        LocalDate fecha = LocalDate.now();
        LocalTime horaActual = LocalTime.now(ZoneId.systemDefault());

        Jornadas jornada;

        try {
            jornada = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, Jornadas.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                jornada = null;
            } else {
                throw e;
            }
        }

        if (jornada == null || jornada.getHora_entrada() == null) {
            model.addAttribute("error", "Por favor fiche la entrada antes de fichar la salida.");
           
        } else if (jornada.getHora_salida() == null && horaActual.isAfter(jornada.getHora_entrada())) {
            jornada.setHora_salida(horaActual);
            restTemplate.put(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, jornada, Jornadas.class);
        } else {
            model.addAttribute("error", "Por favor fiche la entrada antes de fichar la salida.");
            
        }

        return "redirect:/";
    }
    
}
