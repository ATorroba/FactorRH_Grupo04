package es.upm.dit.isst.tfgapi.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Candidato;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "candidatos", path = "candidatos")
public interface CandidatoRepository extends CrudRepository<Candidato, String> {

    List<Candidato> findByPuesto(String puesto);

}
