package io.github.matheushenriquereiter.cathub.post;

import io.github.matheushenriquereiter.cathub.user.User;
import io.github.matheushenriquereiter.cathub.user.UserRepository;
import io.github.matheushenriquereiter.cathub.user.UserResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
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

    @GetMapping
    public List<PostResponse> getPost() {
        List<Post> posts = postsRepository.findAll();

        return posts.stream().map(postsMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public PostResponse getPostById(@PathVariable Integer id) {
        Optional<Post> post = postsRepository.findById(id);

        if (post.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "Post not exists");
        }

        return postsMapper.toResponse(post.get());
    }

    @PostMapping
    public PostResponse insertPost(@RequestBody @Valid PostRequest post) {
        Optional<User> user = userRepository.findById(post.userId());

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Post user does not exists");
        }

        Post addedPost = postsRepository.save(new Post(post.description(), user.get()));

        return postsMapper.toResponse(addedPost);
    }
}
