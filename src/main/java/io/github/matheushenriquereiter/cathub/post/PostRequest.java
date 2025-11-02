package io.github.matheushenriquereiter.cathub.post;

import jakarta.validation.constraints.NotNull;

public record PostRequest(
        @NotNull(message = "Post user_id can't be null") Integer userId,
        @NotNull(message = "Post image_id can't be null") Integer imageId
) {
}
