package com.ubi.beautyClinic.application.ports.out;

import com.itextpdf.text.DocumentException;
import com.ubi.beautyClinic.application.core.domain.Appointment;

import java.io.IOException;
import java.io.OutputStream;

public interface PdfGeneratorOutboundPort {

    void generateAppointmentPdf(Appointment appointment, OutputStream outputStream) throws IOException, DocumentException;
}
