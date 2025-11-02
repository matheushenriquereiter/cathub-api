package io.github.matheushenriquereiter.cathub.controller;

import io.github.matheushenriquereiter.cathub.entity.Post;
import io.github.matheushenriquereiter.cathub.entity.User;
import io.github.matheushenriquereiter.cathub.post.PostRequest;
import io.github.matheushenriquereiter.cathub.post.PostsRepository;
import io.github.matheushenriquereiter.cathub.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("posts")
public class PostController {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;

    public PostController(PostsRepository postsRepository, UserRepository userRepository) {
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<Void> post(@Valid @RequestBody PostRequest postRequest) {
        User user = userRepository.findById(postRequest.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Post post = Post.builder()
                .description(postRequest.description())
                .user(user)
                .build();

        postsRepository.save(post);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}