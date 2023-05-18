package com.ubi.beautyClinic.application.core.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
}
