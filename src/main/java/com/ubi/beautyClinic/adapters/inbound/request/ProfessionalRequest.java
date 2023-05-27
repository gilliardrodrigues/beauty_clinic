package com.ubi.beautyClinic.adapters.inbound.request;

import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalRequest {

    @NotBlank(message = "Full name is required!")
    private String fullName;

    @NotBlank(message = "Email is required!")
    @Email(message = "The 'email' field must be a valid email address!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "The password must contain at least 8 characters, one special character, one lowercase letter, one uppercase letter and one number.")
    private String password;

    @NotBlank(message = "Summary is required!")
    private String summary;

    @Valid
    private AddressRequest address;

    @Valid
    private PhoneNumberRequest phoneNumber;

    @NotEmpty(message = "The list of services offered cannot be empty!")
    @Enumerated(EnumType.STRING)
    private List<ServiceEnum> offeredServices;

    @NotNull(message = "Consultation price is required!")
    @DecimalMin(value = "0.0", inclusive = false, message = "The consultation price must be greater than zero!")
    private BigDecimal consultationPrice;

    @Getter
    @Setter
    public static class AddressRequest {

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
    @Getter
    @Setter
    public static class PhoneNumberRequest {

        @NotBlank(message = "Country code is required!")
        @Pattern(regexp = "\\+[0-9]+", message = "The 'countryCode' field must be in the format '+123'!")
        private String countryCode;

        @Pattern(regexp = "[0-9]*", message = "The 'areaCode' field must only contain numbers!")
        private String areaCode;

        @NotBlank(message = "Phone number is required!")
        @Pattern(regexp = "[0-9]+", message = "The number cannot contain non-numeric characters!")
        private String number;
    }
}
