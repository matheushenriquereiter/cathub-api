package io.github.matheushenriquereiter.cathub.post;

import io.github.matheushenriquereiter.cathub.user.User;
import io.github.matheushenriquereiter.cathub.user.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class PostsMapper {
    public PostResponse toResponse(Post post) {
        User user = post.getUser();

        return new PostResponse(
                post.getId(),
                post.getDescription(),
                new UserResponse(
                        user.getId(),
                        user.getUsername()
                ));
    }
}
