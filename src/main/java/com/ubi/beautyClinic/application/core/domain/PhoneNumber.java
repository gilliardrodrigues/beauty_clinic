package com.ubi.beautyClinic.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumber {

    private String countryCode;
    private String areaCode;
    private String number;
}
