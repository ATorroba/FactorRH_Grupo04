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

    /**
     * @param idRemesa
     * @return la salida es OK si se ha efectuado correctamente el cálculo de la
     *         nómina
     */
    @PostMapping("/crear_recibos")
    public ResponseEntity<Void> crearRecibos(@RequestParam(name = "idRemesa") Integer idRemesa) {
        if (idRemesa == null)
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        try {
            nominaService.crearRecibos(idRemesa);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
