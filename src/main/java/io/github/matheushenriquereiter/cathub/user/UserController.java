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

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/login")
    public RecoveryJwtTokenDto authenticateUser(@RequestBody @Valid LoginUserDto loginUserDto) {
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
}

