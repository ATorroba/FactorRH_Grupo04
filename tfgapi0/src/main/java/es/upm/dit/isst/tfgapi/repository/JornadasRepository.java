package es.upm.dit.isst.tfgapi.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import es.upm.dit.isst.tfgapi.model.Jornadas;
import es.upm.dit.isst.tfgapi.model.jornadasPK;

@RepositoryRestResource(collectionResourceRel = "jornadas", path = "jornadas")
public interface JornadasRepository extends CrudRepository<Jornadas, jornadasPK> {

    List<Jornadas> findByClave_IdEmpleado(String idEmpleado);
    List<Jornadas> findByClave_Fecha(LocalDate fecha);
}
