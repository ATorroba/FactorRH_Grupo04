package es.upm.dit.isst.tfgapi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.time.LocalDate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import es.upm.dit.isst.tfgapi.model.Concepto;
import es.upm.dit.isst.tfgapi.model.ConceptoRecibo;
import es.upm.dit.isst.tfgapi.model.Empleado;
import es.upm.dit.isst.tfgapi.model.Recibo;
import es.upm.dit.isst.tfgapi.model.Remesa;
import es.upm.dit.isst.tfgapi.repository.ConceptoReciboRepository;
import es.upm.dit.isst.tfgapi.repository.ConceptoRepository;
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

    @Autowired
    private ConceptoRepository concM; // Concepto retributivo

    @Autowired
    private ConceptoReciboRepository concR; // Concepto remesa

    @Async
    public void crearRecibos(Integer remesa_entrada) {
        java.util.Date desde, hasta, inicio, fin;
        
        System.out.printf("Llega hasta el punto de entrada.%n");

        Optional<Remesa> opcionalRemesa = remesasRepository.findById(remesa_entrada);
        Remesa remesa = opcionalRemesa
                .orElseThrow(() -> new NoSuchElementException("No se encontró la remesa con el ID proporcionado."));
        System.out.printf("Localizada la remesa %d %n", remesa_entrada);

        // Un poco de la estructura salariar, ceoncepto de IRPF y SS:
        Optional<Concepto> opcionalConcepto = concM.findById((Integer) 70);
        Concepto concM70 = opcionalConcepto
                .orElseThrow(() -> new NoSuchElementException("No se encontró INFO IRPF en estructura salarial."));

        opcionalConcepto = concM.findById((Integer) 71);
        Concepto concM71 = opcionalConcepto
                .orElseThrow(() -> new NoSuchElementException("No se encontró INFO SS en estructura salarial."));

        // Calculamos las fechas de inicio y fin con los datos de la remesa
        inicio = Date.valueOf(LocalDate.of(remesa.getEjercicio(), remesa.getMes(), 1));
        fin = Date.valueOf(LocalDate.of(remesa.getEjercicio(), remesa.getMes(), 1)
                .withDayOfMonth(LocalDate.of(remesa.getEjercicio(), remesa.getMes(), 1).lengthOfMonth()));
        Long dias_mes = 1 + (fin.getTime() - inicio.getTime()) / (24 * 60 * 60 * 1000);

        System.out.printf("Se obtiene la lista de emplados de %s a %s %n", inicio, fin);
        List<Empleado> listaEmpleados = (List<Empleado>) empRepository.seleccionarEmpleadosNomina((Date) inicio,
                (Date) fin);
        System.out.printf("Se ha obtenido la lista de emplados %n");

        for (Empleado empleado : listaEmpleados) {
            Recibo recibo = new Recibo();

            recibo.setIdRemesa(remesa);
            recibo.setIdEmpleado(empleado);
            recibo.setFecha_pago(remesa.getFecha_pago());
            recibo.setIBAN(empleado.getIBAN());
            recibo.setSWIFT(empleado.getSWIFT());

            System.out.printf("Recibo nómina empleado %s %n", empleado.getIdEmpleado());
            recRepository.save(recibo);

            // Empezamos el cálculo de la nómina calculando las unidades de trabajo como el
            // % de tiempo al mes
            desde = (empleado.getFecha_alta().before(inicio)) ? inicio : (Date) empleado.getFecha_alta();
            if (empleado.getFecha_baja() == null) {
                hasta = fin;
            } else {
                hasta = (empleado.getFecha_baja().after(fin)) ? fin : (Date) empleado.getFecha_baja();
            }
            ;

            Long dias_trabajo = (hasta.getTime() - desde.getTime()) / (24 * 60 * 60 * 1000);

            double unidades = ((double) dias_trabajo) / ((double) dias_mes);
            double importe_sueldo = empleado.getSueldo_base() * unidades;
            double importe_antiguedad = empleado.getAntiguedad() * unidades;
            double bruto = importe_sueldo + importe_antiguedad;

            // preparamos la lista de conceptos a dar de alta
            // List<ConceptoRecibo> conceptos = new ArrayList<>();

            // Sueldo base. Concepto 10
            ConceptoRecibo conc10 = new ConceptoRecibo();
            conc10.setIdRecibo(recibo);
            conc10.setIdConcepto((concM.findById((Integer) 10)).get());
            conc10.setUnidades(redondear(unidades));
            conc10.setPrecio(redondear(empleado.getSueldo_base()));
            conc10.setDevengo(redondear(importe_sueldo));
            conc10.setDeduccion((double) 0);
            concR.save(conc10);

            // Antiguedad. Concepto 11
            ConceptoRecibo conc11 = new ConceptoRecibo();
            conc11.setIdRecibo(recibo);
            conc11.setIdConcepto((concM.findById((Integer) 11)).get());
            conc11.setUnidades(redondear(unidades));
            conc11.setPrecio(redondear(empleado.getAntiguedad()));
            conc11.setDevengo(redondear(importe_antiguedad));
            conc11.setDeduccion((double) 0);
            concR.save(conc11);

            // IRPF. Concepto 70
            ConceptoRecibo conc70 = new ConceptoRecibo();
            conc70.setIdRecibo(recibo);
            conc70.setIdConcepto(concM70);
            conc70.setUnidades(redondear(concM70.getPrecio()));
            conc70.setPrecio(redondear(bruto));
            conc70.setDevengo((double) 0);
            conc70.setDeduccion(redondear(bruto * concM70.getPrecio() / 100));
            concR.save(conc70);

            // SS. Concepto 71
            ConceptoRecibo conc71 = new ConceptoRecibo();
            conc71.setIdRecibo(recibo);
            conc71.setIdConcepto(concM71);
            conc71.setUnidades(redondear(concM71.getPrecio()));
            conc71.setPrecio(redondear(bruto));
            conc71.setDevengo((double) 0);
            conc71.setDeduccion(redondear(bruto * concM71.getPrecio() / 100));
            concR.save(conc71);

        }
    }

    public static double redondear(double valor) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    // private void crear_Concepto(Remesa remesa, Recibo recibo, Concepto concepto,
    // Double unidades, Double precio, Double importe ){}

}
