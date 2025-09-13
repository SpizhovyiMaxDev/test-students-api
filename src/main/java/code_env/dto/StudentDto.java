package code_env.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record StudentDto(
        @NotBlank(message = "Name is required")
        String name,
        
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        
        @NotNull(message = "Age is required")
        @Min(value = 14, message = "Age must be at least 14")
        int age,
        
        @NotBlank(message = "Grade is required")
        String grade
) {}

