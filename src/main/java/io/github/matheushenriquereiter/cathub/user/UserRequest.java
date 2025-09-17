package io.github.matheushenriquereiter.cathub.user;

import jakarta.validation.constraints.NotEmpty;

public record UserRequest(
        @NotEmpty(message = "User username can't be empty or null")
        String username,
        @NotEmpty(message = "User email can't be empty or null")
        String email,
        @NotEmpty(message = "User password can't be empty or null")
        String password
) {
}
