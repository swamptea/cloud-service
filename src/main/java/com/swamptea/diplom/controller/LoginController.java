package com.swamptea.diplom.controller;

import com.swamptea.diplom.entity.AuthenticationRequest;
import com.swamptea.diplom.entity.AuthenticationResponse;
import com.swamptea.diplom.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationService service;


    @PostMapping("")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }


}
