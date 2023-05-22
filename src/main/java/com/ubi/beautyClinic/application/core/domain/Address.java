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

    @NotBlank(message = "O campo 'street' é obrigatório.")
    private String street;

    @NotBlank(message = "O campo 'houseNumber' é obrigatório.")
    private String houseNumber;

    @NotBlank(message = "O campo 'neighborhood' é obrigatório.")
    private String neighborhood;

    @NotBlank(message = "O campo 'city' é obrigatório.")
    private String city;

    @NotBlank(message = "O campo 'state' é obrigatório.")
    private String state;

    @NotBlank(message = "O campo 'country' é obrigatório.")
    private String country;
}
