package es.upm.dit.isst.tfgapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import es.upm.dit.isst.tfgapi.model.Turnos;
import es.upm.dit.isst.tfgapi.repository.RepositoryTurnos;

@RestController
public class ControllerTurnos {

    private final RepositoryTurnos turnosRepository;
    
    public ControllerTurnos(RepositoryTurnos t) {
        this.turnosRepository = t;

    }

    @GetMapping("/turnos")
    List<Turnos> readAll() {
        return (List<Turnos>) turnosRepository.findAll();
    }

    @GetMapping("/turnos/{idEmpleado}")
    List<Turnos> readByIdEmpleado(@PathVariable String idEmpleado) {
        return (List<Turnos>) turnosRepository.findByidEmpleado(idEmpleado);
    }

    @GetMapping("/turnos/{idEmpleado}/{idDia}")
    ResponseEntity<Turnos> readByIdEmpleadoYDia(@PathVariable String idEmpleado, @PathVariable Integer idDia) {
        Turnos turno = turnosRepository.findByIdEmpleadoAndIdDia(idEmpleado, idDia);
    
        if (turno == null) {
            return new ResponseEntity<Turnos>(HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok().body(turno);
        }
    }

    @PutMapping("/turnos/{id}")
    ResponseEntity<Turnos> update(@RequestBody Turnos newTurno, @PathVariable Integer id) {
        return turnosRepository.findById(id).map(turno -> {
            turno.setIdTurno(newTurno.getIdTurno());
            turno.setIdEmpleado(newTurno.getIdEmpleado());
            turno.setIdDia(newTurno.getIdDia());
            turno.setHora_entrada(newTurno.getHora_entrada());
            turno.setHora_salida(newTurno.getHora_salida());
            turno.setMinutos_jornada(newTurno.getMinutos_jornada());

            turnosRepository.save(turno);
            return ResponseEntity.ok().body(turno);
        }).orElse(new ResponseEntity<Turnos>(HttpStatus.NOT_FOUND));
    }
}
