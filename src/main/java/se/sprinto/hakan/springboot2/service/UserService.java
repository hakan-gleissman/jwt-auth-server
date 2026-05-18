package se.sprinto.hakan.springboot2.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.sprinto.hakan.springboot2.dto.UserRequestDTO;
import se.sprinto.hakan.springboot2.dto.UserResponseDTO;
import se.sprinto.hakan.springboot2.model.User;
import se.sprinto.hakan.springboot2.repository.UserRepository;

import java.util.Locale;
import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponseDTO register(UserRequestDTO request) {
        String normalizedEmail = request.email().trim().toLowerCase(Locale.ROOT);
        if (userRepository.existsByUsername(request.username().trim())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User();
        user.setUsername(request.username().trim());
        user.setEmail(normalizedEmail);
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        user.setEnabled(true);
        return toResponse(userRepository.save(user));
    }

    public UserResponseDTO getByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found: " + username));
        return toResponse(user);
    }

    private UserResponseDTO toResponse(User user) {
        java.util.List<String> roles = java.util.List.of(user.getRole().split(",")).stream()
                .map(String::trim)
                .filter(role -> !role.isBlank())
                .toList();
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                roles,
                user.isEnabled()
        );
    }
}
