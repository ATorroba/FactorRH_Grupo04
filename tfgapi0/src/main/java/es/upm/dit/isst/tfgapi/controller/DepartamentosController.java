package es.upm.dit.isst.tfgapi.controller;

import es.upm.dit.isst.tfgapi.repository.DepartamentosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import es.upm.dit.isst.tfgapi.model.Departamento;

@RestController
public class DepartamentosController {

    private final DepartamentosRepository depRepository;
    public static final Logger log = LoggerFactory.getLogger(DepartamentosController.class);


    public DepartamentosController(DepartamentosRepository dep) {
        this.depRepository = dep;
    }

    @GetMapping("/departamentos")
    List<Departamento> readAll() {
        return (List<Departamento>) depRepository.findAll();
    }

    @PostMapping("/departamentos")
    ResponseEntity<Departamento> create(@RequestBody Departamento newDep) throws URISyntaxException {
        Departamento result = depRepository.save(newDep);
        return ResponseEntity.created(new URI("/departamentos/" + result.getId_Depto())).body(result);
    }

    @GetMapping("/departamentos/{id}")
    ResponseEntity<Departamento> read(@PathVariable String id) {
        return depRepository.findById(id).map(departamento -> ResponseEntity.ok().body(departamento))
                .orElse(new ResponseEntity<Departamento>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/departamentos/{id}")
    ResponseEntity<Departamento> update(@RequestBody Departamento newDep, @PathVariable String id) {
        return depRepository.findById(id).map( departamento-> {
            departamento.setNombre(newDep.getNombre());
            departamento.setOficina(newDep.getOficina());
            departamento.setPadre(newDep.getPadre());
            return ResponseEntity.ok().body(departamento);
        }).orElse(new ResponseEntity<Departamento>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("departamentos/{id}")
    ResponseEntity<Departamento> delete(@PathVariable String id) {
        depRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/departamentos/nombre/{id}")
    List<Departamento> readNombre(@PathVariable String id) {
        return (List<Departamento>) depRepository.findByNombre(id);
    }

    @GetMapping("/departamentos/padre/{id}")
    List<Departamento> readDependienteDe(@PathVariable String id) {
        return (List<Departamento>) depRepository.findByPadre(id);
    }

    @GetMapping("/departamentos/raiz")
    List<Departamento> readDeptoRaiz() {
        return (List<Departamento>) depRepository.buscarDepartamentoRaiz();
    }


}