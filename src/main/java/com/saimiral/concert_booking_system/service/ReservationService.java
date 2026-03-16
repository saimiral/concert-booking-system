package com.saimiral.concert_booking_system.service;

import com.saimiral.concert_booking_system.dto.PagedResponse;
import com.saimiral.concert_booking_system.dto.ReservationRequestDTO;
import com.saimiral.concert_booking_system.dto.ReservationResponseDTO;
import com.saimiral.concert_booking_system.entity.Concert;
import com.saimiral.concert_booking_system.entity.Reservation;
import com.saimiral.concert_booking_system.entity.ReservationStatus;
import com.saimiral.concert_booking_system.entity.User;
import com.saimiral.concert_booking_system.exception.InsufficientSeatsException;
import com.saimiral.concert_booking_system.exception.ResourceNotFoundException;
import com.saimiral.concert_booking_system.exception.UserNotFoundException;
import com.saimiral.concert_booking_system.mapper.ReservationMapper;
import com.saimiral.concert_booking_system.repository.ConcertRepository;
import com.saimiral.concert_booking_system.repository.ReservationRepository;
import com.saimiral.concert_booking_system.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final UserRepository userRepository;
    private final ConcertRepository concertRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper, UserRepository userRepository, ConcertRepository concertRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.userRepository = userRepository;
        this.concertRepository = concertRepository;
    }

    @Transactional
    public ReservationResponseDTO createReservation(ReservationRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Concert concert = concertRepository.findById(dto.getConcertId())
                .orElseThrow(() -> new ResourceNotFoundException("Concert not found"));

        if(concert.getAvailableSeats() < dto.getNumberOfSeats()) {
            throw new InsufficientSeatsException("Not enough seats available");
        }

        concert.setAvailableSeats(concert.getAvailableSeats() - dto.getNumberOfSeats());
        concertRepository.save(concert);

        Reservation reservation =  new Reservation();
        reservation.setUser(user);
        reservation.setConcert(concert);
        reservation.setNumberOfSeats(dto.getNumberOfSeats());
        reservation.setReservationDateTime(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.CONFIRMED);

        return reservationMapper.toResponse(reservationRepository.save(reservation));
    }

    public ReservationResponseDTO getReservationById(long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        return reservationMapper.toResponse(reservation);
    }

    public PagedResponse<ReservationResponseDTO> getAllReservations(Pageable pageable) {
        Page<Reservation> page = reservationRepository.findAll(pageable);

        List<ReservationResponseDTO> reservations = page
                .getContent()
                .stream()
                .map(reservationMapper::toResponse)
                .toList();

        return new PagedResponse<>(
            reservations,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void cancelReservation(Long id){
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reservation not found"));

        reservationRepository.delete(reservation);
    }
}
