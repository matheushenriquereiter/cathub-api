package io.github.matheushenriquereiter.cathub.user;

import io.github.matheushenriquereiter.cathub.auth.RecoveryJwtTokenDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users/login")
    public ResponseEntity<RecoveryJwtTokenDto> authenticateUser(@RequestBody LoginUserDto loginUserDto) {
        RecoveryJwtTokenDto token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@RequestBody CreateUserDto createUserDto) {
        validateCreateUserDto(createUserDto);
        userService.createUser(createUserDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/users/test")
    public ResponseEntity<String> getAuthenticationTest() {
        return new ResponseEntity<>("Autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/users/test/customer")
    public ResponseEntity<String> getCustomerAuthenticationTest() {
        return new ResponseEntity<>("Cliente autenticado com sucesso", HttpStatus.OK);
    }

    @GetMapping("/users/test/administrator")
    public ResponseEntity<String> getAdminAuthenticationTest() {
        return new ResponseEntity<>("Administrador autenticado com sucesso", HttpStatus.OK);
    }

    private void validateCreateUserDto(CreateUserDto createUserDto) {
        if (createUserDto.username() == null || createUserDto.username().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'username' field is required and cannot be blank.");
        }

        if (createUserDto.email() == null || createUserDto.email().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'email' field is required and cannot be blank.");
        }

        if (createUserDto.password() == null || createUserDto.password().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The 'password' field is required and cannot be blank.");
        }
    }
}
