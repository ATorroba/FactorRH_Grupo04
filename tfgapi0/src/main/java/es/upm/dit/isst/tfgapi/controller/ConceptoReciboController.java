package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.tfgapi.model.ConceptoRecibo;
import es.upm.dit.isst.tfgapi.model.Recibo;
import es.upm.dit.isst.tfgapi.repository.ConceptoReciboRepository;
import es.upm.dit.isst.tfgapi.repository.RecibosRepository;

@RestController
public class ConceptoReciboController {
    @Autowired
    private final ConceptoReciboRepository conRepository;

    @Autowired
    private RecibosRepository recRepository;

    public static final Logger log = LoggerFactory.getLogger(ConceptoReciboRepository.class);
    
    public ConceptoReciboController(ConceptoReciboRepository con) {
        this.conRepository = con;
    }

    @GetMapping("/conceptosrec")
    List<ConceptoRecibo> readAll() {
        return (List<ConceptoRecibo>) conRepository.findAll();
    }

    @GetMapping("/conceptosrec/recibo/{id}")
    List<ConceptoRecibo> read_recibo(@PathVariable Integer id) {
        Optional<Recibo> opcionalRecibo = recRepository.findById(id);
        Recibo recibo = opcionalRecibo
                .orElseThrow(() -> new NoSuchElementException("No se encontró el recibo con el ID proporcionado."));
        return (List<ConceptoRecibo>) conRepository.findByIdRecibo(recibo);
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

