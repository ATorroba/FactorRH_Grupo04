package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.upm.dit.isst.tfgapi.model.Jornadas;
import es.upm.dit.isst.tfgapi.repository.JornadasRepository;
import es.upm.dit.isst.tfgapi.model.jornadasPK;

@RestController
public class JornadasController {

    private final JornadasRepository jornadasRepository;
    
    public JornadasController(JornadasRepository t) {
        this.jornadasRepository = t;
    }

    @GetMapping("/jornadas")
    List<Jornadas> readAllJornadas() {
       Iterable<Jornadas> listaJornadas =  jornadasRepository.findAll();
       return (List<Jornadas>) listaJornadas;
    }
    
    @PostMapping("/jornadas")
    ResponseEntity<Jornadas> create(@RequestBody Jornadas newJornada) throws URISyntaxException {
        int minTrabajados = calculateMinutosTrabajados(newJornada);
        newJornada.setMinutos_trabajados(minTrabajados);
        Jornadas result = jornadasRepository.save(newJornada);
        return ResponseEntity.created(new URI("/jornadas/" + result.getIdEmpleado() + "/" + result.getFecha())).body(result);
    }

    @GetMapping("/jornadas/{idEmpleado}/{fecha}")
    ResponseEntity<Jornadas> getJornadaById(@PathVariable String idEmpleado, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        jornadasPK jornadaPk = new jornadasPK(idEmpleado, fecha);
        return jornadasRepository.findById(jornadaPk).map(jornada ->
        ResponseEntity.ok().body(jornada)
        ).orElse(new ResponseEntity<Jornadas>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/jornadas/{idEmpleado}/{fecha}")
    ResponseEntity<Jornadas> update(@RequestBody Jornadas newJornada, @PathVariable String idEmpleado, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        int minTrabajados = calculateMinutosTrabajados(newJornada);
        newJornada.setMinutos_trabajados(minTrabajados);
        
        jornadasPK jornadaPk = new jornadasPK(idEmpleado, fecha);
        return jornadasRepository.findById(jornadaPk).map(jornada -> {
            jornada.setIdEmpleado(newJornada.getIdEmpleado());
            jornada.setFecha(newJornada.getFecha());
            jornada.setHora_entrada(newJornada.getHora_entrada());
            jornada.setHora_salida(newJornada.getHora_salida());
            jornada.setEntrada_teorica(newJornada.getEntrada_teorica());
            jornada.setSalida_teorica(newJornada.getSalida_teorica());
            jornada.setMinutos_trabajados(newJornada.getMinutos_trabajados());
            jornada.setMinutos_teoricos(newJornada.getMinutos_teoricos());
            jornada.setIncidencia(newJornada.getIncidencia());
            jornada.setEstado(newJornada.getEstado());
            jornada.setSaldo(newJornada.getSaldo());
            jornada.setNotas(newJornada.getNotas());
            
            jornadasRepository.save(jornada);
            return ResponseEntity.ok().body(jornada);
        }).orElse(new ResponseEntity<Jornadas>(HttpStatus.NOT_FOUND));
    }
    
    @DeleteMapping("/jornadas/{idEmpleado}/{fecha}")
    ResponseEntity<Jornadas> delete(@PathVariable String idEmpleado, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        jornadasPK jornadaPk = new jornadasPK(idEmpleado, fecha);
        jornadasRepository.deleteById(jornadaPk);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/jornadas/empleado/{idEmpleado}")
    List<Jornadas> jornadasEmpleado(@PathVariable String idEmpleado) {
        return (List<Jornadas>) jornadasRepository.findByIdEmpleado(idEmpleado);
    }

    @GetMapping("/jornadas/fecha/{fecha}")
    List<Jornadas> jornadasFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return (List<Jornadas>) jornadasRepository.findByFecha(fecha);
    }

    @GetMapping("/jornadas/estado/{estado}")
    List<Jornadas> jornadasEstado(@PathVariable String estado) {
        return (List<Jornadas>) jornadasRepository.findByEstado(estado);
    }

    @GetMapping("/jornadas/estado/{estado}/{incidencia}")
    List<Jornadas> jornadasIncidencia(@PathVariable String estado, @PathVariable String incidencia) {
        return (List<Jornadas>) jornadasRepository.findByIncidenciaAndEstado(incidencia, estado);
    }

    @GetMapping("/jornadas/empleadoIncidencias/{idEmpleado}")
    List<Jornadas> IncidenciasEmpleado(@PathVariable String idEmpleado) {
        List<Jornadas> listaNoFichado = jornadasRepository.findByIdEmpleadoAndIncidenciaAndEstado(idEmpleado, "f1", "1");
        List<Jornadas> listaFichadoMal = jornadasRepository.findByIdEmpleadoAndIncidenciaAndEstado(idEmpleado, "f2", "1");

        listaNoFichado.addAll(listaFichadoMal);

        return listaNoFichado;
    }

    private int calculateMinutosTrabajados(Jornadas jornada) {
        if (jornada.getHora_salida() == null) {
            return 0;
        }
    
        int horaEntr = jornada.getHora_entrada().getHour();
        int minEntr = jornada.getHora_entrada().getMinute();
        int minutosEnt = horaEntr * 60 + minEntr;
        int horaSal = jornada.getHora_salida().getHour();
        int minSal = jornada.getHora_salida().getMinute();
        int minutosSal = horaSal * 60 + minSal;
        int minTrabajados = minutosSal - minutosEnt;
    
        return Math.max(minTrabajados, 0);
    }
}
