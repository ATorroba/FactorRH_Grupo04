package es.upm.dit.isst.tfgapi.controller;

import es.upm.dit.isst.tfgapi.repository.RecibosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import es.upm.dit.isst.tfgapi.model.Recibo;

@RestController
public class RecibosController {
    private final RecibosRepository recRepository;
    public static final Logger log = LoggerFactory.getLogger(RecibosRepository.class);

    public RecibosController(RecibosRepository rec) {
            this.recRepository = rec;
        }

    @GetMapping("/recibos")
    List<Recibo> readAll() {
        return (List<Recibo>) recRepository.findAll();
    }

    @PostMapping("/recibo")
    ResponseEntity<Recibo> create(@RequestBody Recibo newRec) throws URISyntaxException {
        Recibo result = recRepository.save(newRec);
        return ResponseEntity.created(new URI("/recibos/" + result.getIdRecibo())).body(result);
    }

    @GetMapping("/recibo/{id}")
    ResponseEntity<Recibo> read(@PathVariable Integer id) {
        return recRepository.findById(id).map(recibo -> ResponseEntity.ok().body(recibo))
                .orElse(new ResponseEntity<Recibo>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/recibo/{id}")
    ResponseEntity<Recibo> update(@RequestBody Recibo newRec, @PathVariable Integer id) {
        return recRepository.findById(id).map(recibo -> {
            recibo.setIdRemesa(newRec.getIdRemesa());
            recibo.setIdEmpleado(newRec.getIdEmpleado());
            recibo.setFecha_pago(newRec.getFecha_pago());
            recibo.setIBAN(newRec.getIBAN());
            recibo.setSWIFT(newRec.getSWIFT());
            recRepository.save(recibo);
            return ResponseEntity.ok().body(recibo);
        }).orElse(new ResponseEntity<Recibo>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("recibo/{id}")
    ResponseEntity<Recibo> delete(@PathVariable Integer id) {
        recRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

}
