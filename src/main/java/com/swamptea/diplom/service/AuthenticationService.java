package com.swamptea.diplom.service;

import com.swamptea.diplom.domain.Users;
import com.swamptea.diplom.repo.UserRepository;
import com.swamptea.diplom.security.AuthenticationRequest;
import com.swamptea.diplom.security.AuthenticationResponse;
import com.swamptea.diplom.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        if (repository.findByLogin(request.getLogin()).isPresent()) {
           authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getLogin(),
                            request.getPassword()
                    )
            );

            var user = repository.findByLogin(request.getLogin())
                    .orElseThrow();

            Map<String, Object> claims = new HashMap<>();
            claims.put(request.getLogin(), user);
            var jwtToken = jwtService.generateToken(claims, user);

            return AuthenticationResponse.builder()
                    .token(jwtToken)
                    .build();
        } else return null;
    }

    public Optional<Users> getUser(String login) {
        return repository.findByLogin(login);
    }
}