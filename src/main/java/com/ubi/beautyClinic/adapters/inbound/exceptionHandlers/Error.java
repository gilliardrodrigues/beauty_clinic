package com.ubi.beautyClinic.adapters.inbound.exceptionHandlers;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class Error {

    private Integer status;
    private OffsetDateTime dateTime;
    private String title;
    private List<Field> fields;
}