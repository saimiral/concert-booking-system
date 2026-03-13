package com.saimiral.concert_booking_system.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationRequestDTO {
    @NotNull
    private Long userId;
    @NotNull
    private Long concertId;
    @Min(1)
    private int numberOfSeats;
}
