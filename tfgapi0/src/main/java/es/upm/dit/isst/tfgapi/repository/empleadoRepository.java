package es.upm.dit.isst.tfgapi.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import es.upm.dit.isst.tfgapi.model.Empleado;

public interface empleadoRepository extends CrudRepository<Empleado, String> {

    List<Empleado> findByidEmpleado(String id);

    Empleado findByEmail(String email);
    
    @Autowired
    @Query(value = "Select id_empleado, cp, iban, nass, nif, swift, antiguedad, apellido_1, apellido_2," +
                   "direccion, email, email_particular, fecha_alta, fecha_baja, nombre, "+
                   "password, puesto, sueldo_base, telefono "+
                   "from empleado where fecha_baja is null" , nativeQuery = true)
    List<Empleado> seleccionarEmpleadosNomina();
}
