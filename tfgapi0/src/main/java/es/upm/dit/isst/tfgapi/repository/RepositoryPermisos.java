package es.upm.dit.isst.tfgapi.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Permisos;
//import es.upm.dit.isst.tfgapi.model.combinacionPKs;

public interface RepositoryPermisos extends CrudRepository<Permisos, Object> {

    List<Permisos> findByidEmpleado(String idEmpleado);
    // List<Permisos> findByidEmpleadoYejercicio(String idEmpleado, Integer ejercicio);

}


