package es.upm.dit.isst.tfgapi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.upm.dit.isst.tfgapi.model.Rol;
import es.upm.dit.isst.tfgapi.model.RolPK;

@RepositoryRestResource(collectionResourceRel = "roles", path = "roles")
public interface RolRepository extends CrudRepository<Rol, RolPK> {
    List<Rol> findByClave_Idrol(String idrol);

    void deleteByClave(RolPK clave);

    @Autowired
    @Query(value = "select idempleado, idrol, activo from Rol where idempleado =?", nativeQuery = true)
    List<Rol> buscarRoles(String idEmpleado);

    @Autowired
    @Query(value = "    SELECT a.idrol , a.idempleado AS Rol , b.nombre AS Empleado  from Rol a inner join Empleado b on b.id_Empleado = a.idempleado", nativeQuery = true)
    List<Object> join();

}
