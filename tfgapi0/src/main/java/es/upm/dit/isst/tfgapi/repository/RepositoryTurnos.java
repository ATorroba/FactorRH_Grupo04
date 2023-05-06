package es.upm.dit.isst.tfgapi.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Turnos;

public interface RepositoryTurnos extends CrudRepository<Turnos, Integer> {

    List<Turnos> findByidEmpleado(String idEmpleado);
    // List<Turnos> findByidEmpleadoyDia(String idEmpleado, Integer idDia);



}