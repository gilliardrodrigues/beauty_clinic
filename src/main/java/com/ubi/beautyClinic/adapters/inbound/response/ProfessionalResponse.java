package com.ubi.beautyClinic.adapters.inbound.response;

import com.ubi.beautyClinic.application.core.domain.ServiceEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class ProfessionalResponse {

    private Long id;
    private String fullName;
    private String email;
    private AddressResponse address;
    private PhoneNumberResponse phoneNumber;
    private String summary;
    private List<ServiceEnum> offeredServices;
    private BigDecimal consultationPrice;

    // Classe interna para o mapeamento do Address
    @Getter
    @Setter
    public static class AddressResponse {
        private String street;
        private String houseNumber;
        private String neighborhood;
        private String city;
        private String state;
        private String country;
    }

    // Classe interna para o mapeamento do PhoneNumber
    @Getter
    @Setter
    public static class PhoneNumberResponse {
        private String countryCode;
        private String areaCode;
        private String number;
    }
}
