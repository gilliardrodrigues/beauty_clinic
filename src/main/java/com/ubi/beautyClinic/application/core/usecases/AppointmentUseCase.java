package com.ubi.beautyClinic.application.core.usecases;

import com.itextpdf.text.DocumentException;
import com.ubi.beautyClinic.application.core.domain.Appointment;
import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import com.ubi.beautyClinic.application.core.domain.Status;
import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.ports.in.AppointmentUseCaseInboundPort;
import com.ubi.beautyClinic.application.ports.out.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

public class AppointmentUseCase implements AppointmentUseCaseInboundPort {

    private final AppointmentRepositoryOutboundPort outboundPort;
    private final PatientRepositoryOutboundPort patientRepositoryOutboundPort;
    private final ProfessionalRepositoryOutboundPort professionalRepositoryOutboundPort;
    private final JavaMailSenderOutboundPort javaMailSenderOutboundPort;
    private final PdfGeneratorOutboundPort pdfGeneratorOutboundPort;

    public AppointmentUseCase(AppointmentRepositoryOutboundPort outboundPort, PatientRepositoryOutboundPort patientRepositoryOutboundPort, ProfessionalRepositoryOutboundPort professionalRepositoryOutboundPort, JavaMailSenderOutboundPort javaMailSenderOutboundPort, PdfGeneratorOutboundPort pdfGeneratorOutboundPort) {
        this.outboundPort = outboundPort;
        this.patientRepositoryOutboundPort = patientRepositoryOutboundPort;
        this.professionalRepositoryOutboundPort = professionalRepositoryOutboundPort;
        this.javaMailSenderOutboundPort = javaMailSenderOutboundPort;
        this.pdfGeneratorOutboundPort = pdfGeneratorOutboundPort;
    }

    @Override
    public void generateAppointmentAsPdf(Long id, OutputStream outputStream) throws DocumentException, IOException {

        var appointment = outboundPort.findById(id);
        pdfGeneratorOutboundPort.generateAppointmentPdf(appointment, outputStream);
    }

    @Override
    @Transactional
    public Appointment acceptAppointment(Long id) {

        var appointment = outboundPort.findById(id);
        var appointments = listAppointmentsForLoggedUser();

        if(appointments.contains(appointment)) {
            if(appointment.getStatus().equals(Status.REQUESTED))
                appointment.setStatus(Status.TO_ACCOMPLISH);

            var savedAppointment = outboundPort.save(appointment);
            var patient = savedAppointment.getPatient();

            var subjectToPatientEmail = "Appointment request update!";
            var contentToPatientEmail = "Hello " + patient.getFullName().split(" ")[0] +
                    "!\nYour consultation request has been accepted!\n" +
                    "Access your account on the Beauty Clinic platform for more details.";

            sendEmail(patient.getEmail(), subjectToPatientEmail, contentToPatientEmail);
            return savedAppointment;
        }
        else {
            throw new BusinessLogicException("There is no appointment with this id in your list!");
        }
    }

    @Override
    @Transactional
    public Appointment refuseAppointment(Long id) {

        var appointment = outboundPort.findById(id);
        var appointments = listAppointmentsForLoggedUser();

        if(appointments.contains(appointment)) {
            if (appointment.getStatus().equals(Status.REQUESTED))
                appointment.setStatus(Status.REFUSED);

            var savedAppointment = outboundPort.save(appointment);
            var patient = savedAppointment.getPatient();

            var subjectToPatientEmail = "Appointment request update!";
            var contentToPatientEmail = "Hello " + patient.getFullName().split(" ")[0] +
                    "\nUnfortunately, the professional had to refuse his consultation request!\n" +
                    "But don't be discouraged, access your account the Beauty Clinic platform " +
                    "and try to schedule it for another time or with another professional!";

            sendEmail(patient.getEmail(), subjectToPatientEmail, contentToPatientEmail);

            return savedAppointment;
        }
        else {
            throw new BusinessLogicException("There is no appointment with this id in your list!");
        }
    }

    @Override
    @Transactional
    public Appointment requestAppointment(Appointment appointment) throws BusinessLogicException {

        var loggedPatientEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        var loggedPatient = patientRepositoryOutboundPort.findByEmail(loggedPatientEmail);
        var professional = professionalRepositoryOutboundPort.findById(appointment.getProfessional().getId());

        if(!professional.getOfferedServices().contains(appointment.getService()))
            throw new BusinessLogicException("The selected professional does not offer this service!");

        appointment.setProfessional(professional);
        appointment.setPatient(loggedPatient);
        appointment.setStatus(Status.REQUESTED);
        appointment.setRating(null);
        appointment.setDateTime(appointment.getDateTime().withSecond(0).withNano(0));

        var savedAppointment = outboundPort.save(appointment);

        var subjectToProfessionalEmail = "New appointment request!";
        var contentToProfessionalEmail = "Hello " + professional.getFullName().split(" ")[0] +
                    "!\nYou have received a new appointment request!\n " +
                    "Access your account on the Beauty Clinic platform to accept or decline it.";
        sendEmail(professional.getEmail(), subjectToProfessionalEmail, contentToProfessionalEmail);

        var subjectToPatientEmail = "Appointment request successful!";
        var contentToPatientEmail = "Hello " + loggedPatient.getFullName().split(" ")[0] +
                    "!\nYour appointment request was successfully made!\n " +
                    "Now all you have to do is wait for the professional to see it and accept it or not, " +
                    "depending on your availability.";
        sendEmail(loggedPatientEmail, subjectToPatientEmail, contentToPatientEmail);

        return savedAppointment;
    }

    private void sendEmail(String receiverEmail, String subject, String content) {

        content = content + "\n\n Beauty Clinic\n[Do not reply to this email]";
        javaMailSenderOutboundPort.sendEmail(receiverEmail, subject, content);
    }

    @Override
    @Transactional
    public void deleteAppointment(Long id) {

        outboundPort.delete(id);
    }

    @Override
    public Appointment getAppointmentById(Long id) {

        return outboundPort.findById(id);
    }

    private void updateAccomplishedAppointments() {

        var currentDateTime = OffsetDateTime.now();
        var appointments = outboundPort.findAll();

        appointments.stream()
                    .filter(appointment -> appointment.getStatus().equals(Status.TO_ACCOMPLISH) && appointment.getDateTime().isBefore(currentDateTime))
                    .forEach(appointment -> {
                        appointment.setStatus(Status.ACCOMPLISHED);
                        outboundPort.save(appointment);
                    });
    }

    @Override
    public List<Appointment> listAppointmentsForLoggedUser() {

        var loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        var loggedUserAuthorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        updateAccomplishedAppointments();

        if(loggedUserAuthorities.contains(new SimpleGrantedAuthority("PATIENT"))) {
            var patient = patientRepositoryOutboundPort.findByEmail(loggedUserEmail);
            return outboundPort.findByPatient(patient);
        }
        if(loggedUserAuthorities.contains(new SimpleGrantedAuthority("PROFESSIONAL"))) {
            var professional = professionalRepositoryOutboundPort.findByEmail(loggedUserEmail);
            return outboundPort.findByProfessional(professional);
        }
        return new ArrayList<>();
    }

    @Override
    public List<Appointment> listAppointmentsByDate(OffsetDateTime date) {

        OffsetDateTime startOfDay = date.with(LocalTime.MIDNIGHT).withOffsetSameInstant(ZoneOffset.ofHours(-3));
        OffsetDateTime endOfDay = date.with(LocalTime.MAX).withOffsetSameInstant(ZoneOffset.ofHours(-3));

        return outboundPort.findByDate(startOfDay, endOfDay);
    }

    @Override
    public List<Appointment> listAppointmentsByDateTime(OffsetDateTime datetime) {

        return outboundPort.findByDateTime(datetime);
    }

    @Override
    public List<Appointment> listAppointmentsByService(ServiceEnum service) {

        return outboundPort.findByService(service);
    }

    @Override
    public List<Appointment> listAppointmentsByStatus(Status status) {

        return outboundPort.findByStatus(status);
    }

    @Override
    public List<Appointment> listAppointmentsByRating(Integer rating) {

        return outboundPort.findByRating(rating);
    }
}
