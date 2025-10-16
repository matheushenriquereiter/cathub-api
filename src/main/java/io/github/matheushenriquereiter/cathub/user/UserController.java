package io.github.matheushenriquereiter.cathub.user;

import io.github.matheushenriquereiter.cathub.auth.RecoveryJwtTokenDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping
public class UserController {
    private final UserService userService;
    private final UserRepository userRepository;

    public UserController(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping("/users/login")
    public RecoveryJwtTokenDto authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        validateLoginUserDto(loginUserDto);

        try {
            return userService.authenticateUser(loginUserDto);
        } catch (AuthenticationException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exists");
        }
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void validateLoginUserDto(LoginUserDto loginUserDto) {
        if (loginUserDto.email() == null || loginUserDto.email().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'email' field is required and cannot be blank.");
        }

        if (loginUserDto.password() == null || loginUserDto.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'password' field is required and cannot be blank.");
        }
    }
}

