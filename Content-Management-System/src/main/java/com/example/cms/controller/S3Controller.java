package com.example.cms.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.example.cms.service.S3Service;
import com.example.cms.utility.ResponseStructure;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/s3")
@AllArgsConstructor
public class S3Controller {

    private final S3Service s3Service;

    // Upload file
    @PostMapping("/upload")
    public ResponseEntity<ResponseStructure<String>> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        return s3Service.uploadFile(file);
    }

    // Download file
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable("fileName") String key) {
        return s3Service.downloadFile(key);
    }

    // Delete file
    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<ResponseStructure<String>> deleteFile(@PathVariable("fileName") String key) {
        return s3Service.deleteFile(key);
    }

    // List all files
    @GetMapping("/list")
    public ResponseEntity<ResponseStructure<List<String>>> listFiles() {
        return s3Service.listFiles();
    }
}
