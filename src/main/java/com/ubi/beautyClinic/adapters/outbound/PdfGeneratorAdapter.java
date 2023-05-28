package com.ubi.beautyClinic.adapters.outbound;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.ubi.beautyClinic.application.core.domain.Appointment;
import com.ubi.beautyClinic.application.ports.out.PdfGeneratorOutboundPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.time.format.DateTimeFormatter;

@Component
public class PdfGeneratorAdapter implements PdfGeneratorOutboundPort {

    @Override
    public void generateAppointmentPdf(Appointment appointment, OutputStream outputStream) throws DocumentException {

        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, outputStream);

        // Adicionar conteúdo ao cabeçalho
        writer.setPageEvent(new PdfHeaderEvent());

        document.open();

        document.add(new Paragraph("\n"));

        // Adicionar título
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("Appointment Details", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(10);
        document.add(title);

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String formattedDateTime = appointment.getDateTime().format(dateFormatter);

        // Adicionar os dados do Appointment ao PDF
        document.add(new Paragraph("Patient: " + appointment.getPatient().getFullName()));
        document.add(new Paragraph("Service: " + appointment.getService()));
        document.add(new Paragraph("Date and time: " + formattedDateTime));
        document.add(new Paragraph("Professional:: " + appointment.getProfessional().getFullName()));
        document.add(new Paragraph("Status: " + appointment.getStatus()));

        document.close();
    }

    private static class PdfHeaderEvent extends PdfPageEventHelper {

        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            try {
                PdfContentByte canvas = writer.getDirectContent();

                String relativePath = "static/patient/img/logo.png";
                ClassPathResource resource = new ClassPathResource(relativePath);
                String absolutePath = resource.getFile().getAbsolutePath();

                Image logo = Image.getInstance(absolutePath);
                logo.scaleToFit(100, 100);
                logo.setAbsolutePosition(20, document.top() - logo.getScaledHeight() - 10);

                // Adicionar a imagem no cabeçalho
                canvas.addImage(logo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
