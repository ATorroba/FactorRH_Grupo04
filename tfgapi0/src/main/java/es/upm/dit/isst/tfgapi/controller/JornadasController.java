package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.tfgapi.model.Jornadas;
import es.upm.dit.isst.tfgapi.repository.JornadasRepository;

@RestController
public class JornadasController {

    private final JornadasRepository JornadasRepository;
    public JornadasController(JornadasRepository t) {
        this.JornadasRepository = t;
    }

    @GetMapping("/Jornadas")
    List<Jornadas> readAll() {
        return (List<Jornadas>) JornadasRepository.findAll();
    }
    
    @PostMapping("/Jornadas")
    ResponseEntity<Jornadas> create(@RequestBody Jornadas newJornada) throws URISyntaxException {
        Jornadas result = JornadasRepository.save(newJornada);
        return ResponseEntity.created(new URI("/Jornada/" + result.getIdEmpleado())).body(result);
    }

    @GetMapping("/Jornadas/{id}")
    ResponseEntity<Jornadas> read(@PathVariable String id) {
        return JornadasRepository.findById(id).map(Jornada ->
        ResponseEntity.ok().body(Jornada)
        ).orElse(new ResponseEntity<Jornadas>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/Jornadas/{id}")
    ResponseEntity<Jornadas> update(@RequestBody Jornadas newJornada, @PathVariable String id) {
        return JornadasRepository.findById(id).map(Jornada -> {
            Jornada.setFecha(newJornada.getFecha());
            Jornada.setHora_entrada(newJornada.getHora_entrada());
            Jornada.setHora_salida(newJornada.getHora_salida());
            Jornada.setEntrada_teorica(newJornada.getEntrada_teorica());
            Jornada.setSalida_teorica(newJornada.getSalida_teorica());
            Jornada.setMinutos_trabajados(newJornada.getMinutos_trabajados());
            Jornada.setMinutos_teoricos(newJornada.getMinutos_teoricos());
            Jornada.setSaldo(newJornada.getSaldo());
            Jornada.setIncidencia(newJornada.getIncidencia());
            Jornada.setEstado(newJornada.getEstado());
            Jornada.setNotas(newJornada.getNotas());
            JornadasRepository.save(Jornada);
            return ResponseEntity.ok().body(Jornada);
        }).orElse(new ResponseEntity<Jornadas>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("Jornadas/{id}")
    ResponseEntity<Jornadas> delete(@PathVariable String id) {
        JornadasRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }
}
