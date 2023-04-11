package es.upm.dit.isst.tfgapi.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Jornadas;
//import es.upm.dit.isst.tfgapi.model.combinacionPKs;

public interface JornadasRepository extends CrudRepository<Jornadas, String> {

    List<Jornadas> findByidEmpleado(String idEmpleado);
    // List<Jornadas> findByidEmpleadoYdia(String idEmpleado, Integer idDia);
    // List<Jornadas> findByDia(Integer idDia);

}
