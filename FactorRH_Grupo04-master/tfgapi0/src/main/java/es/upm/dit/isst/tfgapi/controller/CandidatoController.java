package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

//import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.tfgapi.model.Candidato;
import es.upm.dit.isst.tfgapi.repository.CandidatoRepository;

@RestController

public class CandidatoController {

    private final CandidatoRepository candidatoRepository;

    // public static final Logger log =
    // LoggerFactory.getLogger(TFGController.class);
    public CandidatoController(CandidatoRepository t) {
        this.candidatoRepository = t;

    }

    @GetMapping("/candidatos")
    List<Candidato> readAll() {
        return (List<Candidato>) candidatoRepository.findAll();
    }

    @PostMapping("/candidatos")
    ResponseEntity<Candidato> create(@RequestBody Candidato newCandidato) throws URISyntaxException {
        Candidato result = candidatoRepository.save(newCandidato);
        return ResponseEntity.created(new URI("/candidatos/" + result.getidcandidato())).body(result);
    }

    @GetMapping("/candidatos/{id}")
    ResponseEntity<Candidato> read(@PathVariable String id) {
        return candidatoRepository.findById(id).map(candidato -> ResponseEntity.ok().body(candidato))
                .orElse(new ResponseEntity<Candidato>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/candidatos/{id}")
    ResponseEntity<Candidato> update(@RequestBody Candidato newCandidato, @PathVariable String id) {
        return candidatoRepository.findById(id).map(candidato -> {
            candidato.setidcandidato(newCandidato.getidcandidato());
            candidato.setnombre(newCandidato.getnombre());
            candidato.setapellido_1(newCandidato.getapellido_1());
            candidato.setapellido_2(newCandidato.getapellido_2());
            candidato.setEmail(newCandidato.getEmail());
            candidato.setform_Experiencia(newCandidato.getform_Experiencia());
            candidato.setidiomas(newCandidato.getidiomas());
            candidato.setdisponibilidad(newCandidato.getdisponibilidad());
            candidato.setnotas_reclutador(newCandidato.getnotas_reclutador());
            candidato.setcurriculum(newCandidato.getcurriculum());
            candidato.setpreseleccionado(newCandidato.getpreseleccionado());
            candidato.setpuesto(newCandidato.getpuesto());
            candidatoRepository.save(candidato);
            return ResponseEntity.ok().body(candidato);
        }).orElse(new ResponseEntity<Candidato>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/candidatos/select/{id}")
    ResponseEntity<Candidato> select(@PathVariable String id) {
        return candidatoRepository.findById(id).map(candidato -> {

            candidato.setpreseleccionado("1");
            candidatoRepository.save(candidato);
            return ResponseEntity.ok().body(candidato);
        }).orElse(new ResponseEntity<Candidato>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/candidatos/deselect/{id}")
    ResponseEntity<Candidato> deselect(@PathVariable String id) {
        return candidatoRepository.findById(id).map(candidato -> {

            candidato.setpreseleccionado("0");
            candidatoRepository.save(candidato);
            return ResponseEntity.ok().body(candidato);
        }).orElse(new ResponseEntity<Candidato>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("candidatos/{id}")
    ResponseEntity<Candidato> delete(@PathVariable String id) {
        candidatoRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/candidatos/puesto/{id}")
    List<Candidato> candidatoPorPuesto(@PathVariable String id) {
        return (List<Candidato>) candidatoRepository.findByPuesto(id);
    }

    // @GetMapping("/tfgs/profesor/{id}")
    // List<Candidato> readdireccion(@PathVariable String id) {
    // return (List<Candidato>) candidatoRepository.findBy(id);
    // }

    // @PostMapping("/tfgs/{id}/incrementa")
    // ResponseEntity<Candidato> incrementa(@PathVariable String id) {
    // return candidatoRepository.findById(id).map(candidato -> {
    // candidato.setStatus(candidato.getStatus() + 1);
    // candidatoRepository.save(candidato);
    // return ResponseEntity.ok().body(candidato);
    // }).orElse(new ResponseEntity<Candidato>(HttpStatus.NOT_FOUND));
    // }
}