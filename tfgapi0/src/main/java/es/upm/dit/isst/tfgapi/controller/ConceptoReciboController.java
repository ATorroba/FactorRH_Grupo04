package es.upm.dit.isst.tfgapi.controller;

import es.upm.dit.isst.tfgapi.repository.ConceptoReciboRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import es.upm.dit.isst.tfgapi.model.ConceptoRecibo;

@RestController
public class ConceptoReciboController {
    private final ConceptoReciboRepository conRepository;
    public static final Logger log = LoggerFactory.getLogger(ConceptoReciboRepository.class);
    
    public ConceptoReciboController(ConceptoReciboRepository con) {
        this.conRepository = con;
    }

    @GetMapping("/conceptosrec")
    List<ConceptoRecibo> readAll() {
        return (List<ConceptoRecibo>) conRepository.findAll();
    }

    @PostMapping("/conceptosrec")
    ResponseEntity<ConceptoRecibo> create(@RequestBody ConceptoRecibo newCon) throws URISyntaxException {
        ConceptoRecibo result = conRepository.save(newCon);
        return ResponseEntity.created(new URI("/conceptosrec/" + result.getIdConcepto())).body(result);
    }

    @GetMapping("/conceptosrec/{id}")
    ResponseEntity<ConceptoRecibo> read(@PathVariable Long id) {
        return conRepository.findById(id).map(conceptoRecibo -> ResponseEntity.ok().body(conceptoRecibo))
                .orElse(new ResponseEntity<ConceptoRecibo>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/conceptosrec/{id}")
    ResponseEntity<ConceptoRecibo> update(@RequestBody ConceptoRecibo newCon, @PathVariable Long id) {
        return conRepository.findById(id).map(conceptoRecibo -> {
            conceptoRecibo.setIdRecibo(newCon.getIdRecibo());
            conceptoRecibo.setIdConcepto(newCon.getIdConcepto());
            conceptoRecibo.setPrecio(newCon.getPrecio());
            conceptoRecibo.setUnidades(newCon.getUnidades());
            conceptoRecibo.setDevengo(newCon.getDevengo());
            conceptoRecibo.setDeduccion(newCon.getDeduccion());
            
            conRepository.save(conceptoRecibo);
            return ResponseEntity.ok().body(conceptoRecibo);
        }).orElse(new ResponseEntity<ConceptoRecibo>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("conceptosrec/{id}")
    ResponseEntity<ConceptoRecibo> delete(@PathVariable long id) {
        conRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }


}

