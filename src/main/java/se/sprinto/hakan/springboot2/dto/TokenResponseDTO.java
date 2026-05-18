package se.sprinto.hakan.springboot2.dto;

import java.util.List;

public record TokenResponseDTO(
        String accessToken,
        long expiresIn,
        String subject,
        List<String> roles
) {
}
