package es.upm.dit.isst.tfgapi.controller;

import es.upm.dit.isst.tfgapi.repository.ConceptoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import es.upm.dit.isst.tfgapi.model.Concepto;

@RestController
public class ConceptoController {
    private final ConceptoRepository conRepository;
    public static final Logger log = LoggerFactory.getLogger(ConceptoRepository.class);
    
    public ConceptoController(ConceptoRepository con) {
        this.conRepository = con;
    }

    @GetMapping("/conceptos")
    List<Concepto> readAll() {
        return (List<Concepto>) conRepository.findAll();
    }

    @PostMapping("/concepto")
    ResponseEntity<Concepto> create(@RequestBody Concepto newCon) throws URISyntaxException {
        Concepto result = conRepository.save(newCon);
        return ResponseEntity.created(new URI("/conceptos/" + result.getIdConcepto())).body(result);
    }

    @GetMapping("/concepto/{id}")
    ResponseEntity<Concepto> read(@PathVariable Integer id) {
        return conRepository.findById(id).map(concepto -> ResponseEntity.ok().body(concepto))
                .orElse(new ResponseEntity<Concepto>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/concepto/{id}")
    ResponseEntity<Concepto> update(@RequestBody Concepto newCon, @PathVariable Integer id) {
        return conRepository.findById(id).map(concepto -> {
            concepto.setDesc_concepto(newCon.getDesc_concepto());
            concepto.setTipo(newCon.getTipo());
            concepto.setPrecio(newCon.getPrecio());
            
            conRepository.save(concepto);
            return ResponseEntity.ok().body(concepto);
        }).orElse(new ResponseEntity<Concepto>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("concepto/{id}")
    ResponseEntity<Concepto> delete(@PathVariable Integer id) {
        conRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }


}

