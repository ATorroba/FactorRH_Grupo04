package es.upm.dit.isst.tfgapi.repository;

import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Rol;
import es.upm.dit.isst.tfgapi.model.RolPK;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RolRepository extends CrudRepository<Rol, RolPK>{
}
