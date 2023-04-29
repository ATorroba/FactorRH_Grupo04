package es.upm.dit.isst.tfgapi.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.ConceptoRecibo;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "conceptoRecibo", path = "conceptosRecibo")
public interface ConceptoReciboRepository extends CrudRepository<ConceptoRecibo, Long> {
    
}
