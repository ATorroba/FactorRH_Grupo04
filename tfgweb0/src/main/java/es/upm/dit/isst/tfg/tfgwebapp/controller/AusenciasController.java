package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.util.stream.Collectors;

import java.io.Console;
import java.lang.reflect.Array;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

import es.upm.dit.isst.tfg.tfgwebapp.model.Ausencias;
import es.upm.dit.isst.tfg.tfgwebapp.model.Permisos;

import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;
import java.util.Arrays;

import java.util.List;

import java.util.Map;

import javax.validation.Constraint;
import javax.validation.Valid;
import javax.validation.executable.ValidateOnExecution;

import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.client.HttpClientErrorException;

import org.springframework.web.client.RestTemplate;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AusenciasController {
    public final String RHMANAGERGER_STRING = "http://localhost:8083/ausencias/";

    public static final String VISTA_LISTA = "lista";

    public static final String VISTA_FORMULARIO = "formulario";

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/gestion")

    public String gestion(Model model, Principal principal) {

        Ausencias Asencia = new Ausencias();
        Empleado empleado2 = restTemplate.getForObject("http://localhost:8083/datos/" + principal.getName(),
                Empleado.class);
        List<Ausencias> aus = new ArrayList<Ausencias>();
        aus = Arrays.asList(restTemplate.getForEntity("http://localhost:8083/ausencias/" + empleado2.getIdEmpleado(),

                Ausencias[].class).getBody());

        model.addAttribute("aus", aus);

        model.addAttribute("Ausencia", Asencia);
        model.addAttribute("date", ZonedDateTime.now());

        return "gestion";

    }

    @GetMapping("/listaaus")

    public String listaaus(Model model, Principal principal) {

        List<Ausencias> aus = new ArrayList<Ausencias>();
        aus = Arrays.asList(restTemplate.getForEntity("http://localhost:8083/ausencias/",

                Ausencias[].class).getBody());

        model.addAttribute("aus", aus);
        model.addAttribute("date", ZonedDateTime.now());

        return "lista_ausencias";

    }

    @GetMapping("/aceptaraus/{idausencia}")

    public String aceptaraus(@PathVariable(value = "idausencia") Integer idausencia, Model model, Principal principal) {

        Ausencias aus = restTemplate.getForObject("http://localhost:8083/ausenciasaus/" + idausencia, Ausencias.class);

        restTemplate.postForObject("http://localhost:8083/aceptaraus/" + idausencia, aus, Ausencias.class);

        model.addAttribute("aus", aus);
        model.addAttribute("date", ZonedDateTime.now());

        return "redirect:/listaaus";

    }

    @GetMapping("/denegaraus/{idausencia}")

    public String denegaraus(@PathVariable(value = "idausencia") Integer idausencia, Model model, Principal principal) {

        Ausencias aus = restTemplate.getForObject("http://localhost:8083/ausenciasaus/" + idausencia, Ausencias.class);

        restTemplate.postForObject("http://localhost:8083/denegaraus/" + idausencia, aus, Ausencias.class);

        model.addAttribute("aus", aus);
        model.addAttribute("date", ZonedDateTime.now());

        return "redirect:/listaaus";

    }

    @GetMapping("/vacaciones")
    public String getAusenciasPorIdEmpleado(Principal principal, Model model) {
        try {
            RestTemplate restTemplate1 = new RestTemplate();

            Empleado empleado2 = restTemplate1.getForObject("http://localhost:8083/datos/" + principal.getName(),
                    Empleado.class);
            String idEmpLog = empleado2.getIdEmpleado();

            if (empleado2 != null) {
                String url = "http://localhost:8083/ausencias/" + idEmpLog;
                RestTemplate restTemplate = new RestTemplate();
                Ausencias[] ausenciasArray = restTemplate.getForObject(url, Ausencias[].class);
                List<Ausencias> ausencias = Arrays.asList(ausenciasArray);

                url = "http://localhost:8083/permisos/" + idEmpLog;
                restTemplate = new RestTemplate();
                Permisos[] permisosArray = restTemplate.getForObject(url, Permisos[].class);
                List<Permisos> permisos = Arrays.asList(permisosArray);
                int vacacionesTotales = permisos.get(0).getVacaciones();

                // Filtrar las ausencias por tipo_ausencia = "vac"
                List<Ausencias> vacaciones = ausencias.stream()
                        .filter(a -> "vac".equals(a.getTipo_ausencia()))
                        .collect(Collectors.toList());

                int vacacionesGastadas = 0;
                for (Ausencias vacacion : vacaciones) {
                    if (vacacion.getN_dias() != null) {
                        vacacionesGastadas += vacacion.getN_dias();
                    } else {
                        continue;
                    }

                }
                int vacacionesDisponibles = vacacionesTotales - vacacionesGastadas;

                model.addAttribute("vacacionesTotales", vacacionesTotales);
                model.addAttribute("vacacionesDisponibles", vacacionesDisponibles);
                model.addAttribute("vacaciones", vacaciones);
                return "vacaciones";
            }
        } catch (Exception e) {
            System.out.println(e);

            return "401";

        }
        return "401";
    }

    @PostMapping("/guardarausencia")

    public String guardar(@Validated Ausencias Ausencia, BindingResult result, Map<String, Object> model,
            Principal principal) {
        Empleado empleado1 = new Empleado();
        if (result.hasErrors()) {
            System.out.println("errores");
            model.put("Ausencia", Ausencia);
            List<ObjectError> r = result.getAllErrors();
            // r.stream().map(a -> a);

            model.put("result", r);// .getDefaultMessage());

            model.put("date", ZonedDateTime.now());

            // 0 System.out.println(r);
            // System.out.println(r.get(0));

            return "gestion";

        } else {
            try {
                Empleado empleado2 = restTemplate.getForObject("http://localhost:8083/datos/"

                        + principal.getName(), Empleado.class);
                if (empleado2 != null) {
                    Ausencia.setIdempleado(empleado2.getIdEmpleado());
                    Ausencia.setAutorizada("0");
                    Ausencia.setFecha_comunicacion(ZonedDateTime.now().toLocalDateTime().toString());

                } else {
                    return "401";
                }

                restTemplate.postForObject(RHMANAGERGER_STRING, Ausencia, Ausencias.class);
                System.out.println("result");

            } catch (Exception e) {
                System.out.println(e);
            }
            try {
            } catch (Error e) {
            }
            model.put("Ausencia", Ausencia);
            System.out.println("result2");

            model.put("date", ZonedDateTime.now());
            return "gestion";
        }
    }

    @GetMapping("eliminaraus/{idausencia}")

    public String eliminar(@PathVariable(value = "idausencia") Integer id) {

        restTemplate.delete(RHMANAGERGER_STRING + id);

        return "redirect:/listaaus";

    }
}