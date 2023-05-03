package es.upm.dit.isst.tfgapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import es.upm.dit.isst.tfgapi.model.Empleado;
import es.upm.dit.isst.tfgapi.model.Recibo;
import es.upm.dit.isst.tfgapi.model.Remesa;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "recibo", path = "recibos")
public interface RecibosRepository extends CrudRepository<Recibo, Integer> {
    
    List<Recibo> findByIdRemesa(Remesa idRemesa);
    
    List<Recibo> findByIdEmpleado(Empleado idEmpleado);
    
}
