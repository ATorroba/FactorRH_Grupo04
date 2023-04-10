package es.upm.dit.isst.tfgapi.controller;

// import java.net.URI;
// import java.net.URISyntaxException;
import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
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


    @GetMapping("/ausencias/{empleado}")

    ResponseEntity<Ausencias> read(@PathVariable String empleado) {

        return ausenciasRepository.findById(empleado).map(ausencia ->
        ResponseEntity.ok().body(ausencia)
        ).orElse(new ResponseEntity<Ausencias>(HttpStatus.NOT_FOUND));

    }

}