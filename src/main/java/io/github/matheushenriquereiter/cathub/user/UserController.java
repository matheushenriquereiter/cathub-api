package io.github.matheushenriquereiter.cathub.user;

import io.github.matheushenriquereiter.cathub.post.Post;
import io.github.matheushenriquereiter.cathub.post.PostResponse;
import io.github.matheushenriquereiter.cathub.post.PostsMapper;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class UserController {
    private final UserRepository userRepository;
    private final PostsMapper postsMapper;

    public UserController(UserRepository userRepository, PostsMapper postsMapper) {
        this.userRepository = userRepository;
        this.postsMapper = postsMapper;
    }

    @GetMapping("/users")
    public List<UserResponse> getUser() {
        List<User> users = userRepository.findAll();

        return users.stream().map((user) -> new UserResponse(user.getId(), user.getUsername())).toList();
    }

    @GetMapping("/users/{id}")
    public UserResponse getUser(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "User not exists");
        }

        return new UserResponse(user.get().getId(), user.get().getUsername());
    }

    @PostMapping("/users")
    public UserResponse insertUser(@RequestBody @Valid UserRequest user) {
        User newUser = userRepository.save(new User(user.username(), user.password()));

        return new UserResponse(newUser.getId(), newUser.getUsername());
    }

    @GetMapping("/user/{id}/post")
    public List<PostResponse> getUserPosts(@PathVariable Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(404), "User not exists");
        }

        List<Post> posts = user.get().getPosts();

        return posts.stream().map(postsMapper::toResponse).toList();
    }
}
