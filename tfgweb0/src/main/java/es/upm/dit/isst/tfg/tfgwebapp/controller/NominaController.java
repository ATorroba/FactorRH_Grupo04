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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.upm.dit.isst.tfg.tfgwebapp.model.Remesa;

@Controller
public class NominaController {

    //API REST que devuelve los datos
    public final String REMESAMANAGER_STRING = "http://localhost:8083/remesas/";
    public final String REMESACALCULAR_STRING = "http://localhost:8083/crear_recibos?idRemesa=";
    
    // Formulario de manejo
    public static final String VISTA_LISTA_REMESAS = "lista_remesas";
    public static final String VISTA_FORM_REMESA = "form_remesa";

    private RestTemplate restTemplate = new RestTemplate();

    @GetMapping("/remesas")
    public String inicio() {
        return "redirect:/remesas/lista";
    }
    
    //Lista de remesas
    @GetMapping("remesas/lista")
    public String lista(Model model, Principal principal) {
        List<Remesa> lista = new ArrayList<Remesa>();
        lista = Arrays.asList(restTemplate.getForEntity(REMESAMANAGER_STRING,
                Remesa[].class).getBody());
        model.addAttribute("remesa", lista);
        return VISTA_LISTA_REMESAS;
    }

    //Crear una remesa
    @GetMapping("remesas/crear")
    public String crear(Map<String, Object> model) {
        Remesa remesa = new Remesa();
        model.put("remesa", remesa);
        model.put("accion", "guardar");
        return VISTA_FORM_REMESA;
    }

    @PostMapping("remesas/guardar")
    public String guardar(@Validated Remesa remesa, BindingResult result) {
        if (result.hasErrors()) {
            return VISTA_FORM_REMESA;
        }
        try {
            //System.out.printf("Creando nueva remesa %s", remesa.getIdRemesa());
            if (remesa.getEstado() == null) {
                remesa.setEstado("1");
            }
            restTemplate.postForObject(REMESAMANAGER_STRING, remesa, Remesa.class);
        } catch (Exception e) {
        }
        return "redirect:/remesas";
    }

    // Editar la remesa
    @GetMapping("remesas/editar/{id}")
    public String editar(@PathVariable(value = "id") Integer id,
            Map<String, Object> model, Principal principal) {
                Remesa remesa = null;
        try {
            remesa = restTemplate.getForObject(REMESAMANAGER_STRING + id, Remesa.class);
        } catch (HttpClientErrorException.NotFound ex) {
        }
        model.put("remesa", remesa);
        model.put("accion", "actualizar");
        return remesa != null ? VISTA_FORM_REMESA : "redirect:/remesas";
    }

    @PostMapping("remesas/editar/actualizar")
    public String actualizar(@Validated Remesa remesa, BindingResult result) {
        if (result.hasErrors()) {
            return VISTA_FORM_REMESA;
        }
        try {
            restTemplate.put(REMESAMANAGER_STRING + remesa.getIdRemesa(),
                    remesa, Remesa.class);
        } catch (Exception e) {
        }
        return "redirect:/remesas";
    }

    @GetMapping("remesas/calcular/{id}")
    public String calcular(@PathVariable(value = "id") Integer id) {
        restTemplate.postForObject(REMESACALCULAR_STRING + id, null, Remesa.class);
        return "redirect:/remesas";
    }

    @GetMapping("remesas/emitir/{id}")
    public String emitir(@PathVariable(value = "id") Integer id) {
        restTemplate.postForObject(REMESAMANAGER_STRING + id +"/emitida", null, Remesa.class);
        return "redirect:/remesas";
    }

    @GetMapping("remesas/pagar/{id}")
    public String pagar(@PathVariable(value = "id") Integer id) {
        restTemplate.postForObject(REMESAMANAGER_STRING + id +"/pagada", null, Remesa.class);
        return "redirect:/remesas";
    }

    @GetMapping("remesas/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Integer id) {
        restTemplate.delete(REMESAMANAGER_STRING + id);
        return "redirect:/remesas";
    }

}
