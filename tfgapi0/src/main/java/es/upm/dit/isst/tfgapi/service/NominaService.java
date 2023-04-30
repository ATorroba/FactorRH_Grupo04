package es.upm.dit.isst.tfgapi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    public void crearRecibos(Integer remesa_entrada) {
        
        System.out.printf("Llega hasta el punto de entrada.%n");
        
        Optional<Remesa> opcionalRemesa = remesasRepository.findById(remesa_entrada);;
        Remesa remesa = opcionalRemesa.orElseThrow(() -> new NoSuchElementException("No se encontró la remesa con el ID proporcionado."));
        
        System.out.printf("Localizada la remesa %d %n",remesa_entrada);
        List<Empleado> listaEmpleados = (List<Empleado>) empRepository.seleccionarEmpleadosNomina();
        System.out.printf("Se obtiene la lista de emplados %n");

        for(Empleado empleado: listaEmpleados){
            Recibo recibo = new Recibo();
                    
            recibo.setIdRemesa(remesa);
            recibo.setIdEmpleado(empleado);
            recibo.setFecha_pago(remesa.getFecha_pago());
            recibo.setIBAN(empleado.getIBAN());
            recibo.setSWIFT(empleado.getSWIFT());
                        
            System.out.printf("Nómina empleado %s %n",empleado.getIdEmpleado());
            recRepository.save(recibo);
        }
    }
}
