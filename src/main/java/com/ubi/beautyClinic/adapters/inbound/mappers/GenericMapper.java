package com.ubi.beautyClinic.adapters.inbound.mappers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class GenericMapper {

    @Autowired
    private ModelMapper modelMapper;

    public <S, D> D mapTo(S source, Class<D> destClass) {

        return this.modelMapper.map(source, destClass);
    }

    public <S, D> List<D> mapToList(List<S> source, Type destClass) {

        return this.modelMapper.map(source, destClass);
    }
}
