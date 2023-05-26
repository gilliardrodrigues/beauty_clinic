package com.ubi.beautyClinic.config;

import com.ubi.beautyClinic.adapters.inbound.request.AppointmentRequest;
import com.ubi.beautyClinic.application.core.domain.Appointment;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig
{
    @Bean
    public ModelMapper modelMapper() {

        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(AppointmentRequest.class, Appointment.class)
                .addMappings(mapping -> mapping.<Long>map(source -> null, Appointment::setId));

        return modelMapper;
    }
}
