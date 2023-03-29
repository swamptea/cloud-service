package com.swamptea.diplom.service;

import com.swamptea.diplom.repo.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        final String authHeader = request.getHeader("auth-token");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        var storedToken = tokenRepository.findByT(authHeader);
        if (storedToken != null) {
            tokenRepository.delete(tokenRepository.findByT(authHeader));
            SecurityContextHolder.clearContext();
        }
    }
}