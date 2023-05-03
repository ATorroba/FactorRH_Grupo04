package es.upm.dit.isst.tfgapi.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.IncidenciaNomina;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "incidecia_n", path = "incidencias_n")
public interface IncidenciaNominaRepository extends CrudRepository<IncidenciaNomina, Integer> {
    
}
