package se.sprinto.hakan.springboot2.dto;

import java.util.List;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        List<String> roles,
        boolean enabled
) {
}
