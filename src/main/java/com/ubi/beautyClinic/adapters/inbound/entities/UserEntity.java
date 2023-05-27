package com.ubi.beautyClinic.adapters.inbound.entities;

import com.ubi.beautyClinic.application.core.domain.Address;
import com.ubi.beautyClinic.application.core.domain.PhoneNumber;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Full name is required!")
    private String fullName;

    @NotBlank(message = "Email is required!")
    @Email(message = "The 'email' field must be a valid email address!")
    private String email;

    @NotBlank(message = "Password is required!")
    @Pattern(regexp = "^(?=.*[!@#$%^&*])(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "The password must contain at least 8 characters, one special character, one lowercase letter, one uppercase letter and one number.")
    private String password;

    @Valid
    private Address address;

    @Valid
    private PhoneNumber phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserEntity that = (UserEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
