package es.upm.dit.isst.tfg.tfgwebapp.util;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Phrase;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.CMYKColor;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import es.upm.dit.isst.tfg.tfgwebapp.model.Recibo;

@Component("recibos_remesa")
public class EmitirRemesaPDF extends AbstractPdfView {

    @Override
    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter writer,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        List<Recibo> lista_recibos = (List<Recibo>) model.get("recibos");

        document.setPageSize(PageSize.A4.rotate());
        document.open();

        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPTable tablaPie = new PdfPTable(1);
              
        Font fuenteTitulo = FontFactory.getFont("Helvetica", 16, CMYKColor.BLUE);

        PdfPCell celdaTit = null;
        celdaTit = new PdfPCell(new Phrase("REMESA DE NÓMINA", fuenteTitulo));
        celdaTit.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaTit.setVerticalAlignment(Element.ALIGN_CENTER);
        celdaTit.setPadding(20);
        tablaTitulo.addCell(celdaTit);
        tablaTitulo.setSpacingAfter(30);
        
        Font ft = FontFactory.getFont("Helvetica", 9, CMYKColor.BLUE);
        
        PdfPTable tablaRecibos = new PdfPTable(7);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        
        double[] acumulado = {0,0};

        lista_recibos.forEach(recibo -> {
            tablaRecibos.addCell(recibo.getIdRecibo().toString());
            tablaRecibos.addCell(recibo.getIdEmpleado().getIBAN());
            tablaRecibos.addCell(recibo.getIdEmpleado().getApellido_1());
            tablaRecibos.addCell(recibo.getIdEmpleado().getApellido_2());
            tablaRecibos.addCell(recibo.getIdEmpleado().getNombre());
            acumulado[0] = acumulado[0] + recibo.getNeto();
            String formattedNeto = currencyFormatter.format(recibo.getNeto());
            tablaRecibos.addCell(formattedNeto);
            tablaRecibos.addCell(LocalDate.now().toString());
        });
        tablaRecibos.setSpacingAfter(30);

        String formattedNeto = currencyFormatter.format(acumulado[0]);
        PdfPCell celdaPie = null;
        celdaPie = new PdfPCell(new Phrase("TOTAL REMESA: " + formattedNeto , fuenteTitulo));
        celdaPie.setHorizontalAlignment(Element.ALIGN_CENTER);
        celdaPie.setVerticalAlignment(Element.ALIGN_CENTER);
        celdaPie.setPadding(20);
        tablaPie.addCell(celdaPie);

        document.add(tablaTitulo);
        document.add(tablaRecibos);
        document.add(tablaPie);
    }
}
