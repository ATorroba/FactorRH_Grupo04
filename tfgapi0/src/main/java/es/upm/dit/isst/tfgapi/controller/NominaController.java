package es.upm.dit.isst.tfgapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.tfgapi.service.NominaService;

@RestController
public class NominaController {
    @Autowired
    private NominaService nominaService;

    @PostMapping("/crear_recibos")
    public ResponseEntity<Void> crearRecibos(@RequestParam(name = "idRemesa") Integer idRemesa) {
        if (idRemesa == null) return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        nominaService.crearRecibos(idRemesa);
        return ResponseEntity.ok().build();
        }
}
