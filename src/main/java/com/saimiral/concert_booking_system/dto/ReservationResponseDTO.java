package com.saimiral.concert_booking_system.dto;

import com.saimiral.concert_booking_system.entity.ReservationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReservationResponseDTO {
    private Long id;
    private String userName;
    private String concertName;
    private int numberOfSeats;
    private LocalDateTime reservationDateTime;
    private ReservationStatus reservationStatus;
}
