package io.github.matheushenriquereiter.cathub.user;

import io.github.matheushenriquereiter.cathub.auth.JwtTokenService;
import io.github.matheushenriquereiter.cathub.auth.RecoveryJwtTokenDto;
import io.github.matheushenriquereiter.cathub.auth.SecurityConfiguration;
import io.github.matheushenriquereiter.cathub.auth.UserDetailsImpl;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenDto authenticateUser(LoginUserDto loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenDto(jwtTokenService.generateToken(userDetails));
    }

    public void createUser(CreateUserDto createUserDto) {
        User newUser = User.builder()
                .username(createUserDto.username())
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .build();

        userRepository.save(newUser);
    }
}