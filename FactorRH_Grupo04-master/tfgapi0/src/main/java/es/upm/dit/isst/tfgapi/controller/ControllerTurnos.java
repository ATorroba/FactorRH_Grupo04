package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import es.upm.dit.isst.tfgapi.model.Turnos;
import es.upm.dit.isst.tfgapi.model.turnoPK;
import es.upm.dit.isst.tfgapi.repository.RepositoryTurnos;
import org.springframework.web.bind.annotation.*;

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

    // @GetMapping("turnos/{idEmpleado}/{idDia}")
    // ResponseEntity<Turnos> getTurnoById(@PathVariable String idEmpleado, @PathVariable int idDia) {
    //     turnoPK turnopk = new turnoPK(idEmpleado, idDia);
    //     return turnosRepository.findById(turnopk).map(turno -> ResponseEntity.ok().body(turno))
    //             .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    // }

    }
