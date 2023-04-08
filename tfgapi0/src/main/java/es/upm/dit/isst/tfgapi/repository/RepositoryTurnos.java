package es.upm.dit.isst.tfgapi.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Turnos;
//import es.upm.dit.isst.tfgapi.model.combinacionPKs;

public interface RepositoryTurnos extends CrudRepository<Turnos, String> {

    List<Turnos> findByidEmpleado(String idEmpleado); //????

}
