package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.security.Principal;

import java.time.ZonedDateTime;


import es.upm.dit.isst.tfg.tfgwebapp.model.Ausencias;
import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;

import java.util.List;

import java.util.Map;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.client.RestTemplate;

@Controller
public class AusenciasController {
    public final String RHMANAGERGER_STRING = "http://localhost:8083/ausencias/";

    public static final String VISTA_LISTA = "lista";

    public static final String VISTA_FORMULARIO = "formulario";

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/gestion")

    public String gestion(Model model, Principal principal) {
        Ausencias Asencia = new Ausencias();
        model.addAttribute("Ausencia", Asencia);
        model.addAttribute("date", ZonedDateTime.now());

        return "gestion";

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
                    Ausencia.setEmpleado(empleado2.getIdEmpleado());

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
}