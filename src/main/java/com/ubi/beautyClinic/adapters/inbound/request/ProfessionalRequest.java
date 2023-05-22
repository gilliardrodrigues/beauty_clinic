package com.ubi.beautyClinic.adapters.inbound.request;

import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalRequest {

    @NotBlank(message = "O nome completo é obrigatório")
    private String fullName;

    @NotBlank(message = "O email é obrigatório")
    @Email(message = "O campo 'email' deve ser um endereço de email válido.")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "A senha deve conter pelo menos 8 caracteres, um caractere especial, uma letra minúscula, uma letra maiúscula e um número.")
    private String password;

    @NotBlank(message = "O resumo é obrigatório.")
    private String summary;

    @Valid
    private AddressRequest address;

    @Valid
    private PhoneNumberRequest phoneNumber;

    @NotEmpty(message = "A lista de serviços oferecidos não pode estar vazia")
    private List<ServiceEnum> offeredServices;

    @NotNull(message = "O preço da consulta é obrigatório")
    @DecimalMin(value = "0.0", inclusive = false, message = "O preço da consulta deve ser maior que zero")
    private BigDecimal consultationPrice;

    @Getter
    @Setter
    public static class AddressRequest {

        @NotBlank(message = "A rua é obrigatória")
        private String street;

        @NotBlank(message = "O número da residência é obrigatório")
        private String houseNumber;

        @NotBlank(message = "O bairro é obrigatório")
        private String neighborhood;

        @NotBlank(message = "A cidade é obrigatória")
        private String city;

        @NotBlank(message = "O estado é obrigatório")
        private String state;

        @NotBlank(message = "O país é obrigatório")
        private String country;
    }
    @Getter
    @Setter
    public static class PhoneNumberRequest {

        @NotBlank(message = "O código do país é obrigatório")
        @Pattern(regexp = "\\+[0-9]+", message = "O campo 'countryCode' deve estar no formato '+123'.")
        private String countryCode;

        @Pattern(regexp = "[0-9]*", message = "O campo 'areaCode' deve conter apenas números.")
        private String areaCode;

        @NotBlank(message = "O número de telefone é obrigatório")
        @Pattern(regexp = "[0-9]+", message = "O campo 'number' deve ser numérico.")
        private String number;
    }
}
