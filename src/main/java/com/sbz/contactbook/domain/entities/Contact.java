package com.sbz.contactbook.domain.entities;


import com.sbz.contactbook.utils.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="contact", uniqueConstraints = {@UniqueConstraint(columnNames = {"firstName", "lastName"})})
public class Contact {
    @Id
    @SequenceGenerator(
            name= "contact_sequence",
            sequenceName = "contact_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_sequence")
    private Long id;

    @NotNull(message = "rol is required")
    @Enumerated(EnumType.ORDINAL)
    private Rol rol;

    @NotNull(message = "firstName is required")
    @NotBlank
    private String firstName;

    @NotNull(message = "lastName is required")
    @NotBlank
    private String lastName;

    @NotNull(message = "address is required")
    @NotBlank
    private String address;

    @Column(unique = true)
    @NotNull(message = "phone is required")
    @NotBlank
    private String phone;

    private String companyName;
    private String city;
    private String website;

    public boolean checkCompanyRol() {
        boolean hasCompanyName = (this.getCompanyName() != null) && !this.getCompanyName().isBlank();
        boolean hasCity = (this.getCity() != null) && !this.getCity().isBlank();
        boolean hasWebSite = (this.getWebsite() != null) && !this.getWebsite().isBlank();

        return hasCompanyName && hasCity && hasWebSite;
    }

}
