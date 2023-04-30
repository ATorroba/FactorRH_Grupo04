package es.upm.dit.isst.tfgapi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.time.LocalDate;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import es.upm.dit.isst.tfgapi.model.Empleado;
import es.upm.dit.isst.tfgapi.model.Recibo;
import es.upm.dit.isst.tfgapi.model.Remesa;
import es.upm.dit.isst.tfgapi.repository.RecibosRepository;
import es.upm.dit.isst.tfgapi.repository.RemesasRepository;
import es.upm.dit.isst.tfgapi.repository.empleadoRepository;

@Service
public class NominaService {
    @Autowired
    private RemesasRepository remesasRepository;
    
    @Autowired
    private empleadoRepository empRepository;

    @Autowired
    private RecibosRepository recRepository;

    @Async
    public void crearRecibos(Integer remesa_entrada) {
        
        System.out.printf("Llega hasta el punto de entrada.%n");
        
        Optional<Remesa> opcionalRemesa = remesasRepository.findById(remesa_entrada);;
        Remesa remesa = opcionalRemesa.orElseThrow(() -> new NoSuchElementException("No se encontró la remesa con el ID proporcionado."));
        System.out.printf("Localizada la remesa %d %n",remesa_entrada);
        
        //Calculamos las fechas de inicio y fin con los datos de la remesa
        Date inicio = Date.valueOf(LocalDate.of(remesa.getEjercicio(), remesa.getMes(), 1));
        Date fin = Date.valueOf(LocalDate.of(remesa.getEjercicio(), remesa.getMes(),1).withDayOfMonth(LocalDate.of(remesa.getEjercicio(), remesa.getMes(), 1).lengthOfMonth()));
        System.out.printf("Se obtiene la lista de emplados de %s a %s %n",inicio, fin);
        List<Empleado> listaEmpleados = (List<Empleado>) empRepository.seleccionarEmpleadosNomina(inicio,fin);
        System.out.printf("Se ha obtenido la lista de emplados %n");

        for(Empleado empleado: listaEmpleados){
            Recibo recibo = new Recibo();
                    
            recibo.setIdRemesa(remesa);
            recibo.setIdEmpleado(empleado);
            recibo.setFecha_pago(remesa.getFecha_pago());
            recibo.setIBAN(empleado.getIBAN());
            recibo.setSWIFT(empleado.getSWIFT());
                     
            System.out.printf("Recibo nómina empleado %s %n",empleado.getIdEmpleado());
            recRepository.save(recibo);
            
        }
    }
}
