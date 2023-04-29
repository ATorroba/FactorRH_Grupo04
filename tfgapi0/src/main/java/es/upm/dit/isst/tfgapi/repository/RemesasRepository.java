package es.upm.dit.isst.tfgapi.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Remesa;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "remesa", path = "remesas")
public interface RemesasRepository extends CrudRepository<Remesa, Integer> {
    
}
