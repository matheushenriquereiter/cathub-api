package io.github.matheushenriquereiter.cathub.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class PostController {
    private final PostsRepository postsRepository;
    private final PostsMapper postsMapper;

    public PostController(PostsRepository postsRepository, PostsMapper postsMapper) {
        this.postsRepository = postsRepository;
        this.postsMapper = postsMapper;
    }

    @GetMapping("/posts")
    public Page<PostResponse> getPosts(Pageable pageable) {
        return postsRepository.findAll(pageable).map(postsMapper::toResponse);
    }

    @GetMapping("/users/{userId}/posts")
    public Page<PostResponse> getUserPosts(@PathVariable Integer userId, Pageable pageable) {
        return postsRepository.findByUserId(userId, pageable).map(postsMapper::toResponse);
    }
}
