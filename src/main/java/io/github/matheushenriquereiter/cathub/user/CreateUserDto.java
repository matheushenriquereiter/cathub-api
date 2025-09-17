package io.github.matheushenriquereiter.cathub.user;

public record CreateUserDto(
        String username,
        String email,
        String password
) {
}
