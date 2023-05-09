package es.upm.dit.isst.tfgapi.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.transaction.Transactional;

import java.time.LocalDate;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import es.upm.dit.isst.tfgapi.model.Concepto;
import es.upm.dit.isst.tfgapi.model.ConceptoRecibo;
import es.upm.dit.isst.tfgapi.model.Empleado;
import es.upm.dit.isst.tfgapi.model.Recibo;
import es.upm.dit.isst.tfgapi.model.Remesa;
import es.upm.dit.isst.tfgapi.model.IncidenciaNomina;

import es.upm.dit.isst.tfgapi.repository.ConceptoReciboRepository;
import es.upm.dit.isst.tfgapi.repository.ConceptoRepository;
import es.upm.dit.isst.tfgapi.repository.RecibosRepository;
import es.upm.dit.isst.tfgapi.repository.RemesasRepository;
import es.upm.dit.isst.tfgapi.repository.empleadoRepository;
import es.upm.dit.isst.tfgapi.repository.IncidenciaNominaRepository;

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

    @Autowired
    private IncidenciaNominaRepository incNRepo; // incidencias de nómina

    //@Async
    @Transactional
    public void crearRecibos(Integer remesa_entrada) {
        java.util.Date desde, hasta, inicio, fin;
        double importe_sueldo, importe_antiguedad, bruto, deducciones;
        Integer n_empleados = 0;
        double bruto_total = 0;
        double deduccion_total = 0;

        // System.out.printf("Llega hasta el punto de entrada.%n");
        // **** Buscar remesa para calcular *****
        Optional<Remesa> opcionalRemesa = remesasRepository.findById(remesa_entrada);
        Remesa remesa = opcionalRemesa
                .orElseThrow(() -> new NoSuchElementException("No se encontró la remesa con el ID proporcionado."));
        // System.out.printf("Localizada la remesa %d %n", remesa_entrada);

        // Un poco de la estructura salarial, concepto de IRPF y SS:
        Optional<Concepto> opcionalConcepto = concM.findById((Integer) 70);
        Concepto concM70 = opcionalConcepto
                .orElseThrow(() -> new NoSuchElementException("No se encontró INFO IRPF en estructura salarial."));

        opcionalConcepto = concM.findById((Integer) 71);
        Concepto concM71 = opcionalConcepto
                .orElseThrow(() -> new NoSuchElementException("No se encontró INFO SS en estructura salarial."));

        // Resto de estructura: bruto y neto
        opcionalConcepto = concM.findById((Integer) 98);
        Concepto concM98 = opcionalConcepto
                .orElseThrow(() -> new NoSuchElementException("No se encontró INFO BRUTO en estructura salarial."));

        opcionalConcepto = concM.findById((Integer) 99);
        Concepto concM99 = opcionalConcepto
                .orElseThrow(
                        () -> new NoSuchElementException("No se encontró INFO DEDUCCIONES en estructura salarial."));

        // *** FECHAS DE LA NOMINA
        // Calculamos las fechas de inicio y fin con los datos de la remesa
        inicio = Date.valueOf(LocalDate.of(remesa.getEjercicio(), remesa.getMes(), 1));
        fin = Date.valueOf(LocalDate.of(remesa.getEjercicio(), remesa.getMes(), 1)
                .withDayOfMonth(LocalDate.of(remesa.getEjercicio(), remesa.getMes(), 1).lengthOfMonth()));
        Long dias_mes = 1 + ((fin.getTime() - inicio.getTime()) / (24 * 60 * 60 * 1000));

        // System.out.printf("Se obtiene la lista de emplados de %s a %s %n", inicio,
        // fin);
        List<Empleado> listaEmpleados = (List<Empleado>) empRepository.seleccionarEmpleadosNomina((Date) inicio,
                (Date) fin);
        System.out.printf("Se ha obtenido la lista de emplados %n");

        // Bucle para recorrer todos los trabajadores a calcular
        for (Empleado empleado : listaEmpleados) {
            Recibo recibo = new Recibo();
            bruto = 0;
            deducciones = 0;

            recibo.setIdRemesa(remesa);
            recibo.setIdEmpleado(empleado);
            recibo.setIBAN(empleado.getIBAN());
            recibo.setSWIFT(empleado.getSWIFT());
            // System.out.printf("Recibo nómina empleado %s %n", empleado.getIdEmpleado());
            try {
                recRepository.save(recibo);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Error en alta recibo");
            }

            // Empezamos el proceso de la nómina calculando las unidades de trabajo de este
            // empleado en particular como el
            // % de tiempo de alta en el mes de cálculo
            desde = (empleado.getFecha_alta().before(inicio)) ? inicio : (Date) empleado.getFecha_alta();
            if (empleado.getFecha_baja() == null) {
                hasta = fin;
            } else {
                hasta = (empleado.getFecha_baja().after(fin)) ? fin : (Date) empleado.getFecha_baja();
            }

            Long dias_trabajo = 1 + ((hasta.getTime() - desde.getTime()) / (24 * 60 * 60 * 1000));
            double unidades = ((double) dias_trabajo) / ((double) dias_mes);

            if (empleado.getSueldo_base() == null) {
                importe_sueldo = 0;
            } else {
                importe_sueldo = empleado.getSueldo_base();
            }
            ;
            if ("1".equals(remesa.getTipo_nomina())) {
                importe_sueldo = importe_sueldo * unidades;
            }
            ;

            if (empleado.getAntiguedad() == null) {
                importe_antiguedad = 0;
            } else {
                importe_antiguedad = empleado.getAntiguedad();
            }
            ;
            if ("1".equals(remesa.getTipo_nomina())) {
                importe_antiguedad = importe_antiguedad * unidades;
            } else {
                importe_antiguedad = 0;
            }

            bruto = importe_sueldo + importe_antiguedad;

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
            try {
                concR.save(conc10);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Error en alta sueldo base");
            }
            ;
            if ("1".equals(remesa.getTipo_nomina())) {
                // Antiguedad. Concepto 11
                ConceptoRecibo conc11 = new ConceptoRecibo();
                conc11.setIdRecibo(recibo);
                conc11.setIdConcepto((concM.findById((Integer) 11)).get());
                conc11.setUnidades(redondear(unidades));
                conc11.setPrecio(redondear(empleado.getAntiguedad()));
                conc11.setDevengo(redondear(importe_antiguedad));
                conc11.setDeduccion((double) 0);
                try {
                    concR.save(conc11);
                } catch (Exception e) {
                    throw new DataIntegrityViolationException("Error en alta antigüedad");
                }
                ;
            }
            ;

            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // TRATAMIENTO DE LAS INCIDENCIAS DE NÓMINA
            // SOLO PARA LAS NOMINAS ORDINARIAS

            if ("1".equals(remesa.getTipo_nomina())) {
                List<IncidenciaNomina> listaIncidencias = (List<IncidenciaNomina>) incNRepo
                        .findByIdEmpleadoAndEjercicioAndMes(recibo.getIdEmpleado(), remesa.getEjercicio(),
                                remesa.getMes());

                // Bucle para recorrer todos los trabajadores a calcular
                for (IncidenciaNomina inc : listaIncidencias) {

                    ConceptoRecibo conc_inc = new ConceptoRecibo();
                    conc_inc.setIdRecibo(recibo);
                    conc_inc.setIdConcepto(inc.getIdConcepto());
                    if (inc.getUnidades() != null) {
                        conc_inc.setUnidades(redondear(inc.getUnidades()));
                    } else {
                        conc_inc.setUnidades((double) 0);
                    }
                    if (inc.getPrecio() != null) {
                        conc_inc.setPrecio(redondear(inc.getPrecio()));
                    } else {
                        conc_inc.setPrecio((double) 0);
                    }
                    if (inc.getIdConcepto().getIdConcepto() < 50) {
                        conc_inc.setDevengo(redondear(inc.getImporte()));
                        conc_inc.setDeduccion((double) 0);
                        bruto = bruto + redondear(inc.getImporte());
                    } else {
                        conc_inc.setDevengo((double) 0);
                        conc_inc.setDeduccion(redondear(inc.getImporte()));
                        deducciones = deducciones + redondear(inc.getImporte());
                    }
                    try {
                        concR.save(conc_inc);
                    } catch (Exception e) {
                        throw new DataIntegrityViolationException("Error en alta incidencia");
                    }
                    // Finalmente marcamos la incidencia con la remesa actual
                    inc.setIdRemesa(remesa);
                    try {
                        incNRepo.save(inc);
                    } catch (Exception e) {
                        throw new DataIntegrityViolationException("Error en marcado incidencia como calculada");
                    }
                }
            }
            // ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
            // Finalmente se calcula el IRPF y la SS
            // IRPF. Concepto 70
            ConceptoRecibo conc70 = new ConceptoRecibo();
            conc70.setIdRecibo(recibo);
            conc70.setIdConcepto(concM70);
            conc70.setUnidades(redondear(concM70.getPrecio()));
            conc70.setPrecio(redondear(bruto));
            conc70.setDevengo((double) 0);
            conc70.setDeduccion(redondear(bruto * concM70.getPrecio() / 100));
            try {
                concR.save(conc70);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Error en alta IRPF");
            }
            ;
            if ("1".equals(remesa.getTipo_nomina())) {
                // SS. Concepto 71
                ConceptoRecibo conc71 = new ConceptoRecibo();
                conc71.setIdRecibo(recibo);
                conc71.setIdConcepto(concM71);
                conc71.setUnidades(redondear(concM71.getPrecio()));
                conc71.setPrecio(redondear(bruto));
                conc71.setDevengo((double) 0);
                conc71.setDeduccion(redondear(bruto * concM71.getPrecio() / 100));
                try {
                    concR.save(conc71);
                } catch (Exception e) {
                    throw new DataIntegrityViolationException("Error en alta SS");
                }
                ;
            }
            ;
            if ("1".equals(remesa.getTipo_nomina())) {
                deducciones = deducciones + (bruto * concM70.getPrecio() / 100) + (bruto * concM71.getPrecio() / 100);
            } else {
                deducciones = deducciones + (bruto * concM70.getPrecio() / 100);
            }

            // BRUTO. Concepto 98
            ConceptoRecibo conc98 = new ConceptoRecibo();
            conc98.setIdRecibo(recibo);
            conc98.setIdConcepto(concM98);
            conc98.setUnidades((double) 0);
            conc98.setPrecio((double) 0);
            conc98.setDevengo(redondear(bruto));
            conc98.setDeduccion((double) 0);
            try {
                concR.save(conc98);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Error en alta BRUTO");
            }
            ;

            // DESCUENTOS. Concepto 99
            ConceptoRecibo conc99 = new ConceptoRecibo();
            conc99.setIdRecibo(recibo);
            conc99.setIdConcepto(concM99);
            conc99.setUnidades((double) 0);
            conc99.setPrecio((double) 0);
            conc99.setDevengo((double) 0);
            conc99.setDeduccion(redondear(deducciones));
            try {
                concR.save(conc99);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Error en alta deducciones");
            }
            ;
            // Actualizamos los datos del recibo
            recibo.setBruto(redondear(bruto));
            recibo.setDeduccion(redondear(deducciones));
            recibo.setNeto(redondear(bruto - deducciones));
            try {
                recRepository.save(recibo);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Error en alta recibo");
            }
            // acumulados por remesa
            n_empleados++;
            bruto_total += bruto;
            deduccion_total += deducciones;
        }

        // Finalmente se marca la remesa como calculada si ha habido empleados y se
        // guardan los acumulados

        if (n_empleados > 0) {
            remesa.setEstado("2");
            remesa.setBruto(redondear(bruto_total));
            remesa.setDeduccion(redondear(deduccion_total));
            remesa.setNeto(redondear(bruto_total - deduccion_total));
            try {
                remesasRepository.save(remesa);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Error en marcado remesa como calculada");
            }
        }
        ;

    }

    public static double redondear(double valor) {
        BigDecimal bd = new BigDecimal(valor);
        bd = bd.setScale(2, RoundingMode.HALF_EVEN);
        return bd.doubleValue();
    }

    @Transactional
    public void borrarRemesa(Integer remesa_entrada) {

        Optional<Remesa> opcionalRemesa = remesasRepository.findById(remesa_entrada);
        Remesa remesa = opcionalRemesa
                .orElseThrow(() -> new NoSuchElementException("No se encontró la remesa con el ID proporcionado."));

        List<IncidenciaNomina> listaIncidencias = (List<IncidenciaNomina>) incNRepo.findByIdRemesa(remesa);
        // Bucle para recorrer todas las incidencias a actualizar eliminando la remesa
        for (IncidenciaNomina inc : listaIncidencias) {
            inc.setIdRemesa(null);
            try {
                incNRepo.save(inc);
            } catch (Exception e) {
                throw new DataIntegrityViolationException("Error en desmarcado incidencia como calculada");
            }
        }
        // Finalmente se borra la remesa
        try {
            remesasRepository.delete(remesa);
        } catch (Exception e) {
            throw new DataIntegrityViolationException("Error en borrado de remesa");
        }

    }
}
