package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.tfgapi.model.Empleado;
import es.upm.dit.isst.tfgapi.model.IncidenciaNomina;
import es.upm.dit.isst.tfgapi.repository.IncidenciaNominaRepository;
import es.upm.dit.isst.tfgapi.repository.empleadoRepository;

@RestController
public class IncidenciaNominaController {
    
    private final IncidenciaNominaRepository incRepository;
    public static final Logger log = LoggerFactory.getLogger(IncidenciaNominaRepository.class);

    @Autowired
    private empleadoRepository emRepository;
    
    public IncidenciaNominaController(IncidenciaNominaRepository inc) {
        this.incRepository = inc;
    }

    @GetMapping("/incidencias_n")
    List<IncidenciaNomina> readAll() {
        return (List<IncidenciaNomina>) incRepository.findAll();
    }

    @PostMapping("/incidencias_n")
    ResponseEntity<IncidenciaNomina> create(@RequestBody IncidenciaNomina newInc) throws URISyntaxException {
        IncidenciaNomina result = incRepository.save(newInc);
        return ResponseEntity.created(new URI("/incidencias_n/" + result.getIdConcepto())).body(result);
    }

    @GetMapping("/incidencias_n/{id}")
    ResponseEntity<IncidenciaNomina> read(@PathVariable Integer id) {
        return incRepository.findById(id).map(conceptoRecibo -> ResponseEntity.ok().body(conceptoRecibo))
                .orElse(new ResponseEntity<IncidenciaNomina>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/incidencias_n/{id}")
    ResponseEntity<IncidenciaNomina> update(@RequestBody IncidenciaNomina newInc, @PathVariable Integer id) {
        return incRepository.findById(id).map(incidenciaNomina -> {
            incidenciaNomina.setIdEmpleado(newInc.getIdEmpleado());
            incidenciaNomina.setIdConcepto(newInc.getIdConcepto());
            incidenciaNomina.setEjercicio(newInc.getEjercicio());
            incidenciaNomina.setMes(newInc.getMes());
            incidenciaNomina.setUnidades(newInc.getUnidades());
            incidenciaNomina.setPrecio(newInc.getPrecio());
            incidenciaNomina.setImporte(newInc.getImporte());
            incidenciaNomina.setIdRemesa(newInc.getIdRemesa());

            incRepository.save(incidenciaNomina);
            return ResponseEntity.ok().body(incidenciaNomina);
        }).orElse(new ResponseEntity<IncidenciaNomina>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("incidencias_n/{id}")
    ResponseEntity<IncidenciaNomina> delete(@PathVariable Integer id) {
        incRepository.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/incidencias_n/empleado/{id}")
    List<IncidenciaNomina> read_remesa(@PathVariable String id) {
        Optional<Empleado> opcionalEmpleado = emRepository.findById(id);
        if (!opcionalEmpleado.isPresent()) {
            return new ArrayList<IncidenciaNomina>();
        }
        Empleado empleado = opcionalEmpleado.get();
        return (List<IncidenciaNomina>) incRepository.findByIdEmpleado(empleado);
        
    }

}

