package es.upm.dit.isst.tfgapi.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Turnos;

public interface RepositoryTurnos extends CrudRepository<Turnos, Integer> {

    List<Turnos> findByidEmpleado(String idEmpleado);
    Turnos findByIdEmpleadoAndIdDia(String idEmpleado, Integer idDia);



}