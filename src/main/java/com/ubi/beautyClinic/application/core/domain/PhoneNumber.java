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

    @NotBlank(message = "Country code is required!")
    @Pattern(regexp = "\\+[0-9]+", message = "The 'countryCode' field must be in the format '+123'!")
    private String countryCode;

    @Pattern(regexp = "[0-9]*", message = "The 'areaCode' field must only contain numbers!")
    private String areaCode;

    @NotBlank(message = "Phone number is required!")
    @Pattern(regexp = "[0-9]+", message = "The number cannot contain non-numeric characters!")
    private String number;
}
