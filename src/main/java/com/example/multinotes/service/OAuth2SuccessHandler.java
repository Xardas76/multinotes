package com.example.multinotes.service;

import com.example.multinotes.config.TenantContext;
import com.example.multinotes.config.security.JwtUtil;
import com.example.multinotes.model.User;
import com.example.multinotes.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public OAuth2SuccessHandler(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> byUsername = userRepository.findByUsername(username);

        TenantContext.setTenantId(byUsername.orElseThrow(AuthenticationException::new).getId());
        String token = jwtUtil.generateToken(username);
        response.setContentType("application/json");
        response.getWriter().write("{\"token\":\"" + token + "\"}");
        response.getWriter().flush();
    }

}