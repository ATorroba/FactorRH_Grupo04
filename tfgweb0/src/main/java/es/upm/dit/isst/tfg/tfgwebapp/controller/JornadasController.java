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
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.Map;

@Controller
public class JornadasController {

    public final String JORNADASMANAGER_STRING = "http://localhost:8083/jornadas/";
    public final String JORNADASESTADOMANAGER_STRING = "http://localhost:8083/jornadas/estado/";
    public final String DATOSMANAGER_STRING = "http://localhost:8083/datos/";
    public final String RHMANAGER_STRING = "http://localhost:8083/empleados/";
    public final String JORNADASINCIDENCIA_STRING = "http://localhost:8083/jornadas/empleadoIncidencias/";
    private RestTemplate restTemplate = new RestTemplate();
    public static final String VISTA_LISTA_JORNADAS = "lista_jornadas";
    public static final String VISTA_LISTA_JORNADAS_ESPECIFICAS = "lista_jornadas_especificas";
    public static final String VISTA_FORM_JORNADAS_NUEVA = "form_crearJornada";
    public static final String VISTA_FORM_JORNADAS_EDITAR = "form_editarJornada";

    @GetMapping("/jornadas/lista")
    public String listaJornadas(Principal principal, Model model) {

        List<Jornadas> lista = new ArrayList<Jornadas>();
        try {
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

        List<Jornadas> listaPorValidar = new ArrayList<Jornadas>();
        try {
            listaPorValidar = Arrays
                    .asList(restTemplate.getForEntity(JORNADASESTADOMANAGER_STRING + "1", Jornadas[].class).getBody());
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

        List<Jornadas> listaValidada = new ArrayList<Jornadas>();
        try {
            listaValidada = Arrays
                    .asList(restTemplate.getForEntity(JORNADASESTADOMANAGER_STRING + "2", Jornadas[].class).getBody());
        } catch (HttpClientErrorException.NotFound e) {
            listaValidada = new ArrayList<>();
        } catch (Exception e) {
        }
        listaValidada = Arrays
                .asList(restTemplate.getForEntity(JORNADASESTADOMANAGER_STRING + "2", Jornadas[].class).getBody());
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
       
        List<Jornadas> listaEspecifica = new ArrayList<Jornadas>();
        try {
            listaEspecifica = Arrays.asList(restTemplate
                    .getForEntity(JORNADASESTADOMANAGER_STRING + estado + "/" + incidencias, Jornadas[].class)
                    .getBody());
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

        if (estado.equals("1") && incidencias.equals("f1")) {
            model.addAttribute("tipoTitulo", "porValidarNoFicho");
        } else if (estado.equals("1") && incidencias.equals("f2")) {
            model.addAttribute("tipoTitulo", "porValidarFichoMal");
        } else if (estado.equals("1") && incidencias.equals("f3")) {
            model.addAttribute("tipoTitulo", "porValidarFicho");
        } else if (estado.equals("2") && incidencias.equals("f1")) {
            model.addAttribute("tipoTitulo", "ValidadoNoFicho");
        } else if (estado.equals("2") && incidencias.equals("f2")) {
            model.addAttribute("tipoTitulo", "ValidadoFichoMal");
        } else if (estado.equals("2") && incidencias.equals("f3")) {
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
        
        List<Empleado> lista_empleados = generaListaEmpleados();
        model.put("empleados", lista_empleados);
        
        return VISTA_FORM_JORNADAS_NUEVA;

    }

    @PostMapping("jornadas/guardar")
    public String guardar(@Validated Jornadas jornada, BindingResult result, Map<String, Object> model) {
        
        if (jornada.getHora_entrada() != null && jornada.getHora_salida() != null) {
            LocalTime horaEntrada = LocalTime.parse(jornada.getHora_entrada());
            LocalTime horaSalida = LocalTime.parse(jornada.getHora_salida());
            
            if (!horaEntrada.isBefore(horaSalida)) {
                FieldError error = new FieldError("jornada", "hora_entrada", "La hora de entrada debe ser menor que la hora de salida");
                result.addError(error);
            }
        }

        if (result.hasErrors()) {
            model.put("jornada", jornada);
            List<ObjectError> r = result.getAllErrors();
            model.put("result", r);
            List<Empleado> lista_empleados = generaListaEmpleados();
            model.put("empleados", lista_empleados);
            return VISTA_FORM_JORNADAS_NUEVA;  
        }
        try {
            restTemplate.postForObject(JORNADASMANAGER_STRING, jornada, Jornadas.class);
        } catch (Exception e) {
        }
        return "redirect:/jornadas/lista";
    }

    @GetMapping("jornadas/validar/{idEmpleado}/{fecha}")
    public String Validar(@PathVariable(value = "idEmpleado") String idEmpleado,
            @PathVariable(value = "fecha") String fecha) {
        Jornadas jornada = new Jornadas();
        try {
            jornada = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, Jornadas.class);
        } catch (Exception e) {
        }

        jornada.setEstado("2");
        restTemplate.put(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, jornada, Jornadas.class);

        return "redirect:/jornadas/lista";
    }

    @GetMapping("jornadas/revisar/{idEmpleado}/{fecha}")
    public String Revisar(@PathVariable(value = "idEmpleado") String idEmpleado,
            @PathVariable(value = "fecha") String fecha) {
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
    public String delete(@PathVariable(value = "idEmpleado") String idEmpleado,
            @PathVariable(value = "fecha") String fecha) {
        try {
            restTemplate.delete(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha);
        } catch (Exception e) {
        }
        return "redirect:/jornadas/lista";
    }

    @GetMapping("jornadas/editar/{idEmpleado}/{fecha}")
    public String editar(@PathVariable(value = "idEmpleado") String idEmpleado,
            @PathVariable(value = "fecha") String fecha, Map<String, Object> model) {
        Jornadas jornada = new Jornadas();
        try {
            jornada = restTemplate.getForObject(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, Jornadas.class);
        } catch (Exception e) {
        }

        model.put("jornada", jornada);
        return VISTA_FORM_JORNADAS_EDITAR;
    }

    @PostMapping("jornadas/actualizar")
    public String actualizar(@Validated Jornadas jornada, BindingResult result, Map<String, Object> model) {
        
        if (jornada.getHora_entrada() != null && jornada.getHora_salida() != null) {
            LocalTime horaEntrada = LocalTime.parse(jornada.getHora_entrada());
            LocalTime horaSalida = LocalTime.parse(jornada.getHora_salida());
            
            if (!horaEntrada.isBefore(horaSalida)) {
                FieldError error = new FieldError("jornada", "hora_entrada", "La hora de entrada debe ser menor que la hora de salida");
                result.addError(error);
            }
        }

        if (result.hasErrors()) {
            model.put("jornada", jornada);
            List<ObjectError> r = result.getAllErrors();
            model.put("result", r);
            
            return VISTA_FORM_JORNADAS_EDITAR;
        }
        try {
            restTemplate.put(JORNADASMANAGER_STRING + jornada.getIdEmpleado() + "/" + jornada.getFecha(), jornada, Jornadas.class);
        } catch (Exception e) {
        }
        return "redirect:/jornadas/lista";
    }

    @PostMapping("fichar/entrada")
    public String ficharEntrada(Principal principal, Model model) {
        String idEmpleado;

        try {
            Empleado empleadoActual = restTemplate.getForObject(DATOSMANAGER_STRING + principal.getName(),
                    Empleado.class);
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

            model.addAttribute("jor", jornada);

        } else if (jornada.getHora_entrada() == null) {
            jornada.setHora_entrada(horaActualString);
            jornada.setEstado("1");
            jornada.setIncidencia("f3");
            restTemplate.put(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, jornada, Jornadas.class);
            model.addAttribute("jor", jornada);

        } else {
            model.addAttribute("error", "Ya ha fichado la entrada, no puede volver a fichar su entrada hasta mañana");
            model.addAttribute("jor", jornada);

        }

        return "home";
    }

    @PostMapping("fichar/salida")
    public String ficharSalida(Principal principal, Model model) {

        String idEmpleado;

        try {
            Empleado empleadoActual = restTemplate.getForObject(DATOSMANAGER_STRING + principal.getName(),
                    Empleado.class);
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

        } else if (jornada.getHora_salida() == null
                && horaActual.isAfter(LocalTime.parse(jornada.getHora_entrada(), formatter))) {
            jornada.setHora_salida(horaActualString);
            jornada.setIncidencia("f3");
            jornada.setEstado("1");
            restTemplate.put(JORNADASMANAGER_STRING + idEmpleado + "/" + fecha, jornada, Jornadas.class);
            model.addAttribute("jor", jornada);

        } else {
            model.addAttribute("error", "Por favor fiche la entrada antes de fichar la salida.");
            model.addAttribute("jor", jornada);

        }

        return "home";
    }

    @GetMapping("/incidencias")
    public String listaIncidenciasEmpleado(Principal principal, Map<String, Object> model) {

        String idEmpleado;

        try {
            Empleado empleadoActual = restTemplate.getForObject(DATOSMANAGER_STRING + principal.getName(), Empleado.class);
            idEmpleado = empleadoActual.getIdEmpleado();
        } catch (Exception e) {
            return "401";
        }

        List <Jornadas> listaIncidenciasEmpleado = new ArrayList<Jornadas>();
        try{
            listaIncidenciasEmpleado = Arrays.asList(restTemplate.getForEntity(JORNADASINCIDENCIA_STRING + idEmpleado, Jornadas[].class).getBody());
        } catch (HttpClientErrorException.NotFound e) {
            listaIncidenciasEmpleado = new ArrayList<>();
        } catch (Exception e) {
        }
        
        Collections.sort(listaIncidenciasEmpleado, new Comparator<Jornadas>() {
            @Override
            public int compare(Jornadas j1, Jornadas j2) {
                return j2.getFecha().compareTo(j1.getFecha()); 
            }
        });

        Jornadas incidencia = new Jornadas();
        incidencia.setIdEmpleado(idEmpleado);

        model.put("incidenciaEmpleado", incidencia);
        model.put("listaincidencias", listaIncidenciasEmpleado);
        return "incidencias";
    }

    @PostMapping("/incidenciasj/guardar")
    public String nuevaIncidencia(@Validated Jornadas incidencia, BindingResult result, Map<String, Object> model) {
       
        if (result.hasErrors()) {
            model.put("incidenciaEmpleado", incidencia);
            List<ObjectError> r = result.getAllErrors();
            model.put("result", r);
            
            List <Jornadas> listaIncidenciasEmpleado = new ArrayList<Jornadas>();
            try{
                listaIncidenciasEmpleado = Arrays.asList(restTemplate.getForEntity(JORNADASINCIDENCIA_STRING + incidencia.getIdEmpleado(), Jornadas[].class).getBody());
            } catch (HttpClientErrorException.NotFound e) {
                listaIncidenciasEmpleado = new ArrayList<>();
            } catch (Exception e) {
            
            }

            model.put("listaincidencias", listaIncidenciasEmpleado);

            return "incidencias";  
        }

        Jornadas newIncidencia = new Jornadas();
        
        try {
            newIncidencia = restTemplate.getForObject(JORNADASMANAGER_STRING + incidencia.getIdEmpleado() + "/" + incidencia.getFecha(), Jornadas.class);
            newIncidencia.setIncidencia(incidencia.getIncidencia());
            newIncidencia.setEstado("1");
            newIncidencia.setNotas(incidencia.getNotas());
            restTemplate.put(JORNADASMANAGER_STRING + incidencia.getIdEmpleado() + "/" + incidencia.getFecha(), newIncidencia, Jornadas.class);

        }  catch (HttpClientErrorException.NotFound e) {
            newIncidencia.setIdEmpleado(incidencia.getIdEmpleado());
            newIncidencia.setFecha(incidencia.getFecha());
            newIncidencia.setIncidencia(incidencia.getIncidencia());
            newIncidencia.setEstado("1");
            newIncidencia.setNotas(incidencia.getNotas());
            restTemplate.postForObject(JORNADASMANAGER_STRING, newIncidencia, Jornadas.class);
        } catch (Exception e) {

        }

        return "redirect:/incidencias";
    }

    public List<Empleado> generaListaEmpleados() {
        List<Empleado> lista_empleados = new ArrayList<Empleado>();
        try {
            lista_empleados = Arrays.asList(restTemplate.getForEntity(RHMANAGER_STRING, Empleado[].class).getBody());
        } catch (Exception e) {
            // Puedes manejar la excepción aquí si es necesario
        }
        return lista_empleados;
    }

}
