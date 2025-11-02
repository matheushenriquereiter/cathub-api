package io.github.matheushenriquereiter.cathub.post;

import io.github.matheushenriquereiter.cathub.entity.Post;
import io.github.matheushenriquereiter.cathub.entity.User;
import org.springframework.stereotype.Component;

@Component
public class PostsMapper {
    public PostResponse toResponse(Post post) {
        User user = post.getUser();

        return new PostResponse(
                post.getId(),
                post.getUser().getId(),
                post.getImage().getId());
    }
}
