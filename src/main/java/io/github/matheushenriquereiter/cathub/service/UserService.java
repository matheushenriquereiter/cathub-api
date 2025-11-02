package io.github.matheushenriquereiter.cathub.service;

import io.github.matheushenriquereiter.cathub.auth.JwtTokenService;
import io.github.matheushenriquereiter.cathub.auth.RecoveryJwtTokenDto;
import io.github.matheushenriquereiter.cathub.auth.SecurityConfiguration;
import io.github.matheushenriquereiter.cathub.auth.UserDetailsImpl;
import io.github.matheushenriquereiter.cathub.entity.User;
import io.github.matheushenriquereiter.cathub.repository.UserRepository;
import io.github.matheushenriquereiter.cathub.user.CreateUserDto;
import io.github.matheushenriquereiter.cathub.user.LoginUserDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final SecurityConfiguration securityConfiguration;

    public UserService(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, UserRepository userRepository, SecurityConfiguration securityConfiguration) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
        this.securityConfiguration = securityConfiguration;
    }

    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {
        User user = User.builder()
                .username(createUserDto.username())
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .build();

        userRepository.save(user);
    }
}