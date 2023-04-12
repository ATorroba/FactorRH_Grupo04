package es.upm.dit.isst.tfgapi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Departamento;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "departamentos", path = "departamentos")
public interface DepartamentosRepository extends CrudRepository<Departamento, String> {
    List<Departamento> findByPadre(String padre);

    List<Departamento> findByNombre(String nombre);

    @Autowired
    // @Query(value = "Select id_depto, nombre, oficina, padre from departament
    // where id_depto = padre", nativeQuery = true)

    @Query(value = "Select depto, nombre, oficina, padre from departamento where padre = 'roo'", nativeQuery = true)
    List<Departamento> buscarDepartamentoRaiz();
}
