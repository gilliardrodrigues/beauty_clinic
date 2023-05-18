package com.ubi.beautyClinic.application.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String fullName;
    private String email;
    private String password;
    private Address address;
    private PhoneNumber phoneNumber;
}
