package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.security.Principal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

import es.upm.dit.isst.tfg.tfgwebapp.model.Candidato;

@Controller
public class CandidatoController {
    public final String CANDIDATOMANAGER_STRING = "http://localhost:8083/candidatos/";
    public static final String VISTA_LISTA_CANDIDATO = "lista_candidatos";
    public static final String VISTA_FORMULARIO_CANDIDATO = "form_candidatos";
    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("candidatos/lista")
    public String lista(Model model, Principal principal) {
        List<Candidato> lista = new ArrayList<Candidato>();
        lista = Arrays.asList(restTemplate.getForEntity(CANDIDATOMANAGER_STRING,
                Candidato[].class).getBody());
        model.addAttribute("candidato", lista);

        model.addAttribute("date", ZonedDateTime.now());

        return VISTA_LISTA_CANDIDATO;
    }

    @GetMapping("procesos/seleccioni/candidatos/datos/{id}")
    public String datos(Model model, @PathVariable String id) {
        Candidato can1 = new Candidato();
        try {

            Candidato can2 = restTemplate.getForObject("http://localhost:8083//candidatos/" + id, Candidato.class);

            if (can2 != null) {
                can1 = can2;
                model.addAttribute("candidato", can1);
                model.addAttribute("date", ZonedDateTime.now());

                return "datoscandidato";
            } else {
                return "401";
            }
        } catch (Exception e) {
            return "403";
        }

    }

    @GetMapping("procesos/seleccionf/candidatos/datos/{id}")
    public String datos2(Model model, @PathVariable String id) {
        Candidato can1 = new Candidato();
        try {

            Candidato can2 = restTemplate.getForObject("http://localhost:8083//candidatos/" + id, Candidato.class);

            if (can2 != null) {
                can1 = can2;
                model.addAttribute("candidato", can1);
                model.addAttribute("date", ZonedDateTime.now());

                return "datoscandidato";
            } else {
                return "401";
            }
        } catch (Exception e) {
            return "403";
        }

    }

    @GetMapping("candidatos/crear")
    public String crear(Map<String, Object> model) {
        Candidato candidato = new Candidato();
        model.put("candidato", candidato);
        model.put("accion", "guardar");
        return VISTA_FORMULARIO_CANDIDATO;
    }

    @PostMapping("candidatos/guardar")
    public String guardar(@Validated Candidato candidato, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<ObjectError> r = result.getAllErrors();
            model.addAttribute("candidato", candidato);
            model.addAttribute("result", r);

            return VISTA_FORMULARIO_CANDIDATO;
        }
        try {
            restTemplate.postForObject(CANDIDATOMANAGER_STRING, candidato, Candidato.class);
        } catch (Exception e) {
            return VISTA_FORMULARIO_CANDIDATO;

        }
        return "redirect:/" + "candidatos/lista";
    }

    @GetMapping("candidatos/eliminar/{idcandidato}")

    public String eliminar(@PathVariable(value = "idcandidato") String id) {

        restTemplate.delete(CANDIDATOMANAGER_STRING + id);

        return "redirect:/" + "candidatos/lista";

    }

}
