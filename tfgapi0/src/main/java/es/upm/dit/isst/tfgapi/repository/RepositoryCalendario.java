package es.upm.dit.isst.tfgapi.repository;
import java.util.Date;
import java.util.List;
import org.springframework.data.repository.CrudRepository;
import es.upm.dit.isst.tfgapi.model.Calendario;
//import es.upm.dit.isst.tfgapi.model.combinacionPKs;

public interface RepositoryCalendario extends CrudRepository<Calendario, Date> {

    List<Calendario> findByFecha(Date fecha); //????

}
