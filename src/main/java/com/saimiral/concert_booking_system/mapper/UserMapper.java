package com.saimiral.concert_booking_system.mapper;

import com.saimiral.concert_booking_system.dto.UserResponseDTO;
import com.saimiral.concert_booking_system.entity.User;

public class UserMapper {
    public UserResponseDTO toResponse(User user) {
        return new UserResponseDTO(user.getId(), user.getName(), user.getBirthDate(), user.getEmail());
    }
}
