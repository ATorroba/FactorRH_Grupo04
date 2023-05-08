package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

// import org.hibernate.annotations.common.util.impl.LoggerFactory;
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

import es.upm.dit.isst.tfgapi.model.Permisos;
//import es.upm.dit.isst.tfgapi.model.combinacionPKs;
import es.upm.dit.isst.tfgapi.repository.RepositoryPermisos;
import es.upm.dit.isst.tfgapi.model.permisosPK;


@RestController
public class ControllerPermisos {

    private final RepositoryPermisos permisosRepository;
    public ControllerPermisos(RepositoryPermisos p) {
        this.permisosRepository = p;
    }

    @GetMapping("/permisos")
    List<Permisos> readAllPermisos() {
        Iterable<Permisos> listaPermisos =  permisosRepository.findAll();
        return (List<Permisos>) listaPermisos;
     }

    @GetMapping("/permisos/{idEmpleado}/{ejercicio}")
    ResponseEntity<Permisos> getPermisosById(@PathVariable String idEmpleado, @PathVariable Integer ejercicio) {
        permisosPK permisoPk = new permisosPK(idEmpleado, ejercicio);
        return permisosRepository.findById(permisoPk).map(permiso ->
        ResponseEntity.ok().body(permiso)
        ).orElse(new ResponseEntity<Permisos>(HttpStatus.NOT_FOUND));
    }
    

    @PostMapping("/permisos")
    ResponseEntity<Permisos> create(@RequestBody Permisos newPermiso) throws URISyntaxException {
        Permisos result = permisosRepository.save(newPermiso);
        return ResponseEntity.created(new URI("/permisos" + result.getIdEmpleado() + "/" + result.getEjercicio())).body(result);
    }

    @PutMapping("/permisos/{idEmpleado}/{ejercicio}")
    ResponseEntity<Permisos> update(@RequestBody Permisos newPermiso, @PathVariable String idEmpleado ,@PathVariable Integer ejercicio) {
        permisosPK permisoPk = new permisosPK(idEmpleado, ejercicio);
        return permisosRepository.findById(permisoPk).map(permiso -> {
            permiso.setIdEmpleado(newPermiso.getIdEmpleado());
            permiso.setEjercicio(newPermiso.getEjercicio());
            permiso.setDiasGracia(newPermiso.getDiasGracia());
            permiso.setVacaciones(newPermiso.getVacaciones());
            permisosRepository.save(permiso);
            return ResponseEntity.ok().body(permiso);
        }).orElse(new ResponseEntity<Permisos>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/permisos/{idEmpleado}/{ejercicio}")
    ResponseEntity<Permisos> delete(@PathVariable String idEmpleado, @PathVariable Integer ejercicio) {
        permisosPK permisoPk = new permisosPK(idEmpleado, ejercicio);
        permisosRepository.deleteById(permisoPk);
        return ResponseEntity.ok().body(null);
    }
}
