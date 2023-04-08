package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
// import java.util.List;

// import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.tfgapi.model.Turnos;
//import es.upm.dit.isst.tfgapi.model.combinacionPKs;
import es.upm.dit.isst.tfgapi.repository.RepositoryTurnos;
import org.springframework.ui.Model;

@RestController

public class ControllerTurnos {

    private final RepositoryTurnos turnosRepository;
    //public static final Logger log = LoggerFactory.getLogger(TFGController.class);
    public ControllerTurnos(RepositoryTurnos t) {
        this.turnosRepository = t;

    }

    @GetMapping("/turnos")
    public String obtenerListaTurnos(Model model) {
        Iterable<Turnos> listaTurnos = turnosRepository.findAll();
        model.addAttribute("turnos", listaTurnos);
        return "{listaTurnos.idEmpleado}";
    }

    // @GetMapping("/turnos")

    // List<Turnos> readAll() {
    //     return (List<Turnos>) turnosRepository.findAll();
    // }

    @PostMapping("/turnos")

    ResponseEntity<Turnos> create(@RequestBody Turnos newTurno) throws URISyntaxException {
        Turnos result = turnosRepository.save(newTurno);
        return ResponseEntity.created(new URI("/turnos/" + result.getidDia())).body(result);
    }

    @GetMapping("/turnos/{idEmpleado}")

    ResponseEntity<Turnos> read(@PathVariable String idEmpleado) {

        return turnosRepository.findById(idEmpleado).map(turno ->
        ResponseEntity.ok().body(turno)
        ).orElse(new ResponseEntity<Turnos>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/turnos/{idEmpleado}/{idDia}")

    ResponseEntity<Turnos> read(@PathVariable String idEmpleado, @PathVariable Integer idDia) {

        return turnosRepository.findById(idEmpleado).map(turno ->
        ResponseEntity.ok().body(turno)
        ).orElse(new ResponseEntity<Turnos>(HttpStatus.NOT_FOUND));

    }

    

    // @DeleteMapping("tfgs/{id}")
    // ResponseEntity<Empleado> delete(@PathVariable String id) {
    //     turnosRepository.deleteById(id);
    //     return ResponseEntity.ok().body(null);
    // }

    // @GetMapping("/tfgs/profesor/{id}")
    // List<Empleado> readdireccion(@PathVariable String id) {
    //     return (List<Empleado>) turnosRepository.findBy(id);
    // }

    // @PostMapping("/tfgs/{id}/incrementa")
    // ResponseEntity<Empleado> incrementa(@PathVariable String id) {
    //     return turnosRepository.findById(id).map(empleado -> {
    //         empleado.setStatus(empleado.getStatus() + 1);
    //         turnosRepository.save(empleado);
    //         return ResponseEntity.ok().body(empleado);
    //     }).orElse(new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND));
    // }
}