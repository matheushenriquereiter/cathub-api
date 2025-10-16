package io.github.matheushenriquereiter.cathub.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record LoginUserDto(
        @Email(message = "Invalid email format")
        @NotBlank(message = "The 'email' field is required and cannot be blank.")
        String email,

        @Size(min = 8, max = 20, message = "The 'password' filed must be greater than 8 characters and lower than 20 characters.")
        @NotBlank(message = "The 'password' field is required and cannot be blank.")
        String password
) {
}