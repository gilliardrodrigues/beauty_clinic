package com.ubi.beautyClinic.config;

import com.ubi.beautyClinic.application.core.usecases.ProfessionalUseCase;
import com.ubi.beautyClinic.application.ports.out.ProfessionalRepositoryOutboundPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Bean
    public ProfessionalUseCase professionalUseCase(ProfessionalRepositoryOutboundPort outboundPort) {

        return new ProfessionalUseCase(outboundPort);
    }
}
