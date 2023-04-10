package es.upm.dit.isst.tfgapi.repository;

import java.util.List;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.upm.dit.isst.tfgapi.model.Puesto;

@RepositoryRestResource(collectionResourceRel = "puestos", path ="puestos")
//public interface PuestoRepository extends JpaRepository<Puesto, String> {
public interface PuestoRepository extends CrudRepository<Puesto, String> {

    //@Autowired
    //@Query("SELECT p FROM Product p WHERE p.price > ?1 AND p.stock > ?2")
    //@Query("Select IdPuesto, Nombre_puesto, Desc_puesto from puesto where idDepto = ?1")
    //List<Puesto> buscarPorDepartamento(String id);
    List<Puesto> findByDepto(String depto);
}
