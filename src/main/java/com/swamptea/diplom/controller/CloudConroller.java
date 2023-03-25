package com.swamptea.diplom.controller;

import com.swamptea.diplom.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.zip.DataFormatException;

@RestController
@RequestMapping("/")
public class CloudConroller {

    @Autowired
    private StorageService service;

    @GetMapping("/list")
    public ResponseEntity<?> getList() {
        return ResponseEntity.status(HttpStatus.OK).body(service.all());
    }

    @PostMapping("/file")
    public ResponseEntity<?> uploadFile(@RequestParam MultipartFile file) throws IOException {
        if(service.uploadFile(file)){
        return new ResponseEntity<>(HttpStatus.OK);}
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestParam String filename) {
        if (service.deleteFile(filename)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> getFile(@RequestParam String filename) {

        byte[] downloadFile;
        try {
            downloadFile = service.downloadFile(filename);
            if (downloadFile != null) {
                return ResponseEntity.status(HttpStatus.OK).body(downloadFile);
            }
            else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (DataFormatException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/file")
    public ResponseEntity<?> editFile(@RequestParam String filename, @RequestBody String newName) {
        if(service.editFileName(filename, newName)){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
