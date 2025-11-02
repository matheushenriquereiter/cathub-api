package io.github.matheushenriquereiter.cathub.controller;

import io.github.matheushenriquereiter.cathub.auth.RecoveryJwtTokenDto;
import io.github.matheushenriquereiter.cathub.service.UserService;
import io.github.matheushenriquereiter.cathub.user.CreateUserDto;
import io.github.matheushenriquereiter.cathub.user.LoginUserDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserDto createUserDto) {
        userService.createUser(createUserDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public RecoveryJwtTokenDto authenticateUser(@RequestBody @Valid LoginUserDto loginUserDto) {
        try {
            return userService.authenticateUser(loginUserDto);
        } catch (AuthenticationException error) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not exists");
        }
    }
}

