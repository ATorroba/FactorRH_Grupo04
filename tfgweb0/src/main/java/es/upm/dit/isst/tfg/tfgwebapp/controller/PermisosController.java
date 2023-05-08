package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.util.stream.Collectors;


import java.io.Console;
import java.lang.reflect.Array;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;

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
public class PermisosController {

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("permisos")
    public String lista(Model model, Principal principal) {
        List<Permisos> lista = new ArrayList<Permisos>();
        lista = Arrays.asList(restTemplate.getForEntity("http://localhost:8083/permisos/",
                Permisos[].class).getBody());
        model.addAttribute("Totpermisos", lista);

        model.addAttribute("date", ZonedDateTime.now());

        return "permisos";
    }
    
    @GetMapping("/permisos/crear")
    public String crear(Map<String, Object> model) {
        Permisos permiso = new Permisos();
        model.put("Permisos", permiso);
        model.put("accion", "guardar");
        return "form_permiso";
    }

    @PostMapping("permisos/guardar")
    public String guardar(@Validated Permisos permiso, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("Permiso", permiso);
            return "form_permiso";
        }
        try {
            restTemplate.postForObject("http://localhost:8083/permisos/", permiso, Permisos.class);
        } catch (Exception e) {
        }
        return "redirect:/" + "permisos";
    }

    @PostMapping("permisos/actualizar")
    public String actualizar(@Validated Permisos permiso, BindingResult result, Map<String, Object> model) {

        if (result.hasErrors()) {

            model.put("Permisos", permiso);
            List<ObjectError> r = result.getAllErrors();
            model.put("result", r);// .getDefaultMessage());
            model.put("date", ZonedDateTime.now());

            return "form_permisos";

        }

        try {
            restTemplate.put("http://localhost:8083/permisos/" + permiso.getIdEmpleado() + "/" + permiso.getEjercicio() ,

                    permiso, Permisos.class);

        } catch (Exception e) {
        }

        return "redirect:/" + "permisos";

    }

    @GetMapping("permisos/eliminar/{idEmpleado}/{ejercicio}")
    public String eliminar(@PathVariable(value = "idEmpleado") String id, @PathVariable(value = "ejercicio") String ejercicio) {
        System.out.println("Antes");
        restTemplate.delete("http://localhost:8083/permisos/" + id + "/" + ejercicio);
        System.out.println(ejercicio);
        return "redirect:/" + "permisos";

    }

    @GetMapping("permisos/editar/{idEmpleado}/{ejercicio}")
    public String editar(@PathVariable(value = "idEmpleado") String idEmpleado, @PathVariable(value = "ejercicio") Integer ejercicio, Map<String, Object> model, Principal principal) {

        Permisos permiso = null;
        try {
            permiso = restTemplate.getForObject("http://localhost:8083/permisos/" + idEmpleado + "/" + ejercicio, Permisos.class);

        } catch (HttpClientErrorException.NotFound ex) {
            System.out.println("idEmpleado");
        }

        model.put("Permisos", permiso);
        model.put("accion", "../../actualizar");
        model.put("date", LocalDate.now());

        return permiso != null ? "form_permiso" : "redirect:/" + "permisos";

    }
    
}