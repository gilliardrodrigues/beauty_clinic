package com.ubi.beautyClinic.application.core.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @NotBlank(message = "The street is required!")
    private String street;

    @NotBlank(message = "O número da residência é obrigatório!")
    private String houseNumber;

    @NotBlank(message = "House number is required!")
    private String neighborhood;

    @NotBlank(message = "The city is required!")
    private String city;

    @NotBlank(message = "Status is required!")
    private String state;

    @NotBlank(message = "Country is required!")
    private String country;
}
