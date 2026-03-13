package com.saimiral.concert_booking_system.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
@Getter
@Setter
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime reservationDateTime;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
    private int numberOfSeats;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "concert_id")
    private Concert concert;

//    public Reservation(User user, Concert concert, int numberOfSeats) {
//        this.user.setId(user.getId());
//        this.concert.setId(concert.getId());
//        this.numberOfSeats = numberOfSeats;
//    }
}
