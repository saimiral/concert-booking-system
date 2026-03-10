package com.saimiral.concert_booking_system.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "concerts")
@Getter
@Setter
@NoArgsConstructor
public class Concert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String venue;
    private LocalDateTime concertDateTime;
    private int totalSeats;
    private int availableSeats;

    public Concert(String name, String venue, LocalDateTime concertDateTime, int totalSeats, int availableSeats) {
        this.name = name;
        this.venue = venue;
        this.concertDateTime = concertDateTime;
        this.totalSeats = totalSeats;
        this.availableSeats = availableSeats;
    }
}
