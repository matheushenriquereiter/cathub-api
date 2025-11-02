package io.github.matheushenriquereiter.cathub.controller;

import io.github.matheushenriquereiter.cathub.entity.Image;
import io.github.matheushenriquereiter.cathub.entity.Post;
import io.github.matheushenriquereiter.cathub.entity.User;
import io.github.matheushenriquereiter.cathub.post.PostResponse;
import io.github.matheushenriquereiter.cathub.post.PostsRepository;
import io.github.matheushenriquereiter.cathub.repository.ImageRepository;
import io.github.matheushenriquereiter.cathub.repository.UserRepository;
import io.github.matheushenriquereiter.cathub.service.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("posts")
public class PostController {
    private final PostsRepository postsRepository;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    public PostController(PostsRepository postsRepository, UserRepository userRepository, ImageRepository imageRepository, ImageService imageService) {
        this.postsRepository = postsRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
        this.imageService = imageService;
    }

    @GetMapping
    public List<PostResponse> getPosts() {
        List<Post> posts = postsRepository.findAll();

        return posts.stream().map(post -> new PostResponse(post.getId(), post.getUser().getId(), post.getImage().getId())).toList();
    }

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestParam("image") MultipartFile file) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        Image image = imageService.uploadImage(file);

        Post post = Post.builder()
                .user(user)
                .image(image)
                .build();

        postsRepository.save(post);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}