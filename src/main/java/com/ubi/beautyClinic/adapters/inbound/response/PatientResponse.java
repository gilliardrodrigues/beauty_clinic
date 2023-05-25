package com.ubi.beautyClinic.adapters.inbound.response;

import com.ubi.beautyClinic.application.core.domain.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
public class PatientResponse {

    private Long id;
    private String fullName;
    private String email;
    private AddressResponse address;
    private PhoneNumberResponse phoneNumber;
    private OffsetDateTime birthDate;
    private Gender gender;

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

