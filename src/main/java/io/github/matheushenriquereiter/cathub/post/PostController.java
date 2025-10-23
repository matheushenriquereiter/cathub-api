package io.github.matheushenriquereiter.cathub.post;

import io.github.matheushenriquereiter.cathub.user.User;
import io.github.matheushenriquereiter.cathub.user.UserRepository;
import io.github.matheushenriquereiter.cathub.user.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("posts")
public class PostController {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final PostsMapper postsMapper;

    public PostController(PostsRepository postsRepository, UserRepository userRepository, PostsMapper postsMapper) {
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
        this.postsMapper = postsMapper;
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