package es.upm.dit.isst.tfgapi.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Concepto;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "concepto", path = "conceptos")
public interface ConceptoRepository extends CrudRepository<Concepto, Integer> {
    
}
