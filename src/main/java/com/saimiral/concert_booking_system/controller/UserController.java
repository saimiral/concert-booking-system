package com.saimiral.concert_booking_system.controller;

import com.saimiral.concert_booking_system.dto.PagedResponse;
import com.saimiral.concert_booking_system.dto.UserRequestDTO;
import com.saimiral.concert_booking_system.dto.UserResponseDTO;
import com.saimiral.concert_booking_system.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public UserResponseDTO createUser(@Valid @RequestBody UserRequestDTO dto) {
        return service.createUser(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(service.getUserById(id));
    }

    @GetMapping
    public ResponseEntity<PagedResponse<UserResponseDTO>> getAllUsers(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return ResponseEntity.ok(service.getAllUsers(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
