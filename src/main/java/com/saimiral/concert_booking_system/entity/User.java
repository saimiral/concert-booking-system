package com.saimiral.concert_booking_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDate birthDate;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    public User(String name, LocalDate birthDate, String email) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
    }
}
