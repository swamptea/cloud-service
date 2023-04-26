package com.swamptea.diplom.controller;

import com.swamptea.diplom.domain.Role;
import com.swamptea.diplom.security.JwtService;
import com.swamptea.diplom.service.AuthenticationService;
import com.swamptea.diplom.service.LogoutService;
import com.swamptea.diplom.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/")
public class CloudConroller {

    private final StorageService service;

    private final JwtService jwtService;

    private final LogoutService logoutService;

    private final AuthenticationService authService;

    public CloudConroller(StorageService service, JwtService jwtService, LogoutService logoutService, AuthenticationService authService) {
        this.service = service;
        this.jwtService = jwtService;
        this.logoutService = logoutService;
        this.authService = authService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getList() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            String login = auth.getName();
            Role role = authService.getUser(login).get().getRole();
            if (role == Role.ADMIN)
                return ResponseEntity.status(HttpStatus.OK).body(service.all());
            else return ResponseEntity.status(HttpStatus.OK).body(service.allForUser(login));
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            String login = auth.getName();
            if (service.uploadFile(file, login)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestParam String filename) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            String login = auth.getName();
            Role role = authService.getUser(login).get().getRole();
            if (service.deleteFile(filename, login, role)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam String filename) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            String login = auth.getName();
            Role role = authService.getUser(login).get().getRole();
            byte[] downloadFile;
            try {
                downloadFile = service.downloadFile(filename, login, role);
                if (downloadFile != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(downloadFile);
                } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (DataFormatException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/file")
    public ResponseEntity<?> editFile(@RequestParam String filename, @RequestBody String newName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()) {
            String login = auth.getName();
            Role role = authService.getUser(login).get().getRole();
            if (service.editFileName(filename, login, newName, role)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        logoutService.logout(request, response, auth);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}