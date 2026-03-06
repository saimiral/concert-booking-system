package com.saimiral.concert_booking_system.repository;

import com.saimiral.concert_booking_system.entity.Concert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
}
