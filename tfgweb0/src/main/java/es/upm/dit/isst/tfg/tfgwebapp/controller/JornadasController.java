package es.upm.dit.isst.tfg.tfgwebapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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

import org.springframework.validation.annotation.Validated;
import org.springframework.validation.BindingResult;
import java.util.Map;


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
            jornada.setEstado("fe");
            restTemplate.postForObject(JORNADASMANAGER_STRING, jornada, Jornadas.class);
        } else if (jornada.getHora_entrada() == null){
            jornada.setHora_entrada(horaActual);
            jornada.setEstado("fe");
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
            jornada.setEstado("1");
            int horaEntr = jornada.getHora_entrada().getHour();
            int minEntr = jornada.getHora_entrada().getMinute();
            int minutosEnt = horaEntr * 60 + minEntr;
            int horaSal = jornada.getHora_salida().getHour();
            int minSal = jornada.getHora_salida().getMinute();
            int minutosSal = horaSal * 60 + minSal;
            int minTrabajados = minutosSal - minutosEnt;
            jornada.setMinutos_trabajados(minTrabajados);
            restTemplate.put(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, jornada, Jornadas.class);
        } else {
            model.addAttribute("error", "Por favor fiche la entrada antes de fichar la salida.");
            
        }

        return "redirect:/";
    }

    @GetMapping("/incidencias")
    public String recuperarIncidencia(Principal principal, Map<String, Object> model) {

        String idEmpleado;

        try {
            Empleado empleadoActual = restTemplate.getForObject(DATOSMANAGER_STRING + principal.getName(), Empleado.class);
            idEmpleado = empleadoActual.getIdEmpleado();
        } catch (Exception e) {
            return "401";
        }

        // Jornadas[] jornadaArray;
        // List<Jornadas> jornadas;
        Jornadas jornadaHoy = new Jornadas();
        LocalDate fecha = LocalDate.now();

        try {
            jornadaHoy = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/"+ fecha, Jornadas.class);

        } catch (HttpClientErrorException.NotFound ex) {
        }
        model.put("jor", jornadaHoy);
        model.put("accion", "/incidencia/publicarIncidencia");
        return "incidencias";
        
    }

    @PostMapping("/incidencia/publicarIncidencia")
public String publicarIncidencia(@Validated Jornadas jornada, BindingResult result, Map<String, Object> model) {
    if (result.hasErrors()) {
        
        model.put("org.springframework.validation.BindingResult.jor", result);
        
        return "incidencias";
    }
    
    try {
        jornada.setEstado(String.valueOf(1));
        restTemplate.put(JORNADASMANAGER_STRING + jornada.getIdEmpleado() + "/"+ jornada.getFecha() , jornada, Jornadas.class);
    } catch (Exception e) {

    }
    
    return "redirect:/incidencias";
}
    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        model.addAttribute("incidenciaForm", new Jornadas());
        return "formulario";
    }
    
    @PostMapping("/form")
    public String procesarFormulario(@ModelAttribute Jornadas incidenciaForm) {
        String incidencia = incidenciaForm.getIncidencia();
        // hacer algo con el valor de incidencia
        return "resultado";
    }
}
