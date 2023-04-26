package com.swamptea.diplom.controller;

import com.swamptea.diplom.security.AuthenticationRequest;
import com.swamptea.diplom.security.AuthenticationResponse;
import com.swamptea.diplom.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationService service;

    @PostMapping("")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        AuthenticationResponse response = service.authenticate(request);
        if (response != null)
            return ResponseEntity.ok(response);
        else return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
