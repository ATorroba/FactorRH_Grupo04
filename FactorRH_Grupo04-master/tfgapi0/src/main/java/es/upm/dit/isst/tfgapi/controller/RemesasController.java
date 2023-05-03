package es.upm.dit.isst.tfgapi.controller;

import es.upm.dit.isst.tfgapi.repository.RemesasRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import es.upm.dit.isst.tfgapi.model.Remesa;

@RestController
public class RemesasController {
    private final RemesasRepository remRepository;
    public static final Logger log = LoggerFactory.getLogger(RemesasRepository.class);
    
    public RemesasController(RemesasRepository rem) {
        this.remRepository = rem;
    }

    @GetMapping("/remesas")
    List<Remesa> readAll() {
        return (List<Remesa>) remRepository.findAll();
    }

    @PostMapping("/remesa")
    ResponseEntity<Remesa> create(@RequestBody Remesa newRem) throws URISyntaxException {
        Remesa result = remRepository.save(newRem);
        return ResponseEntity.created(new URI("/remesas/" + result.getIdRemesa())).body(result);
    }

    @GetMapping("/remesa/{id}")
    ResponseEntity<Remesa> read(@PathVariable Integer id) {
        return remRepository.findById(id).map(remesa -> ResponseEntity.ok().body(remesa))
                .orElse(new ResponseEntity<Remesa>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/remesa/{id}")
    ResponseEntity<Remesa> update(@RequestBody Remesa newRemesa, @PathVariable Integer id) {
        return remRepository.findById(id).map(remesa -> {
            remesa.setEjercicio(newRemesa.getEjercicio());
            remesa.setMes(newRemesa.getMes());
            remesa.setTipo_nomina(newRemesa.getTipo_nomina());
            remesa.setEstado(newRemesa.getEstado());
            remesa.setFecha_pago(newRemesa.getFecha_pago());
            remesa.setFecha_remesa(newRemesa.getFecha_remesa());
            remRepository.save(remesa);
            return ResponseEntity.ok().body(remesa);
        }).orElse(new ResponseEntity<Remesa>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("remesa/{id}")
    ResponseEntity<Remesa> delete(@PathVariable Integer id) {
        remRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }


}
