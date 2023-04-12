package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import es.upm.dit.isst.tfg.tfgwebapp.model.Puesto;

@Controller
public class PuestoController {
    public final String PUESTOMANAGER_STRING = "http://localhost:8083/puestos/";
    public static final String VISTA_LISTA_PUESTO = "lista_puestos";
    public static final String VISTA_FORMULARIO_PUESTO = "form_puesto";
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("puestos/lista")
    public String lista(Model model, Principal principal) {
        List<Puesto> lista = new ArrayList<Puesto>();
        lista = Arrays.asList(restTemplate.getForEntity(PUESTOMANAGER_STRING + "libres/",
                Puesto[].class).getBody());
        model.addAttribute("puesto", lista);
        return VISTA_LISTA_PUESTO;
    }

    @GetMapping("puestos/crear")
    public String crear(Map<String, Object> model) {
        Puesto puesto = new Puesto();
        model.put("Puesto", puesto);
        model.put("accion", "guardar");
        return VISTA_FORMULARIO_PUESTO;
    }

    @PostMapping("puestos/guardar")
    public String guardar(@Validated Puesto puesto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("Puesto", puesto);
            return VISTA_FORMULARIO_PUESTO;
        }
        try {
            restTemplate.postForObject(PUESTOMANAGER_STRING, puesto, Puesto.class);
        } catch (Exception e) {
        }
        return "redirect:/" + "lista";
    }

}
