package es.upm.dit.isst.tfg.tfgwebapp.util;

import java.awt.Color;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

        PdfPTable tablaTitulo = new PdfPTable(1);
        PdfPTable tablaPie = new PdfPTable(1);

        Font fuenteTitulo = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20, Color.BLACK);
        Font fuenteTitCols = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Color.BLACK);
        Font fuenteCeldas = FontFactory.getFont(FontFactory.COURIER, 12, Color.BLACK);

        document.setPageSize(PageSize.A4.rotate());
        document.setMargins(-20, -20, 30, 20);
        document.open();

        PdfPCell celda = null;
        celda = new PdfPCell(new Phrase("REMESA DE NÓMINA", fuenteTitulo));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(20);
        tablaTitulo.addCell(celda);
        tablaTitulo.setSpacingAfter(30);

        PdfPTable tablaRecibos = new PdfPTable(5);
        tablaRecibos.setWidths(new float[] { 2f, 6f, 6f, 3f, 3f });

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        celda = new PdfPCell(new Phrase("Recibo", fuenteTitCols));
        celda.setBackgroundColor((Color.lightGray));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaRecibos.addCell(celda);

        celda = new PdfPCell(new Phrase("IBAN", fuenteTitCols));
        celda.setBackgroundColor((Color.lightGray));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaRecibos.addCell(celda);

        celda = new PdfPCell(new Phrase("Perceptor", fuenteTitCols));
        celda.setBackgroundColor((Color.lightGray));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaRecibos.addCell(celda);

        celda = new PdfPCell(new Phrase("Importe", fuenteTitCols));
        celda.setBackgroundColor((Color.lightGray));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaRecibos.addCell(celda);

        celda = new PdfPCell(new Phrase("Fecha", fuenteTitCols));
        celda.setBackgroundColor((Color.lightGray));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setPadding(10);
        tablaRecibos.addCell(celda);

        double acumulado = 0;

        for (Recibo recibo : lista_recibos) {
            celda = new PdfPCell(new Phrase(recibo.getIdRecibo().toString(), fuenteCeldas));
            celda.setPadding(5);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaRecibos.addCell(celda);

            // celda = new PdfPCell(new
            // Phrase(recibo.getIdEmpleado().getIBAN(),fuenteCeldas));
            celda = new PdfPCell(new Phrase(recibo.getIBAN(), fuenteCeldas));
            celda.setPadding(5);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaRecibos.addCell(celda);

            celda = new PdfPCell(
                    new Phrase(recibo.getIdEmpleado().getApellido_1() + " " + recibo.getIdEmpleado().getApellido_2()
                            + ", " + recibo.getIdEmpleado().getNombre(), fuenteCeldas));
            celda.setPadding(5);
            tablaRecibos.addCell(celda);

            acumulado = acumulado + recibo.getNeto();

            String formattedNeto = currencyFormatter.format(recibo.getNeto());

            celda = new PdfPCell(new Phrase(formattedNeto, fuenteCeldas));
            celda.setPadding(5);
            celda.setHorizontalAlignment(Element.ALIGN_RIGHT);
            tablaRecibos.addCell(celda);

            String fechaFormateada = LocalDate.now().format(formatter);
            celda = new PdfPCell(new Phrase(fechaFormateada, fuenteCeldas));
            //celda = new PdfPCell(new Phrase(LocalDate.now().toString(), fuenteCeldas));
            celda.setPadding(5);
            celda.setHorizontalAlignment(Element.ALIGN_CENTER);
            tablaRecibos.addCell(celda);
        }
        ;

        tablaRecibos.setSpacingAfter(30);

        String formattedNeto = currencyFormatter.format(acumulado);

        celda = new PdfPCell(new Phrase("TOTAL REMESA: " + formattedNeto, fuenteTitulo));
        celda.setHorizontalAlignment(Element.ALIGN_CENTER);
        celda.setVerticalAlignment(Element.ALIGN_CENTER);
        celda.setBackgroundColor((Color.lightGray));
        celda.setPadding(20);
        tablaPie.addCell(celda);

        document.add(tablaTitulo);
        document.add(tablaRecibos);
        document.add(tablaPie);
    }
}
