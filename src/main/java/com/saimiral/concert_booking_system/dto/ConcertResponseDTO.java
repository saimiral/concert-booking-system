package com.saimiral.concert_booking_system.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ConcertResponseDTO {
    private Long id;
    private String name;
    private String venue;
    private LocalDateTime concertDateTime;
    private int totalSeats;
    private int availableSeats;
}
