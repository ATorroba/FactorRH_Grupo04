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

    @PostMapping("/remesas")
    ResponseEntity<Remesa> create(@RequestBody Remesa newRem) throws URISyntaxException {
        newRem.setEstado("1");
        Remesa result = remRepository.save(newRem);
        return ResponseEntity.created(new URI("/remesas/" + result.getIdRemesa())).body(result);
    }

    @GetMapping("/remesas/{id}")
    ResponseEntity<Remesa> read(@PathVariable Integer id) {
        return remRepository.findById(id).map(remesa -> ResponseEntity.ok().body(remesa))
                .orElse(new ResponseEntity<Remesa>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/remesas/{id}")
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

    @DeleteMapping("remesas/{id}")
    ResponseEntity<Remesa> delete(@PathVariable Integer id) {
        remRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("/remesas/{id}/calculada")
    ResponseEntity<Remesa> calculada(@PathVariable Integer id) {
        return remRepository.findById(id).map(remesa -> {
            remesa.setEstado("2");
            remRepository.save(remesa);
            return ResponseEntity.ok().body(remesa);
        }).orElse(new ResponseEntity<Remesa>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/remesas/{id}/emitida")
    ResponseEntity<Remesa> emitida(@PathVariable Integer id) {
        return remRepository.findById(id).map(remesa -> {
            if (remesa.getFecha_remesa() == null) {
                java.util.Date fechaActual = new java.util.Date();
                java.sql.Date fecha = new java.sql.Date(fechaActual.getTime());
                remesa.setFecha_remesa(fecha);
            }
            if ("2".equals(remesa.getEstado()))
                remesa.setEstado("3");
            remRepository.save(remesa);
            return ResponseEntity.ok().body(remesa);
        }).orElse(new ResponseEntity<Remesa>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/remesas/{id}/pagada")
    ResponseEntity<Remesa> pagada(@PathVariable Integer id) {
        return remRepository.findById(id).map(remesa -> {
            if ("3".equals(remesa.getEstado()))
                remesa.setEstado("4");
            if (remesa.getFecha_pago() == null) {
                java.util.Date fechaActual = new java.util.Date();
                java.sql.Date fecha = new java.sql.Date(fechaActual.getTime());
                remesa.setFecha_pago(fecha);
            }
            remRepository.save(remesa);
            return ResponseEntity.ok().body(remesa);
        }).orElse(new ResponseEntity<Remesa>(HttpStatus.NOT_FOUND));
    }

}
