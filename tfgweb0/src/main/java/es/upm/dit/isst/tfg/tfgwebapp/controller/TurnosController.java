package es.upm.dit.isst.tfg.tfgwebapp.controller;
import java.security.Principal;
import java.time.LocalTime;

import es.upm.dit.isst.tfg.tfgwebapp.model.Turnos;
import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.Arrays;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.validation.annotation.Validated;
import java.util.Map;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import org.springframework.web.client.RestTemplate;

@Controller
public class TurnosController {

    public final String RHMANAGER_STRING = "http://localhost:8083/empleados/";

    @GetMapping("/turnos")
    public String getTurnosPorIdEmpleado(Principal principal, Model model) {
        try {
            System.out.println(principal.getName());
            RestTemplate restTemplate1 = new RestTemplate();

            Empleado empleado2 = restTemplate1.getForObject("http://localhost:8083/datos/"

                    + principal.getName(), Empleado.class);
            String idEmpLog = empleado2.getIdEmpleado();

            if (empleado2 != null) {
                String url = "http://localhost:8083/turnos/empleado/" + idEmpLog;
                System.out.println(idEmpLog);
                RestTemplate restTemplate = new RestTemplate();
                Turnos[] turnosArray = restTemplate.getForObject(url, Turnos[].class);
                List<Turnos> turnos = Arrays.asList(turnosArray);
                model.addAttribute("turno", turnos);
                return "turnos";
            }
        } catch (Exception e) {
            return "401";

        }
        return "401";
    }

    @GetMapping("/gestiona_turnos")
    public String getTurnos(Principal principal, Model model) {
        try {
            String url = "http://localhost:8083/turnos/";
            RestTemplate restTemplate = new RestTemplate();
            Turnos[] turnosArray = restTemplate.getForObject(url, Turnos[].class);
            List<Turnos> turnos = Arrays.asList(turnosArray);
            model.addAttribute("turno", turnos);
           
            String url1 = "http://localhost:8083/empleados/";
            RestTemplate restTemplate1 = new RestTemplate();
            Empleado[] empleadosArray = restTemplate1.getForObject(url1, Empleado[].class);
            List<Empleado> empleados = Arrays.asList(empleadosArray);
            model.addAttribute("empleadoss",empleados);           
           
            return "gestiona_turnos";
        } catch (Exception e) {
            return "401";

        }
    }

    @GetMapping("/gestiona_turnos/editar_turno/{idEmpleado}")
    public String getTurnoDeEmpleado(@PathVariable(value = "idEmpleado") String idEmpleado, Principal principal, Model model) {
        try {
            String url = "http://localhost:8083/turnos/" + idEmpleado;
            System.out.println(url);
            
            RestTemplate restTemplateLunes = new RestTemplate();
            Turnos turnoLunes = restTemplateLunes.getForObject(url  + "/1", Turnos.class);
            model.addAttribute("turno1", turnoLunes);      
            
            RestTemplate restTemplateMartes = new RestTemplate();
            Turnos turnoMartes = restTemplateMartes.getForObject(url  + "/2", Turnos.class);
            model.addAttribute("turno2", turnoMartes);

            RestTemplate restTemplateMiercoles = new RestTemplate();
            Turnos turnoMiercoles = restTemplateMiercoles.getForObject(url  + "/3", Turnos.class);
            model.addAttribute("turno3", turnoMiercoles);

            RestTemplate restTemplateJueves = new RestTemplate();
            Turnos turnoJueves = restTemplateJueves.getForObject(url  + "/4", Turnos.class);
            model.addAttribute("turno4", turnoJueves);

            RestTemplate restTemplateViernes = new RestTemplate();
            Turnos turnoViernes = restTemplateViernes.getForObject(url  + "/5", Turnos.class);
            model.addAttribute("turno5", turnoViernes);

            RestTemplate restTemplateSabado = new RestTemplate();
            Turnos turnoSabado = restTemplateSabado.getForObject(url  + "/6", Turnos.class);
            model.addAttribute("turno6", turnoSabado);

            RestTemplate restTemplateDomingo = new RestTemplate();
            Turnos turnoDomingo = restTemplateDomingo.getForObject(url  + "/7", Turnos.class);
            model.addAttribute("turno7", turnoDomingo);
           
            return "editar_turno";
        } catch (Exception e) {
            // e.printStackTrace();
            return "403";

        }
    }

    @PostMapping("/gestiona_turnos/editar_turno/{idEmpleado}")
    public String editarTurno(@Validated Turnos turno,BindingResult result, Map<String, Object> model, @PathVariable String idEmpleado) {
        RestTemplate restTemplate = new RestTemplate();
        // if (result.hasErrors()) {
        //     model.put("org.springframework.validation.BindingResult.jor", result);
        //     System.out.println(turno.getHora_entrada());
        //     return "editar_turno";
        // }
        System.out.println(turno.getHora_entrada());
        System.out.println(turno.getHora_salida());
        try {
            restTemplate.put("http://localhost:8083/turnos/" + turno.getIdTurno() , turno, Turnos.class);
         } catch (Exception e) {

    }
    return "redirect:/gestiona_turnos/editar_turno/" + idEmpleado;
}

    @GetMapping("turnos/crear")
        public String crear(Map<String, Object> model) {
            Turnos turno = new Turnos();
            model.put("turno", turno);
            List<Empleado> lista_empleados = generaListaEmpleados();
            model.put("empleados", lista_empleados);
            return "crear_turno";

        }

    @PostMapping("turnos/guardar")
    public String guardar(@Validated Turnos turno, BindingResult result, Map<String, Object> model) {
        
        RestTemplate restTemplate = new RestTemplate();

        if (turno.getHora_entrada() != null && turno.getHora_salida() != null) {
            LocalTime horaEntrada = LocalTime.parse(turno.getHora_entrada());
            LocalTime horaSalida = LocalTime.parse(turno.getHora_salida());
            
            if (!horaEntrada.isBefore(horaSalida)) {
                FieldError error = new FieldError("turno", "hora_entrada", "La hora de entrada debe ser menor que la hora de salida");
                result.addError(error);
            }
        }

        Turnos turnoexistente = new Turnos();
        try{
            turnoexistente = restTemplate.getForObject("http://localhost:8083/turnos/" + turno.getIdEmpleado() +"/" + turno.getIdDia(), Turnos.class);
        }catch (Exception e) {
            turnoexistente = null;
        }

        if (turnoexistente != null){
            FieldError error1 = new FieldError("turno", "idDia", "El empleado seleccionado ya tiene un turno asignado ese día, por favor edítelo en vez de crearlo");
            result.addError(error1);
        }

        if (result.hasErrors()) {
            model.put("turno", turno);
            List<Empleado> lista_empleados = generaListaEmpleados();
            model.put("empleados", lista_empleados);
            List<ObjectError> r = result.getAllErrors();
            model.put("result", r);
            
            return "crear_turno";
        }

        try {
            restTemplate.postForObject("http://localhost:8083/turnos/", turno, Turnos.class);
        } catch (Exception e) {
        }
        return "redirect:/gestiona_turnos";
    }

    public List<Empleado> generaListaEmpleados() {
        RestTemplate restTemplate = new RestTemplate();
        List<Empleado> lista_empleados = new ArrayList<Empleado>();
        try {
            lista_empleados = Arrays.asList(restTemplate.getForEntity(RHMANAGER_STRING, Empleado[].class).getBody());
        } catch (Exception e) {
            // Puedes manejar la excepción aquí si es necesario
        }
        return lista_empleados;
    }
}
