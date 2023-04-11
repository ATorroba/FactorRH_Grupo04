package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
        return ResponseEntity.created(new URI("/empleados/" + result.getIdEmpleado())).body(result);
    }

    @GetMapping("/empleados/{idEmpleado}")

    ResponseEntity<Empleado> read(@PathVariable String idEmpleado) {

        return empleadoRepository.findById(idEmpleado).map(empleado -> ResponseEntity.ok().body(empleado))
                .orElse(new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND));

    }

    @GetMapping("/datos/{mail}")

    Empleado readEmail(@PathVariable String mail) {

        return (Empleado) empleadoRepository.findByEmail(mail);

    }

    @PutMapping("/empleados/{idEmpleado}")

    ResponseEntity<Empleado> update(@Validated @RequestBody Empleado newEmpleado, @PathVariable String idEmpleado) {
        return empleadoRepository.findById(idEmpleado).map(empleado -> {
            empleado.setIdEmpleado(newEmpleado.getIdEmpleado());
            empleado.setPassword(newEmpleado.getPassword());
            empleado.setNombre(newEmpleado.getNombre());
            empleado.setApellido_1(newEmpleado.getApellido_1());
            empleado.setApellido_2(newEmpleado.getApellido_2());
            empleado.setEmail(newEmpleado.getEmail());
            empleado.setFecha_alta(newEmpleado.getFecha_alta());
            empleado.setFecha_baja(newEmpleado.getFecha_baja());
            empleado.setTelefono(newEmpleado.getTelefono());
            empleado.setAntiguedad(newEmpleado.getAntiguedad());
            empleado.setSueldo_base(newEmpleado.getSueldo_base());
            empleado.setNIF(newEmpleado.getNIF());
            empleado.setNASS(newEmpleado.getNASS());
            empleado.setSWIFT(newEmpleado.getSWIFT());
            empleado.setCP(newEmpleado.getCP());
            empleado.setDireccion(newEmpleado.getDireccion());
            empleado.setFecha_baja(newEmpleado.getFecha_baja());
            empleado.setEmail_particular(newEmpleado.getEmail_particular());

            empleadoRepository.save(empleado);
            return ResponseEntity.ok().body(empleado);
        }).orElse(new ResponseEntity<Empleado>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("empleados/{idEmpleado}")
    ResponseEntity<Empleado> delete(@PathVariable String idEmpleado) {
        empleadoRepository.deleteById(idEmpleado);
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