package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

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

import es.upm.dit.isst.tfgapi.model.Permisos;
//import es.upm.dit.isst.tfgapi.model.combinacionPKs;
import es.upm.dit.isst.tfgapi.repository.RepositoryPermisos;


@RestController

public class ControllerPermisos {

    private final RepositoryPermisos permisosRepository;
    //public static final Logger log = LoggerFactory.getLogger(TFGController.class);
    public ControllerPermisos(RepositoryPermisos p) {
        this.permisosRepository = p;

    }

    @GetMapping("/permisos")
    List<Permisos> readAll() {
        return (List<Permisos>) permisosRepository.findAll();
    }

    @GetMapping("/permisos/{idEmpleado}")

    List<Permisos> readByIdEmpleado(@PathVariable String idEmpleado) {
        return (List<Permisos>) permisosRepository.findByidEmpleado(idEmpleado);
    }

    

    @PostMapping("/permisos")

    ResponseEntity<Permisos> create(@RequestBody Permisos newPermiso) throws URISyntaxException {
        Permisos result = permisosRepository.save(newPermiso);
        return ResponseEntity.created(new URI("/permisos" + result.getIdEmpleado())).body(result);
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
