package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.upm.dit.isst.tfg.tfgwebapp.model.ConceptoRecibo;
import es.upm.dit.isst.tfg.tfgwebapp.model.Recibo;

@Controller
public class RecibosController {

    // API REST que devuelve los datos
    public final String REMESAMANAGER_STRING = "http://localhost:8083/remesas/";
    public final String RECIBOSREMESAMANAGER_STRING = "http://localhost:8083/recibos/remesa/";
    public final String RECIBOSEMPLEADOMANAGER_STRING = "http://localhost:8083/recibos/empleado/";
    public final String RECIBOS_STRING = "http://localhost:8083/conceptosrec/recibo/";
    // public final String CONCEPTOS_STRING =
    // "http://localhost:8083/recibos/empleado/";

    // Formularios de manejo
    public static final String RECIBOS_REMESA = "recibos_remesa";
    public static final String RECIBOS_EMPLEADO = "recibos_empleado";
    public static final String RECIBO_FORM = "recibo_form";

    private RestTemplate restTemplate = new RestTemplate();

    // Listar recibos de una remesa
    @GetMapping("/remesas/recibos/{id}")
    public String recibos_remesa(@PathVariable(value = "id") Integer id,
            Model model, Principal principal) {

        List<Recibo> lista_recibos = new ArrayList<Recibo>();
        System.out.printf("REMESA NUMERO %d %n", id);
        try {
            lista_recibos = Arrays
                    .asList(restTemplate.getForEntity(RECIBOSREMESAMANAGER_STRING + id, Recibo[].class).getBody());
            // System.out.printf("leida lista de recibos.%n");
        } catch (HttpClientErrorException.NotFound ex) {
        }
        // model.put("remesa", remesa);
        // model.put("accion", "actualizar");
        model.addAttribute("recibos", lista_recibos);
        return RECIBOS_REMESA;
    }

    @GetMapping("/empleado/recibos/{id}")
    public String recibos_empleado(@PathVariable(value = "id") String id, Model model, Principal principal) {

        List<Recibo> lista_recibos = new ArrayList<Recibo>();
        System.out.printf("Empleado NUMERO %s %n", id);
        try {
            lista_recibos = Arrays
                    .asList(restTemplate.getForEntity(RECIBOSEMPLEADOMANAGER_STRING + id, Recibo[].class).getBody());
            System.out.printf("leida lista de recibos.%n");
        } catch (HttpClientErrorException.NotFound ex) {
        }
        // Filtrar los recibos pagados (estado = 4)
        List<Recibo> recibosFiltrados = lista_recibos.stream()
                .filter(recibo -> "4".equals(recibo.getIdRemesa().getEstado()))
                .collect(Collectors.toList());

        model.addAttribute("recibos", recibosFiltrados);
        return RECIBOS_EMPLEADO;
    }

    @GetMapping("/remesas/recibos/recibo/{id}")
    public String recibo_remesa(@PathVariable(value = "id") Integer id, Map<String, Object> model, Principal principal) {

        List<ConceptoRecibo> lista_conceptos = new ArrayList<ConceptoRecibo>();
        try {
            lista_conceptos = Arrays
                    .asList(restTemplate.getForEntity(RECIBOS_STRING + id, ConceptoRecibo[].class).getBody());
            // System.out.printf("leida lista de conceptos.%n");
        } catch (HttpClientErrorException.NotFound ex) {
        }
        model.put("conceptoR", lista_conceptos);
        model.put("origen", "remesa");
        // model.addAttribute("conceptoR", lista_conceptos);

        return RECIBO_FORM;
    }

    // redirección a la misma lista cuando vienen de la web de empleados
    @GetMapping("/empleado/recibos/recibo/{id}")
    public String recibo_empleado(@PathVariable(value = "id") Integer id, Map<String, Object> model, Principal principal) {
    
        List<ConceptoRecibo> lista_conceptos = new ArrayList<ConceptoRecibo>();
        try {
            lista_conceptos = Arrays
                    .asList(restTemplate.getForEntity(RECIBOS_STRING + id, ConceptoRecibo[].class).getBody());
            // System.out.printf("leida lista de conceptos.%n");
        } catch (HttpClientErrorException.NotFound ex) {
        }
        model.put("conceptoR", lista_conceptos);
        model.put("origen", "empleado");
        // model.addAttribute("conceptoR", lista_conceptos);

        return RECIBO_FORM;
    }


}
