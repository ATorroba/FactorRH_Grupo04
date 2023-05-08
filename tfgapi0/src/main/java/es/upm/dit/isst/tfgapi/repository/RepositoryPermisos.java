package es.upm.dit.isst.tfgapi.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.upm.dit.isst.tfgapi.model.Permisos;
//import es.upm.dit.isst.tfgapi.model.combinacionPKs;
import es.upm.dit.isst.tfgapi.model.permisosPK;


@RepositoryRestResource(collectionResourceRel = "permisos", path = "permisos")
public interface RepositoryPermisos extends CrudRepository<Permisos, permisosPK> {

    List<Permisos> findByidEmpleado(String idEmpleado);
    // List<Permisos> findByidEmpleadoYejercicio(String idEmpleado, Integer ejercicio);

}


