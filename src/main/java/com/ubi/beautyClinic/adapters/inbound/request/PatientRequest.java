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

    @NotBlank(message = "O nome completo é obrigatório!")
    private String fullName;

    @NotBlank(message = "O email é obrigatório!")
    @Email(message = "O campo 'email' deve ser um endereço de email válido!")
    private String email;

    @NotBlank(message = "A senha é obrigatória!")
    @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "A senha deve conter pelo menos 8 caracteres, um caractere especial, uma letra minúscula, uma letra maiúscula e um número.")
    private String password;

    @NotNull(message = "A data de nascimento é obrigatória!")
    @PastOrPresent(message = "A data de nascimento não pode ser uma data futura!")
    private OffsetDateTime birthDate;

    @NotNull(message = "O gênero é obrigatório!")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Valid
    private AddressRequest address;

    @Valid
    private PhoneNumberRequest phoneNumber;

    @Getter
    @Setter
    public static class AddressRequest {

        @NotBlank(message = "A rua é obrigatória!")
        private String street;

        @NotBlank(message = "O número da residência é obrigatório!")
        private String houseNumber;

        @NotBlank(message = "O bairro é obrigatório!")
        private String neighborhood;

        @NotBlank(message = "A cidade é obrigatória!")
        private String city;

        @NotBlank(message = "O estado é obrigatório!")
        private String state;

        @NotBlank(message = "O país é obrigatório!")
        private String country;
    }
    @Getter
    @Setter
    public static class PhoneNumberRequest {

        @NotBlank(message = "O código do país é obrigatório!")
        @Pattern(regexp = "\\+[0-9]+", message = "O campo 'countryCode' deve estar no formato '+123'!")
        private String countryCode;

        @Pattern(regexp = "[0-9]*", message = "O campo 'areaCode' deve conter apenas números!")
        private String areaCode;

        @NotBlank(message = "O número de telefone é obrigatório!")
        @Pattern(regexp = "[0-9]+", message = "O número não pode conter caracteres que não sejam numéricos!")
        private String number;
    }
}

