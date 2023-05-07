package es.upm.dit.isst.tfg.tfgwebapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import es.upm.dit.isst.tfg.tfgwebapp.model.Jornadas;
import es.upm.dit.isst.tfg.tfgwebapp.model.Empleado;

import org.springframework.validation.annotation.Validated;
import org.springframework.validation.BindingResult;
import java.util.Map;


@Controller
public class JornadasController {

    public final String JORNADASMANAGER_STRING = "http://localhost:8083/jornadas/";
    public final String JORNADASESTADOMANAGER_STRING = "http://localhost:8083/jornadas/estado/";
    public final String DATOSMANAGER_STRING = "http://localhost:8083/datos/";
    private RestTemplate restTemplate = new RestTemplate();
    public static final String VISTA_LISTA_JORNADAS = "lista_jornadas";
    public static final String VISTA_LISTA_JORNADAS_ESPECIFICAS = "lista_jornadas_especificas";
    public static final String VISTA_FORM_JORNADAS_NUEVA = "form_crearJornada";
    public static final String VISTA_FORM_JORNADAS_EDITAR = "form_editarJornada";
    
    @GetMapping("/jornadas/lista")
    public String listaJornadas(Principal principal, Model model) {

        List <Jornadas> lista = new ArrayList<Jornadas>();
        try{
            lista = Arrays.asList(restTemplate.getForEntity(JORNADASMANAGER_STRING, Jornadas[].class).getBody());
        } catch (Exception e) {
            return "401";
        }
        
        Collections.sort(lista, new Comparator<Jornadas>() {
        @Override
        public int compare(Jornadas j1, Jornadas j2) {
            return j2.getFecha().compareTo(j1.getFecha()); 
        }
        });
        model.addAttribute("jornadas", lista);
        return VISTA_LISTA_JORNADAS;
    }

    @GetMapping("/jornadas/porvalidar")
    public String listaJornadasSinValidad(Principal principal, Model model) {

        List <Jornadas> listaPorValidar = new ArrayList<Jornadas>();
        try{
            listaPorValidar = Arrays.asList(restTemplate.getForEntity(JORNADASESTADOMANAGER_STRING + "1", Jornadas[].class).getBody());
        } catch (HttpClientErrorException.NotFound e) {
            listaPorValidar = new ArrayList<>();
        } catch (Exception e) {
        }
        
        Collections.sort(listaPorValidar, new Comparator<Jornadas>() {
        @Override
        public int compare(Jornadas j1, Jornadas j2) {
            return j2.getFecha().compareTo(j1.getFecha()); 
        }
        });
        model.addAttribute("jornadasPorValidar", listaPorValidar);
        model.addAttribute("opcionElegida", "porValidar");
        model.addAttribute("tipoTitulo", "Porvalidar");
        return VISTA_LISTA_JORNADAS_ESPECIFICAS;
    }

    @GetMapping("/jornadas/validadas")
    public String listaJornadasValidadas(Principal principal, Model model) {

        List <Jornadas> listaValidada = new ArrayList<Jornadas>();
        try{
            listaValidada = Arrays.asList(restTemplate.getForEntity(JORNADASESTADOMANAGER_STRING + "2", Jornadas[].class).getBody());
        } catch (HttpClientErrorException.NotFound e) {
            listaValidada = new ArrayList<>();
        } catch (Exception e) {
        }
        listaValidada = Arrays.asList(restTemplate.getForEntity(JORNADASESTADOMANAGER_STRING + "2", Jornadas[].class).getBody());
        Collections.sort(listaValidada, new Comparator<Jornadas>() {
            @Override
            public int compare(Jornadas j1, Jornadas j2) {
                return j2.getFecha().compareTo(j1.getFecha()); 
            }
            });
        model.addAttribute("jornadasValidadas", listaValidada);
        model.addAttribute("opcionElegida", "Validada");
        model.addAttribute("tipoTitulo", "Validada");
        return VISTA_LISTA_JORNADAS_ESPECIFICAS;
    }

    @GetMapping("/jornadas/especificas/{estado}/{incidencias}")
    public String listaJornadasEspecificas(Principal principal, Model model, @PathVariable(value = "estado") String estado, @PathVariable(value = "incidencias") String incidencias) {

        List <Jornadas> listaEspecifica = new ArrayList<Jornadas>();
        try{
            listaEspecifica = Arrays.asList(restTemplate.getForEntity(JORNADASESTADOMANAGER_STRING + estado + "/" + incidencias, Jornadas[].class).getBody());
        } catch (HttpClientErrorException.NotFound e) {
            listaEspecifica = new ArrayList<>();
        } catch (Exception e) {
        }
        
        Collections.sort(listaEspecifica, new Comparator<Jornadas>() {
        @Override
        public int compare(Jornadas j1, Jornadas j2) {
            return j2.getFecha().compareTo(j1.getFecha()); 
        }
        });

        if(estado.equals("1") && incidencias.equals("f1")){
            model.addAttribute("tipoTitulo", "porValidarNoFicho");
        }else if (estado.equals("1") && incidencias.equals("f2")){
            model.addAttribute("tipoTitulo", "porValidarFichoMal");
        }else if (estado.equals("1") && incidencias.equals("f3")){
            model.addAttribute("tipoTitulo", "porValidarFicho");
        }else if (estado.equals("2") && incidencias.equals("f1")){
            model.addAttribute("tipoTitulo", "ValidadoNoFicho");
        }else if (estado.equals("2") && incidencias.equals("f2")){
            model.addAttribute("tipoTitulo", "ValidadoFichoMal");
        }else if (estado.equals("2") && incidencias.equals("f3")){
            model.addAttribute("tipoTitulo", "ValidadoFicho");
        }
        model.addAttribute("jornadasEspecificas", listaEspecifica);
        model.addAttribute("opcionElegida", "Especifico");
        return VISTA_LISTA_JORNADAS_ESPECIFICAS;
    }

    @GetMapping("jornadas/crear")
    public String crear(Map<String, Object> model) {
        Jornadas jornada = new Jornadas();
        model.put("jornada", jornada);
        return VISTA_FORM_JORNADAS_NUEVA;
    }

    @PostMapping("jornadas/guardar")
    public String guardar(@Validated Jornadas jornada, BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            model.put("jornada", jornada);
            return VISTA_FORM_JORNADAS_NUEVA;
        }
        try {
            restTemplate.postForObject(JORNADASMANAGER_STRING, jornada, Jornadas.class);
        } catch (Exception e) {
        }
        return "redirect:/jornadas/lista";
    }

    @GetMapping("jornadas/validar/{idEmpleado}/{fecha}")
    public String Validar(@PathVariable(value = "idEmpleado") String idEmpleado, @PathVariable(value = "fecha") String fecha) {
        Jornadas jornada = new Jornadas();
        System.out.println(idEmpleado);
        try {
            jornada = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, Jornadas.class);
        } catch (Exception e) {
        }

        jornada.setEstado("2");
        restTemplate.put(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, jornada, Jornadas.class);
        
        return "redirect:/jornadas/lista";
    }

    @GetMapping("jornadas/revisar/{idEmpleado}/{fecha}")
    public String Revisar(@PathVariable(value = "idEmpleado") String idEmpleado, @PathVariable(value = "fecha") String fecha) {
        Jornadas jornada = new Jornadas();
        try {
            jornada = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, Jornadas.class);
        } catch (Exception e) {
        }

        jornada.setEstado("1");
        restTemplate.put(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, jornada, Jornadas.class);
        return "redirect:/jornadas/lista";
    }

    @GetMapping("jornadas/delete/{idEmpleado}/{fecha}")
    public String delete(@PathVariable(value = "idEmpleado") String idEmpleado, @PathVariable(value = "fecha") String fecha) {
        try {
            restTemplate.delete(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha);
        } catch (Exception e) {
        }
        return "redirect:/jornadas/lista";
    }

    @GetMapping("jornadas/editar/{idEmpleado}/{fecha}")
    public String editar(@PathVariable(value = "idEmpleado") String idEmpleado, @PathVariable(value = "fecha") String fecha,  Map<String, Object> model) {
        Jornadas jornada = new Jornadas();
        try {
            jornada = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, Jornadas.class);
        } catch (Exception e) {
        }

        model.put("jornada", jornada);
        return VISTA_FORM_JORNADAS_EDITAR;
    }

    @PostMapping("jornadas/actualizar/{idEmpleado}/{fecha}")
    public String actualizar(@Validated Jornadas jornada, BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            model.put("jornada", jornada);
            return VISTA_FORM_JORNADAS_EDITAR;
        }
        try {
            restTemplate.put(JORNADASMANAGER_STRING, jornada, Jornadas.class);
        } catch (Exception e) {
        }
        return "redirect:/jornadas/lista";
    }

    @PostMapping("fichar/entrada")
    public String ficharEntrada(Principal principal, Model model) {
        String idEmpleado;

        try {
            Empleado empleadoActual = restTemplate.getForObject(DATOSMANAGER_STRING + principal.getName(), Empleado.class);
            idEmpleado = empleadoActual.getIdEmpleado();
        } catch (Exception e) {
            return "401";
        }

        LocalDate fecha = LocalDate.now();
        String fechaString = fecha.toString();

        LocalTime horaActual = LocalTime.now(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); 
        String horaActualString = horaActual.format(formatter);
        
        Jornadas jornada;

        try {
            jornada = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, Jornadas.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                jornada = null;
            } else {
                throw e;
            }
        }

        if (jornada == null) {
            jornada = new Jornadas();
            jornada.setIdEmpleado(idEmpleado);
            jornada.setFecha(fechaString);
            jornada.setHora_entrada(horaActualString);
            jornada.setEstado("1");
            jornada.setIncidencia("f3");
            restTemplate.postForObject(JORNADASMANAGER_STRING, jornada, Jornadas.class);
        } else if (jornada.getHora_entrada() == null){
            jornada.setHora_entrada(horaActualString);
            jornada.setEstado("1");
            jornada.setIncidencia("f3");
            restTemplate.put(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, jornada, Jornadas.class);
        }else {
            model.addAttribute("error", "Ya ha fichado la entrada, no puede volver a fichar su entrada hasta mañana");
            
        }
    
        return "redirect:/"; 
    }  

    @PostMapping("fichar/salida")
    public String ficharSalida(Principal principal, Model model) {
        
        String idEmpleado;

        try {
            Empleado empleadoActual = restTemplate.getForObject(DATOSMANAGER_STRING + principal.getName(), Empleado.class);
            idEmpleado = empleadoActual.getIdEmpleado();
        } catch (Exception e) {
            return "401";
        }

        LocalDate fecha = LocalDate.now();
        LocalTime horaActual = LocalTime.now(ZoneId.systemDefault());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss"); 
        String horaActualString = horaActual.format(formatter);

        Jornadas jornada;

        try {
            jornada = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, Jornadas.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                jornada = null;
            } else {
                throw e;
            }
        }

        if (jornada == null || jornada.getHora_entrada() == null) {
            model.addAttribute("error", "Por favor fiche la entrada antes de fichar la salida.");
           
        } else if (jornada.getHora_salida() == null && horaActual.isAfter(LocalTime.parse(jornada.getHora_entrada(), formatter))) {
            jornada.setHora_salida(horaActualString);
            jornada.setIncidencia("f3");
            jornada.setEstado("1");
            restTemplate.put(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, jornada, Jornadas.class);
        } else {
            model.addAttribute("error", "Por favor fiche la entrada antes de fichar la salida.");
            
        }

        return "redirect:/";
    }

    @GetMapping("/incidencias")
    public String recuperarIncidencia(Principal principal, Map<String, Object> model) {

        String idEmpleado;

        try {
            Empleado empleadoActual = restTemplate.getForObject(DATOSMANAGER_STRING + principal.getName(), Empleado.class);
            idEmpleado = empleadoActual.getIdEmpleado();
        } catch (Exception e) {
            return "401";
        }

        // Jornadas[] jornadaArray;
        // List<Jornadas> jornadas;
        Jornadas jornadaHoy = new Jornadas();
        LocalDate fecha = LocalDate.now();

        try {
            jornadaHoy = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/"+ fecha, Jornadas.class);

        } catch (HttpClientErrorException.NotFound ex) {
        }
        model.put("jor", jornadaHoy);
        model.put("accion", "/incidencia/publicarIncidencia");
        return "incidencias";
        
    }

    @PostMapping("/incidencia/publicarIncidencia")
    public String publicarIncidencia(@Validated Jornadas jornada, BindingResult result, Map<String, Object> model) {
    if (result.hasErrors()) {
        
        model.put("org.springframework.validation.BindingResult.jor", result);
        
        return "incidencias";
    }
    
    try {
        jornada.setEstado(String.valueOf(1));
        restTemplate.put(JORNADASMANAGER_STRING + jornada.getIdEmpleado() + "/"+ jornada.getFecha() , jornada, Jornadas.class);
    } catch (Exception e) {

    }
    
    return "redirect:/incidencias";
}
    @GetMapping("/form")
    public String mostrarFormulario(Model model) {
        model.addAttribute("incidenciaForm", new Jornadas());
        return "formulario";
    }
    
    @PostMapping("/form")
    public String procesarFormulario(@ModelAttribute Jornadas incidenciaForm) {
        String incidencia = incidenciaForm.getIncidencia();
        // hacer algo con el valor de incidencia
        return "resultado";
    }
}
