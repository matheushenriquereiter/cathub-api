package io.github.matheushenriquereiter.cathub.post;

import io.github.matheushenriquereiter.cathub.user.UserResponse;

public record PostResponse(
        Integer id,
        String description,
        UserResponse user
) {}

