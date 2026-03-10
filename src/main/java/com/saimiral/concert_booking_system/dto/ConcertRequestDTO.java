package com.saimiral.concert_booking_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ConcertRequestDTO {
    @NotBlank
    private String name;
    private String venue;
    private LocalDateTime concertDateTime;
    @Min(1)
    private int totalSeats;
    private int availableSeats;
}
