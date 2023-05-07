package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
// import java.net.URI;
// import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.tfgapi.model.Ausencias;
import es.upm.dit.isst.tfgapi.repository.AusenciasRepository;

@RestController

public class AusenciasController {

    private final AusenciasRepository ausenciasRepository;

    public AusenciasController(AusenciasRepository t) {
        this.ausenciasRepository = t;

    }

    @GetMapping("/ausencias")
    List<Ausencias> readAll() {
        return (List<Ausencias>) ausenciasRepository.findAll();
    }

    @PostMapping("/aceptaraus/{idausencia}")
    ResponseEntity<Ausencias> acc(@PathVariable Integer idausencia) {
        ResponseEntity<Ausencias> f = ausenciasRepository.findByIdausencia(idausencia)
                .map(puesto -> ResponseEntity.ok().body(puesto)

                )
                .orElse(new ResponseEntity<Ausencias>(HttpStatus.NOT_FOUND));
        if (f.getBody() != null) {
            f.getBody().setAutorizada("1");
            ausenciasRepository.save(f.getBody());
        }
        return f;
    }

    @PostMapping("/denegaraus/{idausencia}")
    ResponseEntity<Ausencias> denacc(@PathVariable Integer idausencia) {
        ResponseEntity<Ausencias> f = ausenciasRepository.findByIdausencia(idausencia)
                .map(puesto -> ResponseEntity.ok().body(puesto)

                )
                .orElse(new ResponseEntity<Ausencias>(HttpStatus.NOT_FOUND));
        if (f.getBody() != null) {
            f.getBody().setAutorizada("2");
            ausenciasRepository.save(f.getBody());
        }
        return f;
    }

    @GetMapping("/ausenciasaus/{idausencia}")

    Optional<Ausencias> readByIdausencia(@PathVariable Integer idausencia) {
        return (Optional<Ausencias>) ausenciasRepository.findByIdausencia(idausencia);
    }

    @DeleteMapping("/ausencias/{idausencia}")
    ResponseEntity<Ausencias> delete(@PathVariable Integer idausencia) {
        ausenciasRepository.deleteByIdausencia(idausencia);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/ausencias/{idEmpleado}")

    List<Ausencias> readByIdEmpleado(@PathVariable String idEmpleado) {
        return (List<Ausencias>) ausenciasRepository.findByIdempleado(idEmpleado);
    }

    @PostMapping("/ausencias")

    ResponseEntity<Ausencias> create(@RequestBody Ausencias newTFG) throws URISyntaxException {
        Ausencias result = ausenciasRepository.save(newTFG);
        return ResponseEntity.created(new URI("/ausencias/" + result.getIdempleado())).body(result);
    }

}