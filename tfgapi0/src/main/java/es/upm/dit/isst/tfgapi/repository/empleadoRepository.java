package es.upm.dit.isst.tfgapi.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;

import es.upm.dit.isst.tfgapi.model.Empleado;

public interface empleadoRepository extends CrudRepository<Empleado, String> {

    List<Empleado> findByidEmpleado(String id);

    Empleado findByEmail(String email);
}
