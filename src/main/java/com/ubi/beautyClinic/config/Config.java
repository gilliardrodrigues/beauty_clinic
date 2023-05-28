package com.ubi.beautyClinic.config;

import com.ubi.beautyClinic.application.core.usecases.AppointmentUseCase;
import com.ubi.beautyClinic.application.core.usecases.PatientUseCase;
import com.ubi.beautyClinic.application.core.usecases.ProfessionalUseCase;
import com.ubi.beautyClinic.application.ports.out.AppointmentRepositoryOutboundPort;
import com.ubi.beautyClinic.application.ports.out.JavaMailSenderOutboundPort;
import com.ubi.beautyClinic.application.ports.out.PatientRepositoryOutboundPort;
import com.ubi.beautyClinic.application.ports.out.ProfessionalRepositoryOutboundPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {

    @Bean
    public ProfessionalUseCase professionalUseCase(ProfessionalRepositoryOutboundPort outboundPort) {

        return new ProfessionalUseCase(outboundPort);
    }

    @Bean
    public PatientUseCase patientUseCase(PatientRepositoryOutboundPort outboundPort, PasswordEncoder bcryptEncoder) {

        return new PatientUseCase(outboundPort);
    }

    @Bean
    public AppointmentUseCase appointmentUseCase(AppointmentRepositoryOutboundPort outboundPort, PatientRepositoryOutboundPort patientRepositoryOutboundPort, ProfessionalRepositoryOutboundPort professionalRepositoryOutboundPort, JavaMailSenderOutboundPort javaMailSenderOutboundPort) {

        return new AppointmentUseCase(outboundPort, patientRepositoryOutboundPort, professionalRepositoryOutboundPort, javaMailSenderOutboundPort);
    }
}
