package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.io.Console;
import java.security.Principal;
import java.util.ArrayList;
import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;
import java.util.Arrays;

import java.util.List;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.core.io.ByteArrayResource;

import org.springframework.http.HttpHeaders;

import org.springframework.http.HttpStatus;

import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;

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

@Controller
public class EmpleadoController {
    public final String RHMANAGERGER_STRING = "http://localhost:8083/empleados/";

    public static final String VISTA_LISTA = "lista";

    public static final String VISTA_FORMULARIO = "formulario";

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/")

    public String inicio(Principal principal) {

        if (principal == null || principal.getName().equals("")) {

            return "login";
        }

        else {
            return "redirect:/" + "lista";
        }
    }

    @GetMapping("/login")

    public String loging(Principal principal) {
        if (principal == null || principal.getName().equals("")) {

            return "login";
        }

        else {
            return "redirect:/" + "lista";
        }
    }

    @PostMapping("/login")

    public String loginp() {

        return "lista";

    }

    @GetMapping("/403")

    public String denied() {

        return "403";

    }

    @GetMapping("/datos")

    public String datos(Model model, Principal principal) {

        Empleado empleado1 = new Empleado();

        if (principal.getName().contains("@empleado.es"))

            try {
                System.out.println(principal.getName());

                Empleado empleado2 = restTemplate.getForObject("http://localhost:8083/datos/"

                        + principal.getName(), Empleado.class);

                if (empleado2 != null)

                    empleado1 = empleado2;

            } catch (Exception e) {
            }
        System.out.println(empleado1.getEmail());
        model.addAttribute("empleado", empleado1);

        return "datos";

    }

    @GetMapping("/lista")

    public String lista(Model model, Principal principal) {

        List<Empleado> lista = new ArrayList<Empleado>();

        if (principal == null || principal.getName().equals(""))

            lista = Arrays.asList(restTemplate.getForEntity(RHMANAGERGER_STRING,

                    Empleado[].class).getBody());
        else if (principal.getName().contains("@empleado.es"))

        {
            lista = Arrays.asList(restTemplate.getForEntity(RHMANAGERGER_STRING

            // + "profesor/" + principal.getName()

                    , Empleado[].class).getBody());

        }

        else if (principal.getName().contains("@controlador.es")) {

            {
                lista = Arrays.asList(restTemplate.getForEntity(RHMANAGERGER_STRING

                // + "profesor/" + principal.getName()

                        , Empleado[].class).getBody());

            }

        } else {
            lista = Arrays.asList(restTemplate.getForEntity(RHMANAGERGER_STRING,

                    Empleado[].class).getBody());

        }

        model.addAttribute("empleados", lista);
        // System.out.println(lista.get(0).getPassword());
        return VISTA_LISTA;

    }

    @GetMapping("/crear")

    public String crear(Map<String, Object> model) {

        Empleado Empleado = new Empleado();
        model.put("Empleado", Empleado);

        model.put("accion", "guardar");

        return VISTA_FORMULARIO;

    }

    @PostMapping("/guardar")

    public String guardar(@Validated Empleado Empleado, BindingResult result) {

        if (result.hasErrors()) {

            return VISTA_FORMULARIO;

        } else {
            try {
                restTemplate.postForObject(RHMANAGERGER_STRING, Empleado, Empleado.class);

            } catch (Exception e) {
            }

            return "redirect:" + VISTA_LISTA;
        }
    }

    @GetMapping("/editar/{id}")

    public String editar(@PathVariable(value = "id") String id,

            Map<String, Object> model, Principal principal) {

        if (principal == null || !principal.getName().equals(id))

            return "redirect:/" + VISTA_LISTA;

        Empleado empleado = null;

        try {
            empleado = restTemplate.getForObject(RHMANAGERGER_STRING + id, Empleado.class);

        } catch (HttpClientErrorException.NotFound ex) {
        }

        model.put("Empleado", empleado);

        model.put("accion", "../actualizar");

        return empleado != null ? VISTA_FORMULARIO : "redirect:/" + VISTA_LISTA;

    }

    @PostMapping("/actualizar")

    public String actualizar(@Validated Empleado empleado, BindingResult result) {

        if (result.hasErrors()) {

            return VISTA_FORMULARIO;

        }

        try {
            restTemplate.put(RHMANAGERGER_STRING + empleado.getEmail(),

                    empleado, Empleado.class);

        } catch (Exception e) {
        }

        return "redirect:" + VISTA_LISTA;

    }

    @GetMapping("/eliminar/{idEmpleado}")

    public String eliminar(@PathVariable(value = "idEmpleado") String id) {

        restTemplate.delete(RHMANAGERGER_STRING + id);

        return "redirect:/" + VISTA_LISTA;

    }

    @GetMapping("/aceptar/{idEmpleado}")

    public String aceptar(@PathVariable(value = "idEmpleado") String idEmpleado,

            Map<String, Object> model, Principal principal) {

        if (principal != null) {

            try {
                Empleado empleado = restTemplate.getForObject(RHMANAGERGER_STRING + idEmpleado,

                        Empleado.class);

                if (empleado != null

                        && principal.getName().equals(empleado.getIdEmpleado())) {

                    restTemplate.postForObject(RHMANAGERGER_STRING

                            + empleado.getIdEmpleado() + "/incrementa", empleado, Empleado.class);

                    model.put("Empleado", empleado);

                }

            } catch (HttpClientErrorException.NotFound ex) {
            }

        }

        return "redirect:/" + VISTA_LISTA;

    }
    /*
     * @PostMapping("/upload")
     * 
     * public String uploadFile(@RequestParam("file") MultipartFile file,
     * 
     * @RequestParam("email") String email, Principal principal) {
     * 
     * if (principal == null || principal.getName() == null ||
     * 
     * !principal.getName().equals(email) || file.isEmpty())
     * 
     * return "redirect:/" + VISTA_LISTA;
     * 
     * try {
     * Empleado empleado = restTemplate.getForObject(RHMANAGERGER_STRING + email,
     * Empleado.class);
     * 
     * if (empleado != null && empleado.getStatus() == 3) {
     * 
     * tfg.setStatus(tfg.getStatus() + 1);
     * 
     * tfg.setMemoria(file.getBytes());
     * 
     * restTemplate.put(RHMANAGERGER_STRING + tfg.getEmail(),
     * 
     * tfg, TFG.class);
     * 
     * }
     * 
     * } catch (Exception e) {
     * }
     * 
     * return "redirect:/" + VISTA_LISTA;
     * 
     * }
     *//*
        * @GetMapping("/download/{email}")
        * 
        * @ResponseBody
        * 
        * public ResponseEntity<ByteArrayResource> getFile(@PathVariable String email)
        * {
        * 
        * try {
        * TFG tfg = restTemplate.getForObject(RHMANAGERGER_STRING + email, TFG.class);
        * 
        * if (tfg != null && tfg.getMemoria() != null) {
        * 
        * HttpHeaders header = new HttpHeaders();
        * 
        * header.setContentType(new MediaType("application", "force-download"));
        * 
        * header.set(HttpHeaders.CONTENT_DISPOSITION,
        * 
        * "attachment; filename=\"TFG.pdf\"");
        * 
        * ByteArrayResource resource = new ByteArrayResource(tfg.getMemoria());
        * 
        * return new ResponseEntity<ByteArrayResource>(resource,
        * 
        * header, HttpStatus.CREATED);
        * 
        * }
        * 
        * } catch (Exception e) {
        * }
        * 
        * return new ResponseEntity<ByteArrayResource>(HttpStatus.NOT_FOUND);
        * 
        * }
        */
}