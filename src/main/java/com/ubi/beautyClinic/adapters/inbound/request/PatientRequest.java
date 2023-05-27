package com.ubi.beautyClinic.adapters.inbound.request;

import com.ubi.beautyClinic.application.core.domain.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {

    @NotBlank(message = "Full name is required!")
    private String fullName;

    @NotBlank(message = "Email is required!")
    @Email(message = "The 'email' field must be a valid email address!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "The password must contain at least 8 characters, one special character, one lowercase letter, one uppercase letter and one number.")
    private String password;

    @NotNull(message = "Date of birth is required!")
    @PastOrPresent(message = "Date of birth cannot be a date in the future!")
    private OffsetDateTime birthDate;

    @NotNull(message = "Gender is required!")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Valid
    private AddressRequest address;

    @Valid
    private PhoneNumberRequest phoneNumber;

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

