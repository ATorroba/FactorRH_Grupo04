package es.upm.dit.isst.tfgapi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import es.upm.dit.isst.tfgapi.model.Puesto;
import es.upm.dit.isst.tfgapi.repository.PuestoRepository;

@RestController
public class PuestoController {

    private final PuestoRepository puestoRepository;
    public static final Logger log = LoggerFactory.getLogger(PuestoController.class);
    public PuestoController(PuestoRepository t) {
        this.puestoRepository = t;
    }

    @GetMapping("/puestos")
    List<Puesto> readAll() {
        return (List<Puesto>) puestoRepository.findAll();
    }
    
    @PostMapping("/puestos")
    ResponseEntity<Puesto> create(@RequestBody Puesto newPuesto) throws URISyntaxException {
        Puesto result = puestoRepository.save(newPuesto);
        return ResponseEntity.created(new URI("/puesto/" + result.getId_puesto())).body(result);
    }

    @GetMapping("/puestos/{id}")
    ResponseEntity<Puesto> read(@PathVariable String id) {
        return puestoRepository.findById(id).map(puesto ->
        ResponseEntity.ok().body(puesto)
        ).orElse(new ResponseEntity<Puesto>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/puestos/{id}")
    ResponseEntity<Puesto> update(@RequestBody Puesto newPuesto, @PathVariable String id) {
        return puestoRepository.findById(id).map(puesto -> {
            puesto.setNombre_puesto(newPuesto.getNombre_puesto());
            puesto.setDesc_puesto(newPuesto.getDesc_puesto());
            puesto.setSueldo_orientativo(newPuesto.getSueldo_orientativo());
            puesto.setReq_exp_form(newPuesto.getReq_exp_form());
            puesto.setReq_idiomas(newPuesto.getReq_idiomas());
            puesto.setReq_disponibilidad(newPuesto.getReq_disponibilidad());
            puesto.setReq_otros(newPuesto.getReq_otros());
            puesto.setDepto(newPuesto.getDepto());
            puestoRepository.save(puesto);
            return ResponseEntity.ok().body(puesto);
        }).orElse(new ResponseEntity<Puesto>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("puestos/{id}")
    ResponseEntity<Puesto> delete(@PathVariable String id) {
        puestoRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }
       
    @GetMapping("/puestos/idDepto/{id}")
    List<Puesto> readPorPuesto(@PathVariable String id) {
        return (List<Puesto>) puestoRepository.findByDepto(id);
    }

}
