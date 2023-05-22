package com.ubi.beautyClinic.application.core.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneNumber {

    @NotBlank(message = "O campo 'countryCode' é obrigatório.")
    @Pattern(regexp = "\\+[0-9]+", message = "O campo 'countryCode' deve estar no formato '+123'.")
    private String countryCode;

    @Pattern(regexp = "[0-9]*", message = "O campo 'areaCode' deve conter apenas números.")
    private String areaCode;

    @NotBlank(message = "O campo 'number' é obrigatório.")
    @Pattern(regexp = "[0-9]+", message = "O campo 'number' deve ser numérico.")
    private String number;
}
