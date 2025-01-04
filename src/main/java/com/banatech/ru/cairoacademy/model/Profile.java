package com.banatech.ru.cairoacademy.model;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy= InheritanceType.JOINED)
@DiscriminatorColumn(name = "title")
public class Person extends EntityId{
    private String firstname;
    private String lastname;
    private LocalDate dateOfBirth;
    private String phone;
    private String document;
    private String nationality;
    private String country;
    private String city;
    private String address;
    private String profession;
}
