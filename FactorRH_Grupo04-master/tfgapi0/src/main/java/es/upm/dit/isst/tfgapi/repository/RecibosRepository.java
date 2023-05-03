package es.upm.dit.isst.tfgapi.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Recibo;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "recibo", path = "recibos")
public interface RecibosRepository extends CrudRepository<Recibo, Integer> {
    
}
