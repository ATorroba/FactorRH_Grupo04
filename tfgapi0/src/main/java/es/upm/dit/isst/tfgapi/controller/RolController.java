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

import es.upm.dit.isst.tfgapi.model.Rol;
import es.upm.dit.isst.tfgapi.model.RolPK;
import es.upm.dit.isst.tfgapi.model.RolVista;
import es.upm.dit.isst.tfgapi.repository.RolRepository;

@RestController
public class RolController {

    private final RolRepository rolRepository;

    public RolController(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @GetMapping("/roles")
    List<Rol> readAll() {
        return (List<Rol>) rolRepository.findAll();
    }

    @GetMapping("/rolesvista")
    List<Object> join() {
        return (List<Object>) rolRepository.join();
    }

    @GetMapping("/roles/{idrol}")
    List<Rol> readAllByIdRol(@PathVariable String idrol) {
        return rolRepository.findByClave_Idrol(idrol);
    }

    @PostMapping("/roles")
    ResponseEntity<Rol> create(@RequestBody Rol newRol) throws URISyntaxException {
        Rol result = rolRepository.save(newRol);
        return ResponseEntity
                .created(new URI(
                        "/departamentos/" + result.getClave().getIdrol() + "/" + result.getClave().getIdempleado()))
                .body(result);
    }

    @GetMapping("roles/{idrol}/{idempleado}")
    ResponseEntity<Rol> getRolById(@PathVariable String idrol, @PathVariable String idempleado) {
        RolPK rolpk = new RolPK(idrol, idempleado);
        return rolRepository.findById(rolpk).map(rol -> ResponseEntity.ok().body(rol))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("roles/{idrol}/{idempleado}")
    ResponseEntity<Rol> update(@RequestBody Rol newRol, @PathVariable String idrol, @PathVariable String idempleado) {
        RolPK rolpk = new RolPK(idrol, idempleado);
        return rolRepository.findById(rolpk).map(rol -> {
            rol.setActivo(newRol.getActivo());
            Rol updated = rolRepository.save(rol);
            return ResponseEntity.ok().body(updated);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("roles/{idrol}/{idempleado}")
    ResponseEntity<Void> deleteRol(@PathVariable String idrol, @PathVariable String idempleado) {
        RolPK rolpk = new RolPK(idrol, idempleado);
        rolRepository.deleteById(rolpk);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/roles/empleado/{idEmpleado}")
    List<Rol> rolesempleado(@PathVariable String idEmpleado) {
        return rolRepository.buscarRoles(idEmpleado);
    }

}
