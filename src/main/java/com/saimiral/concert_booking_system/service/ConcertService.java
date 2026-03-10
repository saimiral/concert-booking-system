package com.saimiral.concert_booking_system.service;

import com.saimiral.concert_booking_system.dto.ConcertRequestDTO;
import com.saimiral.concert_booking_system.dto.ConcertResponseDTO;
import com.saimiral.concert_booking_system.dto.PagedResponse;
import com.saimiral.concert_booking_system.entity.Concert;
import com.saimiral.concert_booking_system.mapper.ConcertMapper;
import com.saimiral.concert_booking_system.repository.ConcertRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConcertService {
    private final ConcertRepository concertRepository;
    private final ConcertMapper concertMapper;

    public ConcertService(ConcertRepository concertRepository, ConcertMapper concertMapper) {
        this.concertRepository = concertRepository;
        this.concertMapper = concertMapper;
    }

    public ConcertResponseDTO createConcert(ConcertRequestDTO dto) {
        Concert concert = new Concert(dto.getName(), dto.getVenue(), dto.getConcertDateTime(), dto.getTotalSeats(), dto.getAvailableSeats());

        Concert newConcert = concertRepository.save(concert);

        return concertMapper.toResponse(newConcert);
    }

    public ConcertResponseDTO getConcertById(Long id) {
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        return concertMapper.toResponse(concert);
    }

    public PagedResponse<ConcertResponseDTO> getAllConcerts(Pageable pageable) {
        Page<Concert> page = concertRepository.findAll(pageable);

        List<ConcertResponseDTO> concerts =
                page.getContent()
                    .stream()
                        .map(concertMapper::toResponse)
                        .toList();

        return new PagedResponse<>(
                concerts,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void deleteConcert(Long id) {
        Concert concert = concertRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Concert not found"));

        concertRepository.delete(concert);
    }
}
