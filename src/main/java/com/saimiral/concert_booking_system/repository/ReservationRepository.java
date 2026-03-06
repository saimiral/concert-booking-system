package com.saimiral.concert_booking_system.repository;

import com.saimiral.concert_booking_system.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
