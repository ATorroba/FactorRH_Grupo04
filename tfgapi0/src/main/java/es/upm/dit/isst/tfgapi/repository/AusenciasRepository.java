package es.upm.dit.isst.tfgapi.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;

// import org.springframework.data.jpa.repository.Query;
import es.upm.dit.isst.tfgapi.model.Ausencias;
// import java.util.Date;

public interface AusenciasRepository extends CrudRepository<Ausencias, String> {

    List<Ausencias> findByIdempleado(String idEmpleado);

    Optional<Ausencias> findByIdausencia(Integer idausencia);

    @Transactional

    void deleteByIdausencia(Integer idpuesto);
    // @Autowired
    // @Query(value = "Select * from ausencias where idempleado = idempleado and
    // inicio = inicio", nativeQuery = true)
    // List<Ausencias> buscarAusenciaPorDia(String idempleado, Date inicio);

}