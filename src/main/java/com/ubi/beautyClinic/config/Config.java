package com.ubi.beautyClinic.config;

import com.ubi.beautyClinic.application.core.usecases.AppointmentUseCase;
import com.ubi.beautyClinic.application.core.usecases.PatientUseCase;
import com.ubi.beautyClinic.application.core.usecases.ProfessionalUseCase;
import com.ubi.beautyClinic.application.ports.out.AppointmentRepositoryOutboundPort;
import com.ubi.beautyClinic.application.ports.out.PatientRepositoryOutboundPort;
import com.ubi.beautyClinic.application.ports.out.ProfessionalRepositoryOutboundPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class Config {

    @Bean
    public ProfessionalUseCase professionalUseCase(ProfessionalRepositoryOutboundPort outboundPort, PasswordEncoder bcryptEncoder) {

        return new ProfessionalUseCase(outboundPort, bcryptEncoder);
    }

    @Bean
    public PatientUseCase patientUseCase(PatientRepositoryOutboundPort outboundPort, PasswordEncoder bcryptEncoder) {

        return new PatientUseCase(outboundPort, bcryptEncoder);
    }

    @Bean
    public AppointmentUseCase appointmentUseCase(AppointmentRepositoryOutboundPort outboundPort, PatientRepositoryOutboundPort patientRepositoryOutboundPort, ProfessionalRepositoryOutboundPort professionalRepositoryOutboundPort) {

        return new AppointmentUseCase(outboundPort, patientRepositoryOutboundPort, professionalRepositoryOutboundPort);
    }
}
