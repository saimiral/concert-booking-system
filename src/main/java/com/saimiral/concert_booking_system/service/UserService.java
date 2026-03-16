package com.saimiral.concert_booking_system.service;

import com.saimiral.concert_booking_system.dto.PagedResponse;
import com.saimiral.concert_booking_system.dto.UserRequestDTO;
import com.saimiral.concert_booking_system.dto.UserResponseDTO;
import com.saimiral.concert_booking_system.entity.User;
import com.saimiral.concert_booking_system.exception.DuplicateEmailException;
import com.saimiral.concert_booking_system.exception.UserNotFoundException;
import com.saimiral.concert_booking_system.mapper.UserMapper;
import com.saimiral.concert_booking_system.repository.UserRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO createUser(UserRequestDTO dto) {
        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateEmailException("Email already in use");
        }
            User user = new User(dto.getName(), dto.getBirthDate(), dto.getEmail());
            user.setPassword(passwordEncoder.encode(dto.getPassword()));

            return userMapper.toResponse(userRepository.save(user));
    }

    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return userMapper.toResponse(user);
    }

    public PagedResponse<UserResponseDTO> getAllUsers(Pageable pageable) {
        Page<User> page = userRepository.findAll(pageable);

        List<UserResponseDTO> users =
                page.getContent()
                        .stream()
                        .map(userMapper::toResponse)
                        .toList();

        return new PagedResponse<>(
               users,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.delete(user);
    }
}
