package es.upm.dit.isst.tfgapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
}
