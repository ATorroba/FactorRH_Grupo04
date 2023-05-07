package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;
import es.upm.dit.isst.tfg.tfgwebapp.model.IncidenciaNomina;

@Controller

public class IncidenciaNominaController {
    // API REST que devuelve los datos
    public final String INCIDENCIAS_N_MANAGER_STRING = "http://localhost:8083/incidencias_n/";
    public final String INCIDENCIAS_N_E_MANAGER_STRING = "http://localhost:8083/incidencias_n/empleado";
    // API para los empleados
    public final String RHMANAGER_STRING = "http://localhost:8083/empleados/";


     // Formularios de manejo
    public static final String VISTA_LISTA_INCIDENCIAS_N = "lista_incidencias_n";
    public static final String VISTA_FORM_INC_N = "form_incidencia_n";
    public static final String INCIDENCIAS_EMPLEADO = "incidencia_n_empleado";
    public static final String INCIDENCIAS_EMPLEADO_VACIO = "incidencia_n_empleado_V";
 
    private RestTemplate restTemplate = new RestTemplate();

   

    @GetMapping("/incidencias_n")
    public String inicio() {
        return "redirect:/incidencias_n/lista";
    }

    // Lista de incidencias en nómina
    @GetMapping("incidencias_n/lista")
    public String lista(Model model, Principal principal) {
        List<IncidenciaNomina> lista = new ArrayList<IncidenciaNomina>();
        lista = Arrays.asList(restTemplate.getForEntity(INCIDENCIAS_N_MANAGER_STRING,
        IncidenciaNomina[].class).getBody());
        model.addAttribute("incs_n", lista);
        return VISTA_LISTA_INCIDENCIAS_N;
    }

    // Crear una incidencia de nómina
    @GetMapping("incidencias_n/crear")
    public String crear(Map<String, Object> model) {
        //Buscamos empleados a los que poder imputar incidencias: los que estén de alte aen el día:
        List<Empleado> lista_empleados = new ArrayList<Empleado>();
        lista_empleados = Arrays.asList(restTemplate.getForEntity(RHMANAGER_STRING, Empleado[].class).getBody());
        model.put("empleados", lista_empleados);
        //Creamos el objeto que guardará la incidencia
        IncidenciaNomina inc_n = new IncidenciaNomina();
        //System.out.printf("IncidenciaNomina CREAR%n");
        model.put("incidenciaNomina", inc_n);
        model.put("accion", "guardar");
        return VISTA_FORM_INC_N;
    }

    @PostMapping("incidencias_n/guardar")
    public String guardar(@Validated IncidenciaNomina inc_n, BindingResult result) {
        System.out.printf("IncidenciaNomina GUARDAR%n");
        if (result.hasErrors()) {
            return VISTA_FORM_INC_N;
        }
        try {
            System.out.printf("IncidenciaNomina GUARDAR TRY%n");
            restTemplate.postForObject(INCIDENCIAS_N_MANAGER_STRING, inc_n, IncidenciaNomina.class);
        } catch (Exception e) {
        }
        return "redirect:/incidencias_n";
    }

    // Editar la incidencia
    @GetMapping("incidencias_n/editar/{id}")
    public String editar(@PathVariable(value = "id") Integer id,
            Map<String, Object> model, Principal principal) {

        //System.out.printf("IncidenciaNomina EDITAR 1 %n");
        IncidenciaNomina inc_n = null;
        try {
            inc_n = restTemplate.getForObject(INCIDENCIAS_N_MANAGER_STRING + id, IncidenciaNomina.class);
        } catch (HttpClientErrorException.NotFound ex) {
        }
        model.put("inc_n", inc_n);
        model.put("accion", "actualizar");
        return inc_n != null ? VISTA_FORM_INC_N : "redirect:/incidencias_n";
    }

    @PostMapping("incidencias_n/editar/actualizar")
    public String actualizar(@Validated IncidenciaNomina inc_n, BindingResult result) {
        //System.out.printf("IncidenciaNomina EDITAR 2 %n");
        if (result.hasErrors()) {
            return VISTA_FORM_INC_N;
        }
        try {
            restTemplate.put(INCIDENCIAS_N_MANAGER_STRING + inc_n.getIdRemesa(),
            inc_n, IncidenciaNomina.class);
        } catch (Exception e) {
        }
        return "redirect:/incidencias_n";
    }

    @GetMapping("incidencias_n/eliminar/{id}")
    public String eliminar(@PathVariable(value = "id") Integer id) {
        restTemplate.delete(INCIDENCIAS_N_MANAGER_STRING + id);
        return "redirect:/incidencias_n";
    }


    @GetMapping("/incidencias_empleado")
    public String incidencias_n_mpleado(Principal principal, Model model) {
        try {
            // System.out.println(principal.getName());
            RestTemplate restTemplate1 = new RestTemplate();
            Empleado empleado2 = (restTemplate1.getForEntity("http://localhost:8083/datos/"
                    + principal.getName(), Empleado.class).getBody());
            if (empleado2 != null) {
                String idEmpLog = empleado2.getIdEmpleado();
                //System.out.printf("Empleado NUMERO %s %n", idEmpLog);
                List<IncidenciaNomina> lista_inc = new ArrayList<IncidenciaNomina>();
                try {
                    lista_inc = Arrays
                            .asList(restTemplate.getForEntity(INCIDENCIAS_N_E_MANAGER_STRING + idEmpLog, IncidenciaNomina[].class)
                                    .getBody());
                    //System.out.printf("leida lista de recibos.%n");
                } catch (HttpClientErrorException.NotFound ex) {
                }
                // Filtrar y dejar solo incidencias confirmadas (con remesa pagada)
                List<IncidenciaNomina> inc_n_Filtradas = lista_inc.stream()
                        .filter(IncidenciaNomina -> "4".equals(IncidenciaNomina.getIdRemesa().getEstado()))
                        .collect(Collectors.toList());
                model.addAttribute("recibos", inc_n_Filtradas);
                 if (inc_n_Filtradas.size()==0) return INCIDENCIAS_EMPLEADO_VACIO;
            }
            return INCIDENCIAS_EMPLEADO;
        } catch (Exception e) {
            return "401";
        }
    }
    
}
