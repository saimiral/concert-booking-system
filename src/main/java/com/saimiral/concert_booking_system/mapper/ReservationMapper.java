package com.saimiral.concert_booking_system.mapper;

import com.saimiral.concert_booking_system.dto.ReservationResponseDTO;
import com.saimiral.concert_booking_system.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public ReservationResponseDTO toResponse(Reservation reservation) {
        return new ReservationResponseDTO(
                reservation.getId(),
                reservation.getUser().getName(),
                reservation.getConcert().getName(),
                reservation.getNumberOfSeats(),
                reservation.getReservationDateTime(),
                reservation.getStatus()
        );
    }
}