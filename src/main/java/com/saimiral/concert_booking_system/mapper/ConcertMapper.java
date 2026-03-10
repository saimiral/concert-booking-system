package com.saimiral.concert_booking_system.mapper;

import com.saimiral.concert_booking_system.dto.ConcertResponseDTO;
import com.saimiral.concert_booking_system.entity.Concert;
import org.springframework.stereotype.Component;

@Component
public class ConcertMapper {
    public ConcertResponseDTO toResponse(Concert concert) {
        return new ConcertResponseDTO(
                concert.getId(),
                concert.getName(),
                concert.getVenue(),
                concert.getConcertDateTime(),
                concert.getTotalSeats(),
                concert.getAvailableSeats());
    }
}
