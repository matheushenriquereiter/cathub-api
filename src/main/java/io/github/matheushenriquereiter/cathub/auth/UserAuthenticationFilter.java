package io.github.matheushenriquereiter.cathub.auth;

import io.github.matheushenriquereiter.cathub.entity.User;
import io.github.matheushenriquereiter.cathub.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class UserAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserRepository userRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public UserAuthenticationFilter(JwtTokenService jwtTokenService, UserRepository userRepository) {
        this.jwtTokenService = jwtTokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (requiresAuthentication(request)) {
            String token = recoveryToken(request);
            if (token != null) {
                String subject = jwtTokenService.getSubjectFromToken(token);
                User user = userRepository.findByEmail(subject)
                        .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

                UserDetailsImpl userDetails = new UserDetailsImpl(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("Token ausente ou inválido.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String recoveryToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private boolean requiresAuthentication(HttpServletRequest request) {
        String uri = request.getRequestURI();

        for (String pattern : SecurityConfiguration.ENDPOINTS_WITH_AUTHENTICATION_REQUIRED) {
            if (pathMatcher.match(pattern, uri)) {
                return true;
            }
        }
        
        return false;
    }
}
