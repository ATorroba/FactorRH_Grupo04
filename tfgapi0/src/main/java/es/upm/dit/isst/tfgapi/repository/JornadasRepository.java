package es.upm.dit.isst.tfgapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.upm.dit.isst.tfgapi.model.Jornadas;
import es.upm.dit.isst.tfgapi.model.jornadasPK;

@RepositoryRestResource(collectionResourceRel = "jornadas", path = "jornadas")
public interface JornadasRepository extends CrudRepository<Jornadas, jornadasPK> {
    List<Jornadas> findByIdEmpleado(String idEmpleado);
    List<Jornadas> findByFecha(LocalDate fecha);
    List<Jornadas> findByEstado(String estado);
    List<Jornadas> findByIncidenciaAndEstado(String incidencia, String estado);
}
