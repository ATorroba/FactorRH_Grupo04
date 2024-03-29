package es.upm.dit.isst.tfgapi.repository;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import es.upm.dit.isst.tfgapi.model.Puesto;

@RepositoryRestResource(collectionResourceRel = "puestos", path = "puestos")
public interface PuestoRepository extends CrudRepository<Puesto, String> {
    // public interface PuestoRepository extends CrudRepository<Puesto, String> {

    // @Autowired
    // @Query("SELECT p FROM Product p WHERE p.price > ?1 AND p.stock > ?2")
    // @Query("Select IdPuesto, Nombre_puesto, Desc_puesto from puesto where idDepto
    // = ?1")
    // List<Puesto> buscarPorDepartamento(String id);
    List<Puesto> findByDepto(Integer depto);

    // void deleteByIdpuesto(Integer id);

    List<Puesto> findByNombre(String nombre_puesto);

    Optional<Puesto> findByIdpuesto(Integer idpuesto);

    @Transactional

    void deleteByidpuesto(Integer idpuesto);

    List<Puesto> findByEstado(Integer estado);

    @Autowired
    @Query(value = "select idpuesto, nombre_puesto, desc_puesto, sueldo_orientativo, " +
            "req_exp_form, req_idiomas, req_disponibilidad, req_otros, depto, estado " +
            "from puestos where estado <'2'", nativeQuery = true)
    List<Puesto> buscarPuestoLibre();

}
