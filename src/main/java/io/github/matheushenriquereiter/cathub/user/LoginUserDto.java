package io.github.matheushenriquereiter.cathub.user;

public record LoginUserDto(
        String email,
        String password
) {
}