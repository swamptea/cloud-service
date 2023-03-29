package com.swamptea.diplom.controller;

import com.swamptea.diplom.service.AuthenticationService;
import com.swamptea.diplom.service.LogoutService;
import com.swamptea.diplom.service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/")
public class CloudConroller {

    private final StorageService service;

    private final AuthenticationService authService;

    private final LogoutService logoutService;

    public CloudConroller(StorageService service, AuthenticationService authService, LogoutService logoutService) {
        this.service = service;
        this.authService = authService;
        this.logoutService = logoutService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getList(@RequestHeader(name = "auth-token", defaultValue = "") String header) {
        if (authService.isTokenValid(header))
            return ResponseEntity.status(HttpStatus.OK).body(service.all());
        else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file, @RequestHeader(name = "auth-token", defaultValue = "") String header) throws IOException {
        if (authService.isTokenValid(header)) {
            if (service.uploadFile(file)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestParam String filename, @RequestHeader(name = "auth-token", defaultValue = "") String header) {
        if (authService.isTokenValid(header)) {
            if (service.deleteFile(filename)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam String filename, @RequestHeader(name = "auth-token", defaultValue = "") String header) {
        if (authService.isTokenValid(header)) {
            byte[] downloadFile;
            try {
                downloadFile = service.downloadFile(filename);
                if (downloadFile != null) {
                    return ResponseEntity.status(HttpStatus.OK).body(downloadFile);
                } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            } catch (DataFormatException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("/file")
    public ResponseEntity<?> editFile(@RequestParam String filename, @RequestBody String newName, @RequestHeader(name = "auth-token", defaultValue = "") String header) {
        if (authService.isTokenValid(header)) {
            if (service.editFileName(filename, newName)) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        logoutService.logout(request, response, authentication);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}