package io.github.matheushenriquereiter.cathub.post;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class PostController {
    private final PostsRepository postsRepository;

    public PostController(PostsRepository postsRepository) {
        this.postsRepository = postsRepository;
    }

    @GetMapping("/posts")
    public List<Post> getPosts() {
        return postsRepository.findAll();
    }
}
