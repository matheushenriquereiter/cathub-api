package io.github.matheushenriquereiter.cathub.user;

import io.github.matheushenriquereiter.cathub.auth.RecoveryJwtTokenDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        validateLoginUserDto(loginUserDto);
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        validateCreateUserDto(createUserDto);
        userService.createUser(createUserDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void validateCreateUserDto(CreateUserDto createUserDto) {
        if (createUserDto.username() == null || createUserDto.username().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'username' field is required and cannot be blank.");
        }

        if (createUserDto.username().length() < 5 || createUserDto.username().length() > 20) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The username length must be between 5 and 20 characters.");
        }

        if (createUserDto.email() == null || createUserDto.email().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'email' field is required and cannot be blank.");
        }

        Validate validate = new Validate();

        if (!validate.email(createUserDto.email())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The email address is invalid.");
        }

        if (createUserDto.password() == null || createUserDto.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'password' field is required and cannot be blank.");
        }
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
