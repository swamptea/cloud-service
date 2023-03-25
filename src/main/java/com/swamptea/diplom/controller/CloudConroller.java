package com.swamptea.diplom.controller;

import com.swamptea.diplom.entity.UploadFileRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cloud")
public class FileConroller {

    @PostMapping("/file")
    public ResponseEntity uploadFile (@RequestBody UploadFileRequest request){
      //  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(request);
      //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(request);
        return ResponseEntity.status(HttpStatus.OK).body(request);
    }

    @DeleteMapping("/file")
    public ResponseEntity deleteFile (@RequestBody UploadFileRequest request){
       // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(request);
        //  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(request);
        //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(request);
        return ResponseEntity.status(HttpStatus.OK).body(request);
    }

    @GetMapping("/file")
    public ResponseEntity getFile (@RequestBody UploadFileRequest request){
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(request);
        //  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(request);
        //  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(request);
        return ResponseEntity.status(HttpStatus.OK).body(request);
    }

    @PutMapping("/file")
    public ResponseEntity editFile (@RequestBody UploadFileRequest request){
        // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(request);
        //  return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(request);
        return ResponseEntity.status(HttpStatus.OK).body(request);
    }
}
