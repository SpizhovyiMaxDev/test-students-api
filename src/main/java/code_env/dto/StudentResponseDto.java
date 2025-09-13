package code_env.dto;

// Response DTO for returning student data with ID
public record StudentResponseDto(
        int id,
        String name,
        String email,
        int age,
        String grade
) {}
