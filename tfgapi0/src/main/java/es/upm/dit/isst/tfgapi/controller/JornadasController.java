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
    List<Jornadas> readAll() {
        return (List<Jornadas>) jornadasRepository.findAll();
    }
    
    @PostMapping("/jornadas")
    ResponseEntity<Jornadas> create(@RequestBody Jornadas newJornada) throws URISyntaxException {
        Jornadas result = jornadasRepository.save(newJornada);
        return ResponseEntity.created(new URI("/jornada/" + result.getClave().getIdEmpleado() + "/" + result.getClave().getFecha())).body(result);
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
        jornadasPK jornadaPk = new jornadasPK(idEmpleado, fecha);
        return jornadasRepository.findById(jornadaPk).map(jornada -> {
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
        return (List<Jornadas>) jornadasRepository.findByClave_IdEmpleado(idEmpleado);
    }

    @GetMapping("/jornadas/fecha/{fecha}")
    List<Jornadas> jornadasFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return (List<Jornadas>) jornadasRepository.findByClave_Fecha(fecha);
    }
}
