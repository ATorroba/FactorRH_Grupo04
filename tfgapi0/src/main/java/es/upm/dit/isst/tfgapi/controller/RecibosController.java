package es.upm.dit.isst.tfgapi.controller;

import es.upm.dit.isst.tfgapi.repository.RecibosRepository;
import es.upm.dit.isst.tfgapi.repository.RemesasRepository;
import es.upm.dit.isst.tfgapi.repository.empleadoRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import es.upm.dit.isst.tfgapi.model.Empleado;
import es.upm.dit.isst.tfgapi.model.Recibo;
import es.upm.dit.isst.tfgapi.model.Remesa;

@RestController
public class RecibosController {
    @Autowired
    private final RecibosRepository recRepository;
    @Autowired
    private RemesasRepository remRepository;
    @Autowired
    private empleadoRepository emRepository;

    public static final Logger log = LoggerFactory.getLogger(RecibosRepository.class);

    public RecibosController(RecibosRepository rec) {
            this.recRepository = rec;
        }

    @GetMapping("/recibos")
    List<Recibo> readAll() {
        return (List<Recibo>) recRepository.findAll();
    }

    @GetMapping("/recibos/remesa/{id}")
    List<Recibo> read_remesa(@PathVariable Integer id) {
        Optional<Remesa> opcionalRemesa = remRepository.findById(id);
        Remesa remesa = opcionalRemesa
                .orElseThrow(() -> new NoSuchElementException("No se encontró la remesa con el ID proporcionado."));
        return (List<Recibo>) recRepository.findByIdRemesa(remesa);
    }

    @GetMapping("/recibos/empleado/{id}")
    List<Recibo> read_remesa(@PathVariable String id) {
        Optional<Empleado> opcionalEmpleado = emRepository.findById(id);
        Empleado empleado = opcionalEmpleado
                .orElseThrow(() -> new NoSuchElementException("No se encontró al empleado con el ID proporcionado."));
        return (List<Recibo>) recRepository.findByIdEmpleado(empleado);
    }

    @PostMapping("/recibos")
    ResponseEntity<Recibo> create(@RequestBody Recibo newRec) throws URISyntaxException {
        Recibo result = recRepository.save(newRec);
        return ResponseEntity.created(new URI("/recibos/" + result.getIdRecibo())).body(result);
    }

    @GetMapping("/recibos/{id}")
    ResponseEntity<Recibo> read(@PathVariable Integer id) {
        return recRepository.findById(id).map(recibo -> ResponseEntity.ok().body(recibo))
                .orElse(new ResponseEntity<Recibo>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/recibos/{id}")
    ResponseEntity<Recibo> update(@RequestBody Recibo newRec, @PathVariable Integer id) {
        return recRepository.findById(id).map(recibo -> {
            recibo.setIdRemesa(newRec.getIdRemesa());
            recibo.setIdEmpleado(newRec.getIdEmpleado());
            recibo.setIBAN(newRec.getIBAN());
            recibo.setSWIFT(newRec.getSWIFT());
            recibo.setBruto(newRec.getBruto());
            recibo.setDeduccion(newRec.getDeduccion());
            recibo.setNeto(newRec.getNeto());
            recRepository.save(recibo);
            return ResponseEntity.ok().body(recibo);
        }).orElse(new ResponseEntity<Recibo>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("recibos/{id}")
    ResponseEntity<Recibo> delete(@PathVariable Integer id) {
        recRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

}
