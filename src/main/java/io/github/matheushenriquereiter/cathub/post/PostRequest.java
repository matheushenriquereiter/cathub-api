package io.github.matheushenriquereiter.cathub.post;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PostRequest(
        @NotEmpty(message = "Post description can't be empty or null")
        String description,
        @NotNull(message = "Post user_id can't be null") Integer userId
) {}
