package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.tfgapi.model.Empleado;
import es.upm.dit.isst.tfgapi.repository.empleadoRepository;

@RestController

public class EmpleadoController {

    private final empleadoRepository empleadoRepository;

    // public static final Logger log =
    // LoggerFactory.getLogger(TFGController.class);
    public EmpleadoController(empleadoRepository t) {
        this.empleadoRepository = t;

    }

    @GetMapping("/empleados")

    List<Empleado> readAll() {
        return (List<Empleado>) empleadoRepository.findAll();
    }

    @PostMapping("/empleados")

    ResponseEntity<Empleado> create(@RequestBody Empleado newTFG) throws URISyntaxException {
        Empleado result = empleadoRepository.save(newTFG);
        return ResponseEntity.created(new URI("/empleados/" + result.getEmail())).body(result);
    }

    @GetMapping("/empleados/{id}")

    ResponseEntity<Empleado> read(@PathVariable String id) {

        return empleadoRepository.findById(id).map(empleado -> ResponseEntity.ok().body(empleado))
                .orElse(new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND));

    }

    @PutMapping("/empleados/{id}")

    ResponseEntity<Empleado> update(@RequestBody Empleado newEmpleado, @PathVariable String id) {
        return empleadoRepository.findById(id).map(empleado -> {
            empleado.setEmail(newEmpleado.getEmail());
            empleado.setPassword(newEmpleado.getPassword());

            empleadoRepository.save(empleado);
            return ResponseEntity.ok().body(empleado);
        }).orElse(new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("empleados/{id}")
    ResponseEntity<Empleado> delete(@PathVariable String id) {
        empleadoRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    /*
     * @GetMapping("/tfgs/profesor/{id}")
     * List<TFG> readTutor(@PathVariable String id) {
     * return (List<TFG>) tfgRepository.findByTutor(id);
     * }
     */
    /*
     * @PostMapping("/tfgs/{id}/incrementa")
     * 
     * ResponseEntity<TFG> incrementa(@PathVariable String id) {
     * return tfgRepository.findById(id).map(tfg -> {
     * tfg.setStatus(tfg.getStatus() + 1);
     * tfgRepository.save(tfg);
     * return ResponseEntity.ok().body(tfg);
     * }).orElse(new ResponseEntity<TFG>(HttpStatus.NOT_FOUND));
     * }
     */
}