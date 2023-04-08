package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
// import java.util.List;

// import org.hibernate.annotations.common.util.impl.LoggerFactory;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.tfgapi.model.Calendario;
//import es.upm.dit.isst.tfgapi.model.combinacionPKs;
import es.upm.dit.isst.tfgapi.repository.RepositoryCalendario;
import org.springframework.ui.Model;

@RestController
public class ControllerCalendario {
    
    private final RepositoryCalendario calendarioRepository;
    //public static final Logger log = LoggerFactory.getLogger(TFGController.class);
    public ControllerCalendario(RepositoryCalendario c) {
        this.calendarioRepository = c;

    }

    @GetMapping("/calendario")
    public String obtenerCalendario(Model model) {
        Iterable<Calendario> listaCalendario = calendarioRepository.findAll();
        model.addAttribute("calendario", listaCalendario);
        return "{listaCalendario}";
    }

    @PostMapping("/calendario")

    ResponseEntity<Calendario> create(@RequestBody Calendario newCalendario) throws URISyntaxException {
        Calendario result = calendarioRepository.save(newCalendario);
        return ResponseEntity.created(new URI("/calendario/" + result.getFecha())).body(result);
    }
}
