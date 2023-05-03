package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.upm.dit.isst.tfg.tfgwebapp.model.Recibo;

@Controller
public class RecibosController {

    // API REST que devuelve los datos
    public final String REMESAMANAGER_STRING = "http://localhost:8083/remesas/";
    public final String RECIBOSREMESAMANAGER_STRING = "http://localhost:8083/recibos/remesa/";
    public final String RECIBOSEMPLEADOMANAGER_STRING = "http://localhost:8083/recibos/empleado/";
    public final String RECIBOS_STRING = "http://localhost:8083/recibos/empleado/";
    public final String CONCEPTOS_STRING = "http://localhost:8083/recibos/empleado/";

    // Formularios de manejo
    public static final String RECIBOS_REMESA = "recibos_remesa";
    public static final String RECIBOS_EMPLEADO = "recibos_empleado";

    private RestTemplate restTemplate = new RestTemplate();

    // Listar recibos de una remesa
    @GetMapping("/remesas/recibos/{id}")
    public String recibos_remesa(@PathVariable(value = "id") Integer id,
            Model model, Principal principal) {

        List<Recibo> lista_recibos = new ArrayList<Recibo>();
        try {
            lista_recibos = Arrays
                    .asList(restTemplate.getForEntity(REMESAMANAGER_STRING + id, Recibo[].class).getBody());
                    System.out.printf("leida lista de recibos.%n");      
        } catch (HttpClientErrorException.NotFound ex) {
        }
        // model.put("remesa", remesa);
        // model.put("accion", "actualizar");
        model.addAttribute("recibos", lista_recibos);
        return RECIBOS_REMESA;
    }

}
