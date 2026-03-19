package com.saimiral.concert_booking_system.controller;

import com.saimiral.concert_booking_system.dto.ConcertRequestDTO;
import com.saimiral.concert_booking_system.dto.ConcertResponseDTO;
import com.saimiral.concert_booking_system.dto.ConcertUpdateDTO;
import com.saimiral.concert_booking_system.dto.PagedResponse;
import com.saimiral.concert_booking_system.service.ConcertService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/concerts")
@CrossOrigin(origins = "http://localhost:3000")
public class ConcertController {
    private final ConcertService service;

    public ConcertController(ConcertService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ConcertResponseDTO> createConcert(@Valid @RequestBody ConcertRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createConcert(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConcertResponseDTO> getConcertById(@PathVariable Long id){
        return ResponseEntity.ok(service.getConcertById(id));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<ConcertResponseDTO>> getAllConcerts(@PageableDefault(size = 10, sort = "id") Pageable pageable){
        return ResponseEntity.ok(service.getAllConcerts(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConcert(@PathVariable Long id){
        service.deleteConcert(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ConcertResponseDTO> updateConcert(@PathVariable Long id, @Valid @RequestBody ConcertUpdateDTO dto) {
        return ResponseEntity.ok(service.updateConcert(id, dto));
    }
}
